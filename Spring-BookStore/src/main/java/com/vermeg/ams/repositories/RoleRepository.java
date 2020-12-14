package com.vermeg.ams.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vermeg.ams.entities.Role;
import com.vermeg.ams.entities.Client;

@Repository("role")
public interface RoleRepository extends CrudRepository <Role , Integer> {
Role findByRole(String  user);
}
