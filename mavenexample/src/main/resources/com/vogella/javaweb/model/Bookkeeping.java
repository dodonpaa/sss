package com.vogella.javaweb.model;

import javax.persistence.*;
import java.util.*;

import java.util.Collection;


public class Bookkeeping 
{
    private String user;
    
    private String points;
    
    private String amount;
    
    private String transaction;
    
    private String consensustime;
    
    public Bookkeeping(String user, String points, String amount, String transaction,String consensustime){
    	this.user = user;
    	this.points = points;
    	this.amount = amount;
    	this.transaction = transaction;
    	this.consensustime = consensustime;
    }
         
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPoints() {
        return points;
    }
    public void setPoints(String points) {
        this.points = points;
    }
    
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    public String getTransaction() {
        return transaction;
    }
    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }
    
    public String getConsensustime() {
        return consensustime;
    }
    public void setConsensustime(String consensustime) {
        this.consensustime = consensustime;
    }
 
    @Override
    public String toString() {
        return "Bookkeeping [user=" + user + ", points=" + points + ", amount=" + amount + ", transaction=" + transaction + "]";
    }
}