package com.remodelingllc.api.resource;

import com.remodelingllc.api.entity.User;
import com.remodelingllc.api.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserResource {

    private final UserService userService;

    public UserResource(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User findById(@PathVariable final int id) {
        return userService.findById(id);
    }

    @PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public User save(@Validated @RequestBody final User user) {
        return userService.save(user);
    }

    @PutMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public User update(@Validated @RequestBody final User user) {
        return userService.update(user);
    }

}
