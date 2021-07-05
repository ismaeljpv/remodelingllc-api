package com.remodelingllc.api.service;

import com.remodelingllc.api.dto.EmailDTO;
import com.remodelingllc.api.dto.PasswordChangeDTO;
import com.remodelingllc.api.dto.UserDTO;
import com.remodelingllc.api.dto.UserTokenDTO;
import com.remodelingllc.api.entity.PasswordToken;
import com.remodelingllc.api.entity.User;
import com.remodelingllc.api.entity.enums.Status;
import com.remodelingllc.api.exception.BadRequestException;
import com.remodelingllc.api.exception.EntityNotFoundException;
import com.remodelingllc.api.interfaces.UserData;
import com.remodelingllc.api.repository.UserRepository;
import com.remodelingllc.api.security.UserDetailsMapper;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UserService implements UserDetailsService  {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordTokenService passwordTokenService;
    private final EmailService emailService;
    private final UserRepository userRepository;

    public UserService(final BCryptPasswordEncoder bCryptPasswordEncoder,
                       final PasswordTokenService passwordTokenService,
                       final EmailService emailService,
                       final UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.passwordTokenService = passwordTokenService;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAllActive() {
        return userRepository.findAllByStatus(Status.ACTIVE).stream()
                .map(this::converUserToDTO).collect(Collectors.toList());
    }

    public Page<UserData> findAllActivePaginated(final int page, final int size) {
        return userRepository.findAllByStatus(Status.ACTIVE, PageRequest.of(page, size));
    }

    public User findById(final int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User Not Found");
        }
        return user.get();
    }

    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User Not Found");
        }
        return user.get();
    }

    public User save(final User user) {
        Optional<User> oldUser = userRepository.findByUsername(user.getUsername());
        if (oldUser.isPresent()) {
            throw new BadRequestException("Username is already taken");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User update(final User user) {
        Optional<User> oldUser = userRepository.findById(user.getId());
        if (oldUser.isEmpty()) {
            throw new EntityNotFoundException("User Not Found");
        }
        oldUser = userRepository.findByUsername(user.getUsername());
        if (oldUser.isPresent() && user.getId() != oldUser.get().getId()) {
            throw new BadRequestException("Username is already taken");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public boolean passwordRecovery(final String username) {
        User user = findByUsername(username);
        if (user.getStatus() == Status.INACTIVE) {
            throw new BadRequestException("User Not Found");
        }
        PasswordToken token = passwordTokenService.createPasswordToken(user.getId());
        EmailDTO email = new EmailDTO();
        email.setTo(user.getEmail());
        email.setUsername(user.getUsername());
        email.setToken(token.getToken());
        email.setSubject("Password Recovery Token");
        return emailService.sendPasswordRecoveryEmail(email);
    }

    public User passwordChange(final PasswordChangeDTO passwordChange) {
        User user = findByUsername(passwordChange.getUsername());
        var token = passwordTokenService.findByTokenAndUser(passwordChange.getToken(), user.getId());
        log.info("Token {} verified for user with id {}", token.getToken(), token.getUserId());
        user.setPassword(bCryptPasswordEncoder.encode(passwordChange.getPassword()));
        return userRepository.save(user);
    }

    public void inactivateUser (final int id) {
        var token = (UserTokenDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = this.findById(id);
        if (token.getId() == user.getId()) {
            throw new BadRequestException("User cant delete himself");
        }
        user.setStatus(Status.INACTIVE);
        userRepository.save(user);
    }

    @Transactional
    public void updateLastLogin(final User user) {
        userRepository.updateLastLogin(user.getId());
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User Not Found");
        }
        return UserDetailsMapper.build(user.get());
    }

    private UserDTO converUserToDTO(final User user) {
        return new ModelMapper().map(user, UserDTO.class);
    }
}
