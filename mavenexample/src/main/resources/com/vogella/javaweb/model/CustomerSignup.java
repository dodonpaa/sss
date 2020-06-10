package com.vogella.javaweb.model;

import org.hibernate.annotations.Fetch;

import javax.lang.model.element.Name;
import javax.persistence.*;
import java.util.Set;

/**
 * Created by Elfee on 10/12/17.
 */

@Entity
public class CustomerSignup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long signupId;

    //private Double total;
    private String signupText;

    @OneToOne
    private Customer customer;

    public Long getSignupId() {
        return signupId;
    }

    public void setSignupId(Long signupId) {
        this.signupId = signupId;
    }
 
    public String getsignupText() {
        return signupText;
    }

    public void setsignupText(String signupText) {
        this.signupText = signupText;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}