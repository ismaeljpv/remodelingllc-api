package com.remodelingllc.api.service;

import com.remodelingllc.api.entity.User;
import com.remodelingllc.api.exception.BadRequestException;
import com.remodelingllc.api.exception.EntityNotFoundException;
import com.remodelingllc.api.repository.RoleRepository;
import com.remodelingllc.api.repository.UserRepository;
import com.remodelingllc.api.security.UserDetailsMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService  {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public UserService(final BCryptPasswordEncoder bCryptPasswordEncoder,
                       final UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
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
        return userRepository.save(user);
    }

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
}
