package com.vogella.javaweb.model;
import javax.persistence.*;
import java.util.*;


/**
 * Created by Elfee on 10/12/17.
 * added for Hedera
 */

@Entity
public class LoyaltyCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long cardId;
    
    // Hedera added
    @OneToMany(mappedBy = "card")
    Set<CustomerLoyalty> customerLoyalty;

    // Hedera topic id
    private String topicId;

    // vendor id
    private String vendorId;
    
    // customer id
    private String customerId;
     
    public long getCardId() {
        return cardId;
    }
    
    public void setCardId(long cardId) {
        this.cardId = cardId;
    }
    
    // hedera added
    public Set<CustomerLoyalty> getCustomerLoyalty() {
        return customerLoyalty;
    }
 
    // hedera added
    public void setCustomerLoyalty(Set<CustomerLoyalty> customerLoyalty) {
        this.customerLoyalty = customerLoyalty;
    }
      
    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }
    
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}