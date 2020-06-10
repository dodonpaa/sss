package com.vogella.javaweb.repository;

import com.vogella.javaweb.model.HederaMapping;

//import org.springframework.data.repository.CrudRepository;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

/**
 * Created by Elfee on 10/12/17.
 */
public interface MappingRepository extends JpaRepository<HederaMapping,Long>{

	List<HederaMapping> findByLoyaltyCard_cardId(Long cardId);
}