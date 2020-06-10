package com.vogella.javaweb.repository;

import com.vogella.javaweb.model.CustomerOrder;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by Elfee on 10/12/17.
 */
public interface OrderRepository extends JpaRepository<CustomerOrder,Long>{

}
