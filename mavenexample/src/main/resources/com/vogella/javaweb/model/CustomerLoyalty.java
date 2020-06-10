package com.vogella.javaweb.model;
import javax.persistence.*;

/**
 * Created by Elfee on 10/12/17.
 * Hedera added
 */

@Entity
public class CustomerLoyalty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long bindingId;
    
    @ManyToOne
    @JoinColumn(name = "customerId")
    Customer customer;
    
    @ManyToOne
    @JoinColumn(name = "cardId")
    LoyaltyCard card;
    
    private String points;
    
    public CustomerLoyalty() {
    	//for email validation
    	super();
    }
    

    public long getBindingId() {
        return bindingId;
    }
    
    public void setBindingId(long bindingId) {
        this.bindingId = bindingId;
    }
  
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public LoyaltyCard getLoyaltyCard(){
    	return card;
    }
    
    public void setLoyaltyCard(LoyaltyCard card){
    	this.card = card;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}