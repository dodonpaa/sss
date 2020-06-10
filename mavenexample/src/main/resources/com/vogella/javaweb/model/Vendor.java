package com.vogella.javaweb.model;
import javax.persistence.*;

/**
 * Created by Elfee on 10/12/17.
 * added for Hedera
 */

@Entity
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long vendorId;
    
    private String name;
    
    private String address;
    
    private String email;
        
    private String phone;
    
    private long balance;
    
    private long nooftransaction;
    
    @OneToOne
    private LoyaltyCard loyaltyCard;
    

    public long getVendorId() {
        return vendorId;
    }
    
    public void setVendorId(long vendorId) {
        this.vendorId = vendorId;
    }
        
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
    
    public long getNooftransaction() {
        return nooftransaction;
    }

    public void setNooftransaction(long nooftransaction) {
        this.nooftransaction = nooftransaction;
    }
    
    public LoyaltyCard getLoyaltyCard() {
        return loyaltyCard;
    }

    public void setLoyaltyCard(LoyaltyCard loyaltyCard) {
        this.loyaltyCard = loyaltyCard;
    }
}