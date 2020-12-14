package com.vermeg.ams.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.vermeg.ams.entities.Client;

public interface ClientRepository extends CrudRepository<Client,Integer> {
@Query("FROM Client where mail=?1")
Client findByEmail(String mail);
}
