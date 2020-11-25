package com.vermeg.ams.repositories;

import org.springframework.data.repository.CrudRepository;

import com.vermeg.ams.entities.Order;

public interface OrderRepository extends CrudRepository<Order,Integer> {

}
