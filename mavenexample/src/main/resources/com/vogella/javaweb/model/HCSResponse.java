package com.vogella.javaweb.model;

import javax.persistence.*;
import java.util.*;

import java.util.Collection;

/*
{
	  "size": 1,
	  "totalCount": 1,
	  "data": [
	    {
	      "transactionID": "XXXXXXXXXXXXXXXXXXXXXX",
	      "readableTransactionID": "0.0.accountNum@XXXXXXXXX-XXXXXX",
	      "consensusTime": "2020-05-24T12:49:18.310+0000",
	      "status": "SUCCESS",
	      "topicID": "0.0.topicNum",
	      "message": "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
	      "topicSequenceNumber": 123,
	      "topicRunningHash": "XXXXXXXXXXXXXXXXXXXXXX",
	      "submitter": "0.0.accountNum"
	    }
	  ]
	}
*/


public class HCSResponse 
{
    private String transactionID;
    
    private String readableTransactionID;
    
    private String consensusTime;
    
    private String status;
    
    private String topicID;
    
    private String message;
    
    private long topicSequenceNumber;
   
    private String topicRunningHash;
   
    private String submitter;
     
    public long getTopicSequenceNumber() {
        return topicSequenceNumber;
    }
    public void setTopicSequenceNumber(long topicSequenceNumber) {
        this.topicSequenceNumber = topicSequenceNumber;
    }
    public String getTransactionID() {
        return transactionID;
    }
    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }
    
    public String getReadableTransactionID() {
        return readableTransactionID;
    }
    public void setReadableTransactionID(String readableTransactionID) {
        this.readableTransactionID = readableTransactionID;
    }
    
    public String getConsensusTime() {
        return consensusTime;
    }
    public void setConsensusTime(String consensusTime) {
        this.consensusTime = consensusTime;
    }
 
    public String getStatus() {
        return status;
    }
    public void setStatus(String consensusTime) {
        this.status = status;
    }
 
    public String getTopicID() {
        return topicID;
    }
    public void setTopicID(String topicID) {
        this.status = topicID;
    }
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getTopicRunningHash() {
        return topicRunningHash;
    }
    public void setTopicRunningHash(String topicRunningHash) {
        this.topicRunningHash = topicRunningHash;
    }
    
    public String getSubmitter() {
        return submitter;
    }
    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }
    
    @Override
    public String toString() {
        return "HCSResponse [topic=" + topicID + ", message=" + message + "]";
    }
}