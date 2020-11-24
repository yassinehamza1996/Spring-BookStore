package com.vermeg.ams.repositories;

import org.springframework.data.repository.CrudRepository;

import com.vermeg.ams.entities.Book;

public interface BookRepository extends CrudRepository<Book,Integer> {

}
