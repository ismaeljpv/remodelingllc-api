package com.remodelingllc.api.repository;

import com.remodelingllc.api.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    Optional<Role> findByRole(final String role);

}
