package com.vermeg.ams.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.vermeg.ams.entities.Order;
import com.vermeg.ams.entities.Order_Details;

public interface OrderDetailsRepository extends CrudRepository<Order_Details,Integer>{
	
	 @Query("FROM Order_Details where orders = ?1")
	 List <Order_Details> booksfindAllById(Order d );

	
	 
}
