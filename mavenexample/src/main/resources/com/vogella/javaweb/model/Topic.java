package com.vogella.javaweb.model;

import javax.persistence.*;
import java.util.Set;

//Role for login hedera

@Entity
@Table(name = "topic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topicId;
    
    private String creator;
    
    @ManyToMany(mappedBy = "topics")
    Set<Customer> customers;
    
    public Topic() {}
    
    public Topic(String topicId,String creator) {
        this.topicId = topicId;
        this.creator = creator;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }
    
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
    
    public Set getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }
}