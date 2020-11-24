package com.vermeg.ams.repositories;

import org.springframework.data.repository.CrudRepository;

import com.vermeg.ams.entities.Client;

public interface ClientRepository extends CrudRepository <Client , Long> {

}
