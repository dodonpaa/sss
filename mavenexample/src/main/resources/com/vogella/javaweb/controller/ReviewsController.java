package com.vogella.javaweb.controller;

import com.vogella.javaweb.model.Customer;
import com.vogella.javaweb.model.CustomerOrder;
import com.vogella.javaweb.model.CustomerReview;
import com.vogella.javaweb.model.Product;
import com.vogella.javaweb.repository.ProductRepository;
import com.vogella.javaweb.repository.CustomerRepository;
import com.vogella.javaweb.repository.OrderRepository;
import com.vogella.javaweb.repository.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Elfee on 12/10/17.
 */

@Controller
public class ReviewsController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping(value = "/blog", method = RequestMethod.GET)
    public String productsList(Model model){
        // TODO: parse product id and retrieve review according to the id
    	Long id = new Long(1);
    	model.addAttribute("reviews",reviewRepository.findByProduct_productId(id));
    	//model.addAttribute("products", productRepository.findAll());
        //model.addAttribute("orders", orderRepository.findAll());
        //return "orders";
        return "blog";
    }

    @RequestMapping(value="/createreview", method = RequestMethod.POST)
    @ResponseBody
    public String saveOrder(@RequestParam String firstName, @RequestParam String reviewText, @RequestParam Long productId){

        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customerRepository.save(customer);
        CustomerReview customerReview = new CustomerReview();
        
        customerReview.setCustomer(customer);
        
        Optional<Product> optionalEntityb =  productRepository.findById(productId);
        Product myproduct = optionalEntityb.get();

        
        
        customerReview.setProduct(myproduct);        
        customerReview.setReviewText(reviewText);
        reviewRepository.save(customerReview);

        return customerReview.getReviewId().toString();
    }
    
    @RequestMapping(value = "/removereview", method = RequestMethod.POST)
    @ResponseBody
    public String removeReview(@RequestParam Long Id){
        reviewRepository.deleteById(Id);
        return Id.toString();
    }
    
    @RequestMapping(value = "/getreview", method = RequestMethod.POST)
    @ResponseBody
    public String getReview(@RequestParam Long Id){
        Optional<CustomerReview> optionalEntityb =  reviewRepository.findById(Id);
        CustomerReview myreview = optionalEntityb.get();

        CustomerReview customerReview = myreview;
        if(customerReview == null)	return "Not found";
        else	return customerReview.getReviewText();
    }
}