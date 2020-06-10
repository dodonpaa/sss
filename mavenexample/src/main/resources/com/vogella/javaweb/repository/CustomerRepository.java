package com.vogella.javaweb.repository;

import com.vogella.javaweb.model.Customer;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Collection;
import com.vogella.javaweb.model.Role;

//for login hedera

/**
 * Created by Elfee on 10/12/17.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	Customer findByEmail(String email);
	Customer findByRolesIn(Collection<Role> roles);
}