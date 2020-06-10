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


public class DragonGlassResponse 
{
    private long size;
    
    private long totalCount;
    
    private HCSResponse[] data;
     
    public long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }
    public long getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
    
    public HCSResponse[] getData() {
        return data;
    }
    public void setData(HCSResponse[] data) {
        this.data = data;
    }
    
    @Override
    public String toString() {
        return "DragonGlassResponse [size=" + size + ", totalCount=" + totalCount + "]";
    }
}