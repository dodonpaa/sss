package com.vogella.javaweb.model;

import org.hibernate.annotations.Fetch;

import javax.lang.model.element.Name;
import javax.persistence.*;
import java.util.Set;

/**
 * Created by Elfee on 10/12/17.
 */

@Entity
public class Invitee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // secret Key
    private String inviteId;
    
    private String inviteeId;
           
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE} , fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    public Invitee() {
    	//for email validation
    	super();
    }
    
    public Invitee(String inviteId, String inviteeId, Customer customer) {
        this.inviteId = inviteId;
        this.inviteeId = inviteeId;
        this.customer = customer;
    }
    
    public Long getId(){
    	return id;
    }
    
    public String getInviteId() {
        return inviteId;
    }

    public void setInviteId(String inviteId) {
        this.inviteId = inviteId;
    }
    
    public String getInviteeId() {
        return inviteeId;
    }

    public void setInviteeId(String inviteeId) {
        this.inviteeId = inviteeId;
    }
    
    
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}