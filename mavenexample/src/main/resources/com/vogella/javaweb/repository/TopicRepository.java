package com.vogella.javaweb.repository;

import com.vogella.javaweb.model.Topic;
//import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

import com.vogella.javaweb.model.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

//import org.springframework.data.jpa.repository.CrudRepository;

public interface TopicRepository extends JpaRepository<Topic, Long>{
	Collection<Topic> findByCustomersIn(List<Customer> customers);
}