package com.vogella.javaweb.model;
import javax.persistence.*;
import java.util.*;

import com.vogella.javaweb.model.Role;
import com.vogella.javaweb.model.Topic;

import java.util.Collection;



/**
 * Created by Elfee on 10/12/17.
 */

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long customerId;
    
    // Hedera added
    //@OneToMany(mappedBy = "customer")
    //Set<CustomerLoyalty> customerLoyalty;

    private String firstName;

    private String lastName;
    
    private String address;
    
    //username for login hedera
    private String email;
    
    //username for login hedera, will copy from email
    private String username;
    
    private String zip;
    
    private String phone;
    
    //password for login hedera
    private String password;
    
    //password confirm for login hedera
    @Transient
    private String passwordConfirm;
    
    //for email validation
    @Column(name = "enabled")
    private boolean enabled;
    
    //roles for login hedera
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(
            name = "user_id", referencedColumnName = "customerId"),
        inverseJoinColumns = @JoinColumn(
            name = "role_id", referencedColumnName = "id"))
    private Collection < Role > roles;
    
  //topics for hedera
    @ManyToMany
    @JoinTable(
    		  name = "users_topics", 
    		  joinColumns = @JoinColumn(name = "user_id"), 
    		  inverseJoinColumns = @JoinColumn(name = "topic_id"))
    private Set < Topic > topics;
    
    
    public Customer() {
    	//for email validation
    	super();
    	this.enabled = false;
    }

    public Customer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        //for email validation
        this.enabled = false;
    }

    public Customer(String firstName, String lastName, String email, String password, Collection < Role > roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
      //for email validation
        this.enabled = false;
    }
    
    public Customer(String firstName, String lastName, String email, String password, Collection < Role > roles, Set <Topic> topics) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
      //for email validation
        this.enabled = false;
        this.topics = topics;
    }
    
    //get username for login hedera, copy from email
    public String getUsername(){
    	return username;
    }
    
    //set username for login hedera, copy from email
    public void setUsername(String username){
    	this.username = username;
    }
    
    //get password for login hedera
    public String getPassword(){
    	return password;
    }
    
    //set password for login hedera
    public void setPassword(String password){
    	this.password = password;
    }
    
    //get password confirm for login hedera
    public String getPasswordConfirm(){
    	return passwordConfirm;
    }

    //set password confirm for login hedera
    public void setPasswordConfirm(String passwordConfirm){
    	this.passwordConfirm = passwordConfirm;
    }
    
    //get roles for login hedera
    public Collection < Role > getRoles() {
        return roles;
    }

    public void setRoles(Collection < Role > roles) {
        this.roles = roles;
    }
    
  //get roles for login hedera
    public Set < Topic > getTopics() {
        return topics;
    }

    public void setTopics(Set < Topic > Topics) {
        this.topics = topics;
    }
    
    public void setTopic(Topic topic) {
        this.topics.add(topic);
    }
    
    public void removeTopic(Topic topic) {
        this.topics.remove(topic);
    }
    
    public long getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
    
   
      
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
    
    public String getphone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean getEnabled() {
        return enabled;
    }
    
}