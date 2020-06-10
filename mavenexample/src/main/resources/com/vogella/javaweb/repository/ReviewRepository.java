package com.vogella.javaweb.repository;

import com.vogella.javaweb.model.CustomerReview;

//import org.springframework.data.repository.CrudRepository;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

/**
 * Created by Elfee on 10/12/17.
 */
public interface ReviewRepository extends JpaRepository<CustomerReview,Long>{

	List<CustomerReview> findByProduct_productId(Long productId);
}