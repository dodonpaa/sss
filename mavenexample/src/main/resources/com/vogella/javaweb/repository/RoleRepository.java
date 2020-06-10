package com.vogella.javaweb.repository;

import com.vogella.javaweb.model.Role;
//import org.springframework.data.repository.CrudRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//import org.springframework.data.jpa.repository.CrudRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
	List<Role> findByName(String name);
}