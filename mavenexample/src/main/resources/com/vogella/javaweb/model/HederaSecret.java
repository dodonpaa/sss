package com.vogella.javaweb.model;

import org.hibernate.annotations.Fetch;

import javax.lang.model.element.Name;
import javax.persistence.*;
import java.util.Set;

/**
 * Created by Elfee on 10/12/17.
 */

@Entity
public class HederaSecret {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // secret Key
    private String secretKey;
    
    private String customerId;
    
    // real topic id for recording points
    private String pointTopic;
       
    // time when we write topic to Hedera node
    // @Basic
    private java.time.LocalDateTime writeTime;
   
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE} , fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;
    
    public HederaSecret() {
    	//for email validation
    	super();
    }
    
    public HederaSecret(String secretKey, String pointTopic, Topic topic, String customerId, java.time.LocalDateTime writeTime) {
        this.secretKey = secretKey;
        this.topic = topic;
        this.pointTopic = pointTopic;
        this.customerId = customerId;
        this.writeTime = writeTime;
    }
    
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
    
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
       
    public java.time.LocalDateTime getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(java.time.LocalDateTime writeTime) {
        this.writeTime = writeTime;
    }
    
    public String getPointTopic(){
    	return pointTopic;
    }
    
    public void setPointTopic(String pointTopic){
    	this.pointTopic = pointTopic;
    }
}