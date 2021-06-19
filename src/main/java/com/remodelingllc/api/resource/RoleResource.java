package com.remodelingllc.api.resource;

import com.remodelingllc.api.entity.Role;
import com.remodelingllc.api.service.RoleService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleResource {

    private final RoleService roleService;

    public RoleResource(final RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping(value = "/role", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Role> findAll(){
        return roleService.findAll();
    }

    @GetMapping(value = "/role/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Role findById(@PathVariable final int id){
        return roleService.findById(id);
    }

    @PostMapping(value = "/role", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Role save(@Validated @RequestBody final Role role) {
        return roleService.save(role);
    }

}
