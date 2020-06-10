package com.vogella.javaweb.controller;

//for login hedera
import com.vogella.javaweb.service.SecurityService;
import com.vogella.javaweb.service.CustomerService;
import com.vogella.javaweb.validator.CustomerValidator;
import org.springframework.validation.BindingResult;


import com.vogella.javaweb.model.Customer;
import com.vogella.javaweb.model.CustomerOrder;
import com.vogella.javaweb.model.Product;
import com.vogella.javaweb.repository.ProductRepository;
import com.vogella.javaweb.repository.CustomerRepository;
import com.vogella.javaweb.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;

import io.github.cdimascio.dotenv.Dotenv;
import javassist.bytecode.Descriptor.Iterator;

import com.hedera.hashgraph.sdk.*;
import com.hedera.hashgraph.sdk.account.AccountId;
import com.hedera.hashgraph.sdk.crypto.ed25519.Ed25519PrivateKey;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.mirror.MirrorClient;
import com.hedera.hashgraph.sdk.TransactionId;
import com.hedera.hashgraph.sdk.consensus.ConsensusTopicId;
import com.hedera.hashgraph.sdk.consensus.ConsensusTopicCreateTransaction;
import com.hedera.hashgraph.sdk.mirror.*;
import com.hedera.hashgraph.sdk.consensus.*;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.GrantedAuthority;

import java.lang.Integer;


/**
 * Created by Elfee on 12/10/17.
 */

@Controller
public class OrdersController {
	
	// for login hedera
	//@Autowired
    //private CustomerService userService;
	
	//@Autowired
    //private SecurityService securityService;
	
	//@Autowired
    //private CustomerValidator userValidator;
	////
	


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    //@Autowired
    private CustomerRepository customerRepository;
    // Hedera Test
    //private static final AccountId OPERATOR_ID;
  //Test hedera    private static final AccountId OPERATOR_ID = AccountId.fromString("0.0.36356");
  //Test hedera   private static final Ed25519PrivateKey OPERATOR_KEY = Ed25519PrivateKey.fromString("302e020100300506032b657004220420cf856d291283ac20bc0cb6b45c86dc19179d51c37e2a5174ede3bc412bf843a3");
  //Test hedera   private static final String MIRROR_NODE_ADDRESS = "hcs.testnet.mirrornode.hedera.com:5600";
    
    // for vendorpos
    @GetMapping("/vendorpos")
    public String vpos(Model model) {
    	
    	System.out.println("vendorpos");
    	
    	/*try{
    	System.err.println("authentication user = " + authentication.getName());
    	}catch(Exception e){
    		System.err.println("error happened!");
    		e.printStackTrace();
    	}*/
        return "vendorpos";
    }
    
 // for vendorpos
    @GetMapping("/usernotfound")
    public String usernotfound(Model model) {
    	
    	System.out.println("usernotfound");
    	
    	/*try{
    	System.err.println("authentication user = " + authentication.getName());
    	}catch(Exception e){
    		System.err.println("error happened!");
    		e.printStackTrace();
    	}*/
        return "usernotfound";
    }
    
    @GetMapping("/pointsinvalid")
    public String pointsinvalid(Model model) {
    	
    	System.out.println("pointsinvalid");
    	
    	/*try{
    	System.err.println("authentication user = " + authentication.getName());
    	}catch(Exception e){
    		System.err.println("error happened!");
    		e.printStackTrace();
    	}*/
        return "pointsinvalid";
    }
    
    @RequestMapping(value="/vendorpos", method = RequestMethod.POST)
    @ResponseBody
    public String vendorpos(@RequestParam String customerid, HttpSession session){
    	System.out.println("customerid is " + customerid);
    	session.setAttribute("customerid", customerid);
    	
    	if(customerid.length()==0)	return "error";
    	
        return customerid;
    }
    
