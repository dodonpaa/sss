package com.vogella.javaweb.repository;

import com.vogella.javaweb.model.Product;

//import org.springframework.data.repository.CrudRepository;

import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by Elfee on 10/12/17.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
 
}