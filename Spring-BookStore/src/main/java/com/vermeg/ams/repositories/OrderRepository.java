package com.vermeg.ams.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.vermeg.ams.entities.Order;

public interface OrderRepository extends CrudRepository<Order,Integer> {
@Query("FROM Order where id_client =?1")
List<Order> getOrderbyclient(int id);
}