    /*
    @RequestMapping(value="/updatepoints", method = RequestMethod.POST)
    @ResponseBody
    public String updatepoints(@RequestParam String points, HttpSession session){
    	System.out.println("points is " + points);
    	
    	if(points.length()==0)	return "error";
    	
    	try{
    		Integer mypoints = Integer.parseInt(points);
    		System.out.println("mypoints = " + mypoints);
    	}catch(Exception e){
    		return "error";
    	}
    	
        return points;
    }*/
        
    // for login hedera  
    @GetMapping("/login")
    public String login(Model model) {
    	
    	/*try{
    	System.err.println("authentication user = " + authentication.getName());
    	}catch(Exception e){
    		System.err.println("error happened!");
    		e.printStackTrace();
    	}*/
        return "login";
    }
    
    // for login hedera
    @GetMapping("/vendors")
    public String vendors(Model model) {
        return "vendors";
    }

    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }
    
    
    
   // @GetMapping("/registration")
  // public String registration(Model model) {
    // model.addAttribute("userForm", new Customer());

    // return "registration";
   //}
    
    // for login hedera
    //@RequestMapping("/registration")
   // public String registration(@ModelAttribute("userForm") Customer userForm, BindingResult bindingResult) {
     //   userValidator.validate(userForm, bindingResult);
//
  //      if (bindingResult.hasErrors()) {
    //        return "registration";
      //  }
//
  //      userService.save(userForm);
//
  //      securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
//
  //      return "redirect:/welcome";
    //}
    
    // for login hedera
    //@RequestMapping("/login")
   // public String login(Model model, String error, String logout) {
     //   if (error != null)
       //     model.addAttribute("error", "Your username and password is invalid.");
//
  //      if (logout != null)
    //        model.addAttribute("message", "You have been logged out successfully.");
//
  //      return "login";
   // }
    
    // for login hedera
  //  @RequestMapping({"/", "/welcome"})
  //  public String welcome(Model model) {
   //     return "welcome";
    //}

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String productsList(Model model,Authentication authentication){
    	
    	System.out.println("productsList() is called!");
    	String myrole = "";
    	
    	
    	if(authentication == null){
    		System.out.println("authentication is null");
    	}else{
    		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    		
    		for(GrantedAuthority ga : userDetails.getAuthorities()){
    			myrole = ga.getAuthority();
    		}
    		
    		System.out.println("User has authorities: " + myrole);
    		//System.out.println("User has authorities: " + userDetails.getAuthorities());
    	
    	}
    	
        //model.addAttribute("products", productRepository.findAll());
        //model.addAttribute("orders", orderRepository.findAll());
        
        // Hedera Test
     // Grab the OPERATOR_ID and OPERATOR_KEY from the .env file
       /*private static final AccountId OPERATOR_ID = AccountId.fromString("0.0.36356");
        private static final Ed25519PrivateKey OPERATOR_KEY = Ed25519PrivateKey.fromString("302e020100300506032b657004220420cf856d291283ac20bc0cb6b45c86dc19179d51c37e2a5174ede3bc412bf843a3");
*/
      //Test hedera      final MirrorClient mirrorClient = new MirrorClient(MIRROR_NODE_ADDRESS);

        
        
        // Build Hedera testnet client        
      //Test hedera      Client client = Client.forTestnet();

        // Set the operator account ID and operator private key
      //Test hedera       client.setOperator(OPERATOR_ID, OPERATOR_KEY);
        
     // Grab the mirror node endpoint from the .env file
        //private static final String MIRROR_NODE_ADDRESS = "hcs.testnet.mirrornode.hedera.com:5600";

        // Build the mirror node client
        //final MirrorClient mirrorClient = new MirrorClient(MIRROR_NODE_ADDRESS);
        
  //Test hedera      	try{
        
      //Test hedera      		final TransactionId transactionId = new ConsensusTopicCreateTransaction().execute(client);       
      //Test hedera    		final ConsensusTopicId topicId = transactionId.getReceipt(client).getConsensusTopicId();

      //Test hedera   		System.out.println("Your topic ID is: " +topicId);
        
      //Test hedera  		Thread.sleep(9000);
       		
      //Test hedera    		new MirrorConsensusTopicQuery()
      //Test hedera           .setTopicId(topicId)
      //Test hedera           .subscribe(mirrorClient, resp -> {
      //Test hedera               String messageAsString = new String(resp.message, StandardCharsets.UTF_8);

      //Test hedera             System.out.println(resp.consensusTimestamp + " received topic message: " + messageAsString);
      //Test hedera           },
                    // On gRPC error, print the stack trace
      //Test hedera             Throwable::printStackTrace);
        		
        		//Submit a message to a topic
      //Test hedera       	    new ConsensusMessageSubmitTransaction()
      //Test hedera   	        .setTopicId(topicId)
      //Test hedera   	        .setMessage("hello, HCS! ")
      //Test hedera   	        .execute(client)
      //Test hedera   	        .getReceipt(client);  
    //test hedera   		
        		/* new MirrorConsensusTopicQuery()
        	.setTopicId(topicId)
        	.subscribe(mirrorClient, resp -> {
            	String messageAsString = new String(resp.message, StandardCharsets.UTF_8);

            	System.out.println(resp.consensusTimestamp + " received topic message: " + messageAsString);
        	},
            	// On gRPC error, print the stack trace
            	Throwable::printStackTrace);*/
      //Test hedera        }catch(Exception e){
      //Test hedera       	try{
      //Test hedera    	Thread.sleep(3000);}catch(Exception ff){}
      //Test hedera  	e.printStackTrace();
      //Test hedera   }
        
        
       
        
        
        
        
               //return "orders";
        //return "homelisting";
        //change to hybel2
        //return "hybel2";
        // hedera test
        if(myrole.length()>0 && myrole.compareTo("Vendor")==0)	return "redirect:/vendorpage?reload=true";
    	
    	return "homepage?reload=true";
        // for login hedera
       // return "welcome";
    }

    @RequestMapping(value="/createorder", method = RequestMethod.POST)
    @ResponseBody
    public String saveOrder(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String phone, @RequestParam String email, @RequestParam String address, @RequestParam String zip, @RequestParam(value="productIds[]") Long[] productIds){

        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setAddress(address);
        customer.setZip(zip);
        customerRepository.save(customer);
        CustomerOrder customerOrder = new CustomerOrder();
        
        Optional<Customer> optionalEntity =  customerRepository.findById(customer.getCustomerId());
        Customer mycustomer = optionalEntity.get();
        
        customerOrder.setCustomer(mycustomer);
        Set<Product> productSet = new HashSet<Product>();
        for (Long productId:productIds){
        	
        	Optional<Product> optionalEntitya =  productRepository.findById(productId);
            Product myproduct = optionalEntitya.get();
        	
            productSet.add(myproduct);
        }
        customerOrder.setProducts(productSet);
        Double total = 0.0;
        for (Long productId:productIds){
        	Optional<Product> optionalEntitya =  productRepository.findById(productId);
            Product myproduct = optionalEntitya.get();
        	
        	
        	
            total = total + (myproduct.getProductPrice());
        }
        customerOrder.setTotal(total);
        customerOrder.setStatus("Order Received");
        orderRepository.save(customerOrder);

        return customerOrder.getOrderId().toString();
    }
    
    @RequestMapping(value = "/removeorder", method = RequestMethod.POST)
    @ResponseBody
    public String removeOrder(@RequestParam Long Id){
        orderRepository.deleteById(Id);
        return Id.toString();
    }
    
    @RequestMapping(value = "/getorder", method = RequestMethod.POST)
    @ResponseBody
    public String getOrder(@RequestParam Long Id){
    	
    	Optional<CustomerOrder> optionalEntitya =  orderRepository.findById(Id);
        CustomerOrder myorder = optionalEntitya.get();
    	
        CustomerOrder customerOrder = myorder;
        if(customerOrder == null)	return "Not found";
        else	return customerOrder.getStatus();
    }
}