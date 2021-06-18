package com.remodelingllc.api.service;

import com.remodelingllc.api.entity.Role;
import com.remodelingllc.api.exception.BadRequestException;
import com.remodelingllc.api.exception.EntityNotFoundException;
import com.remodelingllc.api.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        return (List<Role>) roleRepository.findAll();
    }

    public Role findById(final int id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isEmpty()) {
            throw new EntityNotFoundException("Role Doesnt Exist");
        }
        return role.get();
    }

    public Role save(final Role role) {
        Optional<Role> rol = roleRepository.findByRole(role.getRole());
        if (rol.isPresent()) {
            throw new BadRequestException("Role Already Exist");
        }
        return roleRepository.save(role);
    }

}
