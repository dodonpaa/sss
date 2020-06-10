package com.vogella.javaweb.repository;

import com.vogella.javaweb.model.CustomerSignup;

//import org.springframework.data.repository.CrudRepository;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

/**
 * Created by Elfee on 10/12/17.
 */
public interface SignupRepository extends JpaRepository<CustomerSignup,Long>{
}