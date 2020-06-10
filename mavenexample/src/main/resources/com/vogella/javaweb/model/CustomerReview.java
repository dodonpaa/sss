package com.vogella.javaweb.model;

import org.hibernate.annotations.Fetch;

import javax.lang.model.element.Name;
import javax.persistence.*;
import java.util.Set;

/**
 * Created by Elfee on 10/12/17.
 */

@Entity
public class CustomerReview {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reviewId;

    //private Double total;
    private String reviewText;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE} , fetch = FetchType.LAZY)
    @JoinTable(name = "REVIEW_PRODUCTS", joinColumns = {@JoinColumn(name = "REVIEW_ID")},inverseJoinColumns = {@JoinColumn(name = "PRODUCT_ID")})
    private Product product;

    @OneToOne
    private Customer customer;

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }
 
    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}