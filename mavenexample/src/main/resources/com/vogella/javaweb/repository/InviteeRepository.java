package com.vogella.javaweb.repository;

import com.vogella.javaweb.model.Customer;
import com.vogella.javaweb.model.Invitee;
//import org.springframework.data.repository.CrudRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

//import org.springframework.data.jpa.repository.CrudRepository;

public interface InviteeRepository extends JpaRepository<Invitee, Long>{
    Collection<Invitee> findByCustomerAndInviteId(Customer customer,String inviteId);
    Collection<Invitee> findByCustomerAndInviteeId(Customer customer,String inviteeId);
    Collection<Invitee> findByCustomerAndInviteIdAndInviteeId(Customer customer,String inviteId,String inviteeId);
    Long removeById(Long id);
}