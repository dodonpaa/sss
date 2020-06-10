package com.vogella.javaweb.repository;

import com.vogella.javaweb.model.HederaSecret;
import com.vogella.javaweb.model.Topic;
//import org.springframework.data.repository.CrudRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;


//import org.springframework.data.jpa.repository.CrudRepository;

public interface HederaSecretRepository extends JpaRepository<HederaSecret, Long>{
    Collection<HederaSecret> findByTopicId(Long postId);
    Collection<HederaSecret> findByTopic(Topic topic);
    Collection<HederaSecret> findByTopicAndCustomerId(Topic topic,String customerId);
    @Transactional
    Long removeByTopicAndCustomerId(Topic topic,String customerId);
    
    //Collection<HederaSecret> findByTopicId(String topicId);
}