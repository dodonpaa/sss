package com.vogella.javaweb.model;

import org.hibernate.annotations.Fetch;

import javax.lang.model.element.Name;
import javax.persistence.*;
import java.util.Set;

/**
 * Created by Elfee on 10/12/17.
 */

@Entity
public class HederaMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mappingId;

    // secret Key
    private String secretKey;
       
    // time when we write topic to Hedera node
    // @Basic
    private java.time.LocalDateTime writeTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE} , fetch = FetchType.LAZY)
    @JoinTable(name = "MAPPING_LOYALTY", joinColumns = {@JoinColumn(name = "MAPPING_ID")},inverseJoinColumns = {@JoinColumn(name = "LOYALTY_ID")})
    private LoyaltyCard loyaltyCard;
    
    public LoyaltyCard getLoyaltyCard() {
        return loyaltyCard;
    }

    public void setLoyaltyCard(LoyaltyCard loyaltyCard) {
        this.loyaltyCard = loyaltyCard;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
    
    
    public java.time.LocalDateTime getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(java.time.LocalDateTime writeTime) {
        this.writeTime = writeTime;
    }
}