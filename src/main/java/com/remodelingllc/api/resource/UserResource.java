package com.remodelingllc.api.resource;

import com.remodelingllc.api.dto.GenericMessageDTO;
import com.remodelingllc.api.dto.PasswordChangeDTO;
import com.remodelingllc.api.dto.UserDTO;
import com.remodelingllc.api.entity.User;
import com.remodelingllc.api.interfaces.UserData;
import com.remodelingllc.api.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserResource {

    private final UserService userService;

    public UserResource(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDTO> findAllActive() {
        return userService.findAllActive();
    }

    @GetMapping(value = "/user", params = {"page", "size"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<UserData> findAllActivePaginated(@RequestParam("page") final int page,
                                                 @RequestParam("size") final int size) {
        return userService.findAllActivePaginated(page, size);
    }

    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public User findById(@PathVariable final int id) {
        return userService.findById(id);
    }

    @PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public User save(@Validated @RequestBody final User user) {
        return userService.save(user);
    }

    @PutMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public User update(@Validated @RequestBody final User user) {
        return userService.update(user);
    }

    @DeleteMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable final int id) {
        userService.inactivateUser(id);
    }

    @GetMapping(value = "/user/password/recovery/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericMessageDTO passwordRecovery(@PathVariable String username) {
        boolean success = userService.passwordRecovery(username);
        String message = (success) ? "Password recovery token sended successfully!, please verify your email."
                : "Oops ! There was an error sendind the recovery token, please try again later.";
        return new GenericMessageDTO(message, success);
    }

    @PutMapping(value = "/user/password/change", produces = MediaType.APPLICATION_JSON_VALUE)
    public User passwordChange(@Validated @RequestBody PasswordChangeDTO passwordChange) {
        return userService.passwordChange(passwordChange);
    }

}
