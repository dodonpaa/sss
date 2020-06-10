package com.vogella.javaweb.controller;

import com.vogella.javaweb.model.Customer;
import com.vogella.javaweb.model.CustomerOrder;
import com.vogella.javaweb.model.CustomerReview;
import com.vogella.javaweb.model.CustomerSignup;
import com.vogella.javaweb.model.Product;
import com.vogella.javaweb.repository.ProductRepository;
import com.vogella.javaweb.repository.CustomerRepository;
import com.vogella.javaweb.repository.OrderRepository;
import com.vogella.javaweb.repository.ReviewRepository;
import com.vogella.javaweb.repository.SignupRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

/**
 * Created by Elfee on 12/10/17.
 */

@Controller
public class SignupController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private SignupRepository signupRepository;

    @RequestMapping(value="/createsignup", method = RequestMethod.POST)
    @ResponseBody
    public String signup(@RequestParam String firstName, @RequestParam String lastName,@RequestParam String email,@RequestParam String phone){

        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPhone(phone);
        customerRepository.save(customer);
        CustomerSignup customerSignup = new CustomerSignup();
        
        Optional<Customer> optionalEntity =  customerRepository.findById(customer.getCustomerId());
        Customer mycustomer = optionalEntity.get();
        
        
        customerSignup.setCustomer(mycustomer);
        customerSignup.setsignupText("hello");
        signupRepository.save(customerSignup);

        return customerSignup.getSignupId().toString();
    }
    
    @RequestMapping(value = "/removesignup", method = RequestMethod.POST)
    @ResponseBody
    public String removeSignup(@RequestParam Long Id){
        signupRepository.deleteById(Id);
        return Id.toString();
    }
    
    @RequestMapping(value = "/getsignup", method = RequestMethod.POST)
    @ResponseBody
    public String getSignup(@RequestParam Long Id){
    	
    	 Optional<CustomerSignup> optionalEntity =  signupRepository.findById(Id);
         CustomerSignup customerSignup = optionalEntity.get();
    	
        //CustomerSignup customerSignup = signupRepository.findById(Id);
        if(customerSignup == null)	return "Not found";
        else	return customerSignup.getsignupText();
    }
}