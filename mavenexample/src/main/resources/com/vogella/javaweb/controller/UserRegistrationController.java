package com.vogella.javaweb.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
// for email validation
import org.springframework.context.ApplicationEventPublisher;
import com.vogella.javaweb.model.VerificationToken;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;
import org.springframework.context.MessageSource;
import java.util.Calendar;
import com.vogella.javaweb.OnRegistrationCompleteEvent;

import com.vogella.javaweb.model.Customer;
import com.vogella.javaweb.service.CustomerService;
import com.vogella.javaweb.UserRegistrationDto;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.core.env.Environment;

import com.vogella.javaweb.repository.VerificationTokenRepository;
import com.vogella.javaweb.model.VerificationToken;

// for hedera access
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
import com.hedera.hashgraph.sdk.crypto.ed25519.Ed25519PublicKey;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.vogella.javaweb.model.DragonGlassResponse;
import com.google.gson.Gson;

import com.vogella.javaweb.model.Role;
import com.vogella.javaweb.model.CustomerLoyalty;
import com.vogella.javaweb.model.LoyaltyCard;

import com.vogella.javaweb.repository.TopicRepository;
import com.vogella.javaweb.repository.HederaSecretRepository;
import com.vogella.javaweb.model.Topic;
import com.vogella.javaweb.model.HederaSecret;
import java.util.Random;
import java.lang.StringBuilder;
import java.util.List;
import java.util.ArrayList;

import com.vogella.javaweb.model.HCSResponse;
import com.vogella.javaweb.model.Bookkeeping;
import com.vogella.javaweb.repository.RoleRepository;

import com.vogella.javaweb.model.Invitee;
import com.vogella.javaweb.repository.InviteeRepository;



@Controller
//@RequestMapping("/registration") // for email validation
public class UserRegistrationController {

    @Autowired
    private CustomerService userService;
    
    // for email validation
    @Autowired
    ApplicationEventPublisher eventPublisher;
    
    //for email validation
    @Autowired
    private MessageSource messages;
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private Environment env;
    
    @Autowired
    private TopicRepository topicRepository;
    
    @Autowired
    private HederaSecretRepository hederaSecretRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private InviteeRepository inviteeRepository;
    
    //for hedera access
    private static final AccountId OPERATOR_ID = AccountId.fromString("0.0.36356");
    private static final Ed25519PrivateKey OPERATOR_KEY = Ed25519PrivateKey.fromString("302e020100300506032b657004220420cf856d291283ac20bc0cb6b45c86dc19179d51c37e2a5174ede3bc412bf843a3");
    private static final String MIRROR_NODE_ADDRESS = "hcs.testnet.mirrornode.hedera.com:5600";

    
    //private String message;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }    
    
    //@ModelAttribute("message")
    //public String message() {
      //  return message;
    //}

    //@GetMapping for email validation
    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        return "registration";
    }
    
    //@RequestMapping(value = "/homepage", method = RequestMethod.POST)
    //public String removeOrder(@RequestParam Long Id){
    	//System.err.println("login success!!");
        //return "homepage";
    //}
    
    //@GetMapping("/homepage")
    //public String getUserPage() {
    	//System.err.println("login success!!");
    	
    	//System.out.println("homepage() is called");
    	
      //  return "homepage";
    //}
    
    //@GetMapping("/vendorpage")
    //public String getVendorPage() {
    	//System.err.println("login success!!");
      //  return "vendor/homepage";
    //}
    
    @GetMapping("/accountcreated")
    public String accountCreated() {
    	//System.err.println("login success!!");
        return "accountcreated";
    }
    
    // for expired token
    @GetMapping("/resendRegistrationToken")
    public String resendRegistrationToken(
    		  HttpServletRequest request, @RequestParam("token") String existingToken) {
    		    
    	System.err.println("resendRegistrationToken() is called");
    	
    	VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
    		     
    		    Customer user = userService.getUser(newToken.getToken());
    		    
    		    String appUrl = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
                
                System.err.println("appUrl=" + appUrl);
                
                eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, 
                		new Locale("en"),appUrl));
    		    
    		    /*    		    
    		    String appUrl = 
    		      "http://" + request.getServerName() + 
    		      ":" + request.getServerPort() + 
    		      request.getContextPath();
    		    SimpleMailMessage email = 
    		      constructResendVerificationTokenEmail(appUrl, newToken, user);
    		    mailSender.send(email);*/
    		    
    		    System.err.println("mailSender Sent.");
    		 
    		    return "redirect:/badUser2?success";
    		    
    		    //return new GenericResponse("New verification link had been sent. Please check your inbox.");
    		}
    
    @GetMapping("/verificationfailed")
    public String verificationFailed() {
    	//System.err.println("login success!!");
        return "verificationfailed";
    }
    
    @GetMapping("/error")
    public String errorpage() {
    	//System.err.println("login success!!");
        return "error";
    }
    
    @GetMapping("/pointerror")
    public String pointerrorpage() {
    	//System.err.println("login success!!");
        return "pointerror";
    }
    
    @GetMapping("/dgrerror")
    public String dgrerrorpage() {
    	//System.err.println("login success!!");
        return "dgrerror";
    }
    
    @GetMapping("/badUser2")
    public String badUser(HttpSession session) {
        //model.addAttribute("message", "hello world");
    	//System.err.println("login success!!");
        return "badUser2";
    }
       
    @GetMapping("/homepage")
    public String homepage(HttpServletRequest request, Model model, Authentication authentication, @RequestParam(value = "reload", required = false) String reload) {
    	String myrole="";
    	
		System.out.println("==========GET METHOD IS CALLED==========");
    	
    	UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    	
    	HttpSession session = request.getSession();
		
		for(GrantedAuthority ga : userDetails.getAuthorities()){
			myrole = ga.getAuthority();
		}
		
		String customerid = userDetails.getUsername();
		String vendorid = "";
		
		System.err.println("myname = " + customerid);
		
		session.setAttribute("myrole", myrole);
		session.setAttribute("myname", customerid);
		
		System.err.println("homepage is called. my role is " + myrole);
    	
		if(myrole.length()!=0 && myrole.compareTo("Vendor")==0)	return "redirect:/vendorpage";
		
		if(reload!=null && reload.length()!=0)	{
			if(model.getAttribute("allvendors") == null)	System.out.println("get attributes return null");
			else	System.out.println("model attribute not null");
			
			model.addAttribute("bookkeepings", session.getAttribute("bookkeepings"));
			model.addAttribute("allvendors", session.getAttribute("allvendors"));

			model.addAttribute("cpoints",session.getAttribute("cpoints"));
			model.addAttribute("camount",session.getAttribute("camount"));
			model.addAttribute("ctransaction",session.getAttribute("ctransaction"));
			
			return "homepage";
		}

		
    	// Step 1 Get all vendors in the database
		
		System.out.println("Step 1: Get all roles with name Vendor");
		
		List<com.vogella.javaweb.model.Role> roles = roleRepository.findByName("Vendor");
		
		System.out.println("there are " + roles.size() + " records with Role Vendor");

    	// Step 2 Get all vendors in the database
		List<Collection<com.vogella.javaweb.model.Role>> myroles = new ArrayList<Collection<com.vogella.javaweb.model.Role>>();
		
		for(Role r : roles){
			Collection<Role> myrole1 = new ArrayList<Role>();
			System.out.println("added role id = " + r.getId());			
			myrole1.add(r);
			myroles.add(myrole1);
		}
		
		
		System.out.println("Step 2: Get all vendors with role Vendor -> myroles.size = " + myroles.size());

		List<Customer> vendors = userService.getVendorByRole(myroles);
		
		for(Customer cm : vendors){
			if(vendorid.length()==0 && cm!=null){
				vendorid = cm.getEmail();
				break;
			}
			//System.out.println("vendor: " + cm.getEmail());
		}
		
		model.addAttribute("allvendors", vendors);
		session.setAttribute("allvendors", vendors);
		
		// read hedera response using dragonglass
				try{			
		    		Customer vendor = userService.getVendorByEmail(vendorid);
                	Set<Bookkeeping> bookkeepings = new HashSet<Bookkeeping>();

                	Integer total_points = 0;
                	Integer total_amount = 0;
                	Integer total_transaction = 0;
		    		
		    		for(Topic tp : vendor.getTopics()){
		    			String topicId = tp.getTopicId();
		    			//Collection<HederaSecret> hs = hederaSecretRepository.findByTopicAndCustomerId(tp,vendorid);
		    			Collection<HederaSecret> hs = hederaSecretRepository.findByTopicAndCustomerId(tp,customerid);

		        		for(HederaSecret hcs : hs){     			
		        			System.out.println("secretKey = " + hcs.getSecretKey() + " customerId = " + hcs.getCustomerId() + " HCS_id = " + hcs.getPointTopic());
		        			if(hcs.getPointTopic()!=null&&hcs.getPointTopic().length()!=0){
		        				OkHttpClient myclient = new OkHttpClient().newBuilder()
		        						.build();
		        				Request request02 = new Request.Builder()
		        						//.url("https://api.dragonglass.me/hedera/api/v1/topics/" + "0.0.35650" + "/messages")
		        						.url("https://api-testnet.dragonglass.me/hedera/api/v1/topics/" + hcs.getPointTopic() + "/messages")
		        						.method("GET", null)
		        						//.addHeader("X-API-KEY", "1eb33c6f-21c7-31f5-a0fe-8bd052ef58fd")
		        						.addHeader("X-API-KEY", "cf86a1d3-fd82-37d5-8902-3e6bb1c1e263")
		        						.build();
		        				
		        				Response response = myclient.newCall(request02).execute();
		        				okhttp3.ResponseBody responseBody = response.body();
		        			
		        				Gson gson = new Gson(); 

		        				String jsonstring = responseBody.string();
		        				
		        				DragonGlassResponse dgr = gson.fromJson(jsonstring, DragonGlassResponse.class);
		        				
		        				HCSResponse[] hcsresponse = dgr.getData();
		        				
		                    	//Set<Bookkeeping> bookkeepings = new HashSet<Bookkeeping>();
		        				if(hcsresponse == null)	continue;

		        				for(HCSResponse hcsr : hcsresponse){
		        					
		        					String message = hcsr.getMessage();
		        					//System.out.println("hcsr -> " + message);
		        		        	String messageAsString = new String(message.getBytes(), StandardCharsets.UTF_8);

		        		        	String readableText = fromHexString(messageAsString);
		        		        	
		        		        	System.out.println("received topic message: " + readableText + " at " + hcsr.getConsensusTime());

		        		        	if(isMessageValid(readableText)){
		        		        		Bookkeeping bk = createBookkeeping(readableText,hcsr.getConsensusTime());
		        		        		bookkeepings.add(bk);
		        		        		total_points+=Integer.parseInt(bk.getPoints());
		        		        		total_amount+=Integer.parseInt(bk.getAmount());
		        		        		total_transaction+=Integer.parseInt(bk.getTransaction());
		        		        		/*System.out.println("user is " + extractUser(readableText) +
		        		        		" points is " + extractPoints(readableText) + 
		        		        		" amount is " + extractAmount(readableText) +
		        		        		" transaction is " + extractTransaction(readableText));*/
		        		        	}
		        				}
		        				
		        				model.addAttribute("bookkeepings",bookkeepings);
		        				session.setAttribute("bookkeepings", bookkeepings);
		        				model.addAttribute("cpoints",total_points.toString());
		        				session.setAttribute("cpoints", total_points.toString());
		        				model.addAttribute("camount",total_amount.toString());
		        				session.setAttribute("camount", total_amount.toString());
		        				model.addAttribute("ctransaction",total_transaction.toString());
		        				session.setAttribute("ctransaction", total_transaction.toString());
		        				//System.out.println("dgr --> " + dgr.toString());
		        				//System.out.println("response OK --> " + jsonstring);
		        				
		        				response.close();
		        			}
		        		}
		    		}
				}catch(Exception e){
					System.err.println("Error while reading hedera response");
					e.printStackTrace();
				}
		
        return "homepage";
    }
    
    @RequestMapping(value="/homepage", method = RequestMethod.POST)
    @ResponseBody
    public String homepageupdate(@RequestParam String vendorid, HttpSession session, Model model, Authentication authentication) {
    	String myrole="";
    	
    	UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    	
    	//HttpSession session = request.getSession();
		
		for(GrantedAuthority ga : userDetails.getAuthorities()){
			myrole = ga.getAuthority();
		}
		
		System.out.println("==========POST METHOD IS CALLED==========");
		
		System.err.println("myname = " + userDetails.getUsername());
		
		String customerid = userDetails.getUsername();
		
	
		session.setAttribute("myrole", myrole);
		session.setAttribute("myname", userDetails.getUsername());
		
		System.err.println("homepage is called. my role is " + myrole);
    	
		if(myrole.length()!=0 && myrole.compareTo("Vendor")==0)	return "redirect:/vendorpage";
		
    	// Step 1 Get all vendors in the database
		
		System.out.println("Step 1: Get all roles with name Vendor");
		
		List<com.vogella.javaweb.model.Role> roles = roleRepository.findByName("Vendor");
		
		System.out.println("there are " + roles.size() + " records with Role Vendor");

    	// Step 2 Get all vendors in the database
		List<Collection<com.vogella.javaweb.model.Role>> myroles = new ArrayList<Collection<com.vogella.javaweb.model.Role>>();
		
		for(Role r : roles){
			Collection<Role> myrole1 = new ArrayList<Role>();
			System.out.println("added role id = " + r.getId());			
			myrole1.add(r);
			myroles.add(myrole1);
		}
		
		
		System.out.println("Step 2: Get all vendors with role Vendor -> myroles.size = " + myroles.size());

		List<Customer> vendors = userService.getVendorByRole(myroles);
		
		//for(Customer cm : vendors){
			//System.out.println("vendor: " + cm.getEmail());
		//}
		
		model.addAttribute("allvendors", vendors);
		session.setAttribute("allvendors", vendors);

		// read hedera response using dragonglass
		try{			
    		Customer vendor = userService.getVendorByEmail(vendorid);
        	Set<Bookkeeping> bookkeepings = new HashSet<Bookkeeping>();
        	
        	Integer total_points = 0;
        	Integer total_amount = 0;
        	Integer total_transaction = 0;
    		
    		for(Topic tp : vendor.getTopics()){
    			String topicId = tp.getTopicId();
    			//Collection<HederaSecret> hs = hederaSecretRepository.findByTopicAndCustomerId(tp,vendorid);
    			Collection<HederaSecret> hs = hederaSecretRepository.findByTopicAndCustomerId(tp,customerid);

        		for(HederaSecret hcs : hs){     			
        			System.out.println("secretKey = " + hcs.getSecretKey() + " customerId = " + hcs.getCustomerId() + " HCS_id = " + hcs.getPointTopic());
        			if(hcs.getPointTopic()!=null&&hcs.getPointTopic().length()!=0){
        				OkHttpClient myclient = new OkHttpClient().newBuilder()
        						.build();
        				Request request = new Request.Builder()
        						//.url("https://api.dragonglass.me/hedera/api/v1/topics/" + "0.0.35650" + "/messages")
        						.url("https://api-testnet.dragonglass.me/hedera/api/v1/topics/" + hcs.getPointTopic() + "/messages")
        						.method("GET", null)
        						//.addHeader("X-API-KEY", "1eb33c6f-21c7-31f5-a0fe-8bd052ef58fd")
        						.addHeader("X-API-KEY", "cf86a1d3-fd82-37d5-8902-3e6bb1c1e263")
        						.build();
        				
        				Response response = myclient.newCall(request).execute();
        				okhttp3.ResponseBody responseBody = response.body();
        			
        				Gson gson = new Gson(); 

        				String jsonstring = responseBody.string();
        				
        				DragonGlassResponse dgr = gson.fromJson(jsonstring, DragonGlassResponse.class);
        				
        				HCSResponse[] hcsresponse = dgr.getData();
        				if(hcsresponse == null)	continue;

                    	//Set<Bookkeeping> bookkeepings = new HashSet<Bookkeeping>();
        				
        				for(HCSResponse hcsr : hcsresponse){
        					
        					String message = hcsr.getMessage();
        					//System.out.println("hcsr -> " + message);
        		        	String messageAsString = new String(message.getBytes(), StandardCharsets.UTF_8);

        		        	String readableText = fromHexString(messageAsString);
        		        	
        		        	System.out.println("received topic message: " + readableText + " at " + hcsr.getConsensusTime());

        		        	if(isMessageValid(readableText)){
        		        		Bookkeeping bk = createBookkeeping(readableText,hcsr.getConsensusTime());
        		        		bookkeepings.add(bk);
        		        		total_points+=Integer.parseInt(bk.getPoints());
        		        		total_amount+=Integer.parseInt(bk.getAmount());
        		        		total_transaction+=Integer.parseInt(bk.getTransaction());

        		        		
        		        		/*System.out.println("user is " + extractUser(readableText) +
        		        		" points is " + extractPoints(readableText) + 
        		        		" amount is " + extractAmount(readableText) +
        		        		" transaction is " + extractTransaction(readableText));*/
        		        	}
        				}
        				
        				model.addAttribute("bookkeepings",bookkeepings);
        				session.setAttribute("bookkeepings", bookkeepings);
        				model.addAttribute("cpoints",total_points.toString());
        				session.setAttribute("cpoints", total_points.toString());
        				model.addAttribute("camount",total_amount.toString());
        				session.setAttribute("camount", total_amount.toString());
        				model.addAttribute("ctransaction",total_transaction.toString());
        				session.setAttribute("ctransaction", total_transaction.toString());

        				//System.out.println("dgr --> " + dgr.toString());
        				//System.out.println("response OK --> " + jsonstring);
        				
        				response.close();
        			}
        		}
    		}
			
			/*
			OkHttpClient myclient = new OkHttpClient().newBuilder()
					.build();
		
			Request request = new Request.Builder()
					//.url("https://api.dragonglass.me/hedera/api/v1/topics/" + "0.0.35650" + "/messages")
					.url("https://api-testnet.dragonglass.me/hedera/api/v1/topics/" + "0.0.51047" + "/messages")
					.method("GET", null)
					//.addHeader("X-API-KEY", "1eb33c6f-21c7-31f5-a0fe-8bd052ef58fd")
					.addHeader("X-API-KEY", "cf86a1d3-fd82-37d5-8902-3e6bb1c1e263")
					.build();
		
			Response response = myclient.newCall(request).execute();
			okhttp3.ResponseBody responseBody = response.body();
		
			Gson gson = new Gson(); 
		
			String jsonstring = responseBody.string();
		 
			DragonGlassResponse dgr = gson.fromJson(jsonstring, DragonGlassResponse.class);  
		 
			System.out.println("dgr --> " + dgr.toString());
			System.out.println("response OK --> " + jsonstring);
		
			response.close();*/
		}catch(Exception e){
			System.err.println("Error while reading hedera response");
			e.printStackTrace();
		}

		
		
		
		
    	//System.err.println("login success!!");
        return "homepage";
    }
    
    
    
    
    private String fromHexString(String hex) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < hex.length(); i+=2) {
            str.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
        }
        return str.toString();
    }
    
    // Streetpoints aoi@krubka.com 7 20 1
    private boolean isMessageValid(String message) {
    	int length = message.length();
    	String newmessage = message.replace(" ", "");
    	int length2 = newmessage.length();
    	
    	//System.out.println("length = " + length + " length2 = " + length2);
    	
    	int len = length - length2;
    	
    	if(len == 4){
    		//System.out.println("return true");
    		return true;
    	}
    	
    	//System.out.println("length is " + len);
    			
    	return false;
    }
    
    // Streetpoints aoi@krubka.com 7 20 1
    private String extractUser(String message) {
    	int head = message.indexOf(" ");
    	
    	//System.out.println("head = " + head);
    	 	
    	int tail = message.indexOf(" ",head+1);
    	
    	//System.out.println("tail = " + tail);
    	
    	return message.substring(head+1, tail);
    }
    
 // Streetpoints aoi@krubka.com 7 20 1
    private String extractPoints(String message) {
    	int skip = message.indexOf(" ");
    	int head = message.indexOf(" ",skip+1);
    	int tail = message.indexOf(" ",head+1);
    	
    	return message.substring(head+1, tail);
    }
    
 // Streetpoints aoi@krubka.com 7 20 1
    private String extractAmount(String message) {
    	int skip = message.indexOf(" ");
    	skip = message.indexOf(" ",skip+1);
    	int head = message.indexOf(" ",skip+1);
    	int tail = message.indexOf(" ",head+1);
    	
    	return message.substring(head+1, tail);
    }
    
    // Streetpoints aoi@krubka.com 7 20 1
    private String extractTransaction(String message) {
    	int skip = message.indexOf(" ");
    	skip = message.indexOf(" ",skip+1);
    	skip = message.indexOf(" ",skip+1);
    	
    	int head = message.indexOf(" ",skip+1);
    	//int tail = message.indexOf(" ",head+1);
    	
    	return message.substring(head+1);
    }
    
    
 // Streetpoints aoi@krubka.com 7 20 1
    private Bookkeeping createBookkeeping(String message,String consensustime) {
    	int skip = message.indexOf(" ");
    	int skip2 = message.indexOf(" ",skip+1);
    	
    	String user = message.substring(skip+1, skip2);
    	int skip3 = message.indexOf(" ",skip2+1);
    	String points = message.substring(skip2+1, skip3);
    	int skip4 = message.indexOf(" ",skip3+1);
    	String amount = message.substring(skip3+1, skip4);
    	String transaction = message.substring(skip4+1);
    	//int tail = message.indexOf(" ",head+1);
    	
    	consensustime = consensustime.replaceAll("T", "  ");
    	
    	int til = consensustime.indexOf(".");
    	
    	consensustime = consensustime.substring(0, til);
    	
    	//System.out.println("user = " + user + " points = " + points + " amount = " + amount + " transaction = " + transaction);
    	
    	return new Bookkeeping(user,points,amount,transaction,consensustime);
    }
    
 // added session & vendor id 03.06.2020
    @PostMapping("/vendorpage")
    public String vendorpagepost(Model model, Authentication authentication, HttpSession session ) {
String myrole="";
    	
    	UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		for(GrantedAuthority ga : userDetails.getAuthorities()){
			myrole = ga.getAuthority();
		}
		
		System.err.println("vendorpage is called. my role is " + myrole);

		//

		if(myrole.length()!=0 && myrole.compareTo("Customer")==0)	return "redirect:/homepage";
    	
		// added vendorid
		
		String vendorid = userDetails.getUsername();
		session.setAttribute("vendorid", vendorid);
		
		System.out.println("added session attribute vendorid = " + userDetails.getUsername());
		
				
		// Step 1 Get all vendors in the database
		
		System.out.println("Step 1: Get all roles with name Vendor");
				
		List<com.vogella.javaweb.model.Role> roles = roleRepository.findByName("Vendor");
				
	    System.out.println("there are " + roles.size() + " records with Role Vendor");

		// Step 2 Get all vendors in the database
		List<Collection<com.vogella.javaweb.model.Role>> myroles = new ArrayList<Collection<com.vogella.javaweb.model.Role>>();
				
		for(Role r : roles){
			Collection<Role> myrole1 = new ArrayList<Role>();
			System.out.println("added role id = " + r.getId());			
			myrole1.add(r);
			myroles.add(myrole1);
		}
				
				
		System.out.println("Step 2: Get all vendors with role Vendor -> myroles.size = " + myroles.size());

		List<Customer> vendors = userService.getVendorByRole(myroles);
				
		for(Customer cm : vendors){
			if(vendorid.length()==0 && cm!=null){
				vendorid = cm.getEmail();
				break;
			}
					//System.out.println("vendor: " + cm.getEmail());
		}
				
		model.addAttribute("allvendors", vendors);
		session.setAttribute("allvendors", vendors);
		
		Integer total_points=0;
		Integer total_amount=0;
		Integer total_transaction=0;
		
		
		// read hedera response using dragonglass
		try{			
    		Customer vendor = userService.getVendorByEmail(vendorid);
        	Set<Bookkeeping> bookkeepings = new HashSet<Bookkeeping>();
    		
    		for(Topic tp : vendor.getTopics()){
    			String topicId = tp.getTopicId();
    			//Collection<HederaSecret> hs = hederaSecretRepository.findByTopicAndCustomerId(tp,vendorid);
    			Collection<HederaSecret> hs = hederaSecretRepository.findByTopic(tp);

        		for(HederaSecret hcs : hs){     			
        			System.out.println("secretKey = " + hcs.getSecretKey() + " customerId = " + hcs.getCustomerId() + " HCS_id = " + hcs.getPointTopic());
        			if(hcs.getPointTopic()!=null&&hcs.getPointTopic().length()!=0){
        				OkHttpClient myclient = new OkHttpClient().newBuilder()
        						.build();
        				Request request = new Request.Builder()
        						//.url("https://api.dragonglass.me/hedera/api/v1/topics/" + "0.0.35650" + "/messages")
        						.url("https://api-testnet.dragonglass.me/hedera/api/v1/topics/" + hcs.getPointTopic() + "/messages")
        						.method("GET", null)
        						//.addHeader("X-API-KEY", "1eb33c6f-21c7-31f5-a0fe-8bd052ef58fd")
        						.addHeader("X-API-KEY", "cf86a1d3-fd82-37d5-8902-3e6bb1c1e263")
        						.build();
        				
        				Response response = myclient.newCall(request).execute();
        				okhttp3.ResponseBody responseBody = response.body();
        			
        				Gson gson = new Gson(); 

        				String jsonstring = responseBody.string();
        				
        				DragonGlassResponse dgr = gson.fromJson(jsonstring, DragonGlassResponse.class);
        				
        				HCSResponse[] hcsresponse = dgr.getData();
        				if(hcsresponse == null)	continue;

                    	//Set<Bookkeeping> bookkeepings = new HashSet<Bookkeeping>();
        				if(hcsresponse == null)	continue;
        					
        				for(HCSResponse hcsr : hcsresponse){
        					
        					String message = hcsr.getMessage();
        					//System.out.println("hcsr -> " + message);
        		        	String messageAsString = new String(message.getBytes(), StandardCharsets.UTF_8);

        		        	String readableText = fromHexString(messageAsString);
        		        	
        		        	System.out.println("received topic message: " + readableText + " at " + hcsr.getConsensusTime());

        		        	if(isMessageValid(readableText)){
        		        		Bookkeeping bk = createBookkeeping(readableText,hcsr.getConsensusTime());
        		        		//bookkeepings.add(bk);
        		        		
        		        		if(hcs.getCustomerId().compareTo(tp.getCreator())==0){
        		        			total_points+=Integer.parseInt(bk.getPoints());
        		        			total_amount+=Integer.parseInt(bk.getAmount());
        		        			total_transaction+=Integer.parseInt(bk.getTransaction());
        		        		}else	bookkeepings.add(bk);
        		        		
        		        		/*System.out.println("user is " + extractUser(readableText) +
        		        		" points is " + extractPoints(readableText) + 
        		        		" amount is " + extractAmount(readableText) +
        		        		" transaction is " + extractTransaction(readableText));*/
        		        	}
        				}
        				

        				//System.out.println("dgr --> " + dgr.toString());
        				//System.out.println("response OK --> " + jsonstring);
        				
        				response.close();
        			}
        		}
        		
				model.addAttribute("bookkeepings",bookkeepings);
				session.setAttribute("bookkeepings",bookkeepings);

				model.addAttribute("tpoints",total_points.toString());
				session.setAttribute("tpoints", total_points.toString());
				model.addAttribute("tamount",total_amount.toString());
				session.setAttribute("tamount", total_amount.toString());
				model.addAttribute("ttransaction",total_transaction.toString());
				session.setAttribute("ttransaction", total_transaction.toString());
				
    		}
			
			/*
			OkHttpClient myclient = new OkHttpClient().newBuilder()
					.build();
		
			Request request = new Request.Builder()
					//.url("https://api.dragonglass.me/hedera/api/v1/topics/" + "0.0.35650" + "/messages")
					.url("https://api-testnet.dragonglass.me/hedera/api/v1/topics/" + "0.0.51047" + "/messages")
					.method("GET", null)
					//.addHeader("X-API-KEY", "1eb33c6f-21c7-31f5-a0fe-8bd052ef58fd")
					.addHeader("X-API-KEY", "cf86a1d3-fd82-37d5-8902-3e6bb1c1e263")
					.build();
		
			Response response = myclient.newCall(request).execute();
			okhttp3.ResponseBody responseBody = response.body();
		
			Gson gson = new Gson(); 
		
			String jsonstring = responseBody.string();
		 
			DragonGlassResponse dgr = gson.fromJson(jsonstring, DragonGlassResponse.class);  
		 
			System.out.println("dgr --> " + dgr.toString());
			System.out.println("response OK --> " + jsonstring);
		
			response.close();*/
		}catch(Exception e){
			System.err.println("Error while reading hedera response");
			e.printStackTrace();
		}
		
		
		
		//final MirrorClient mirrorClient = new MirrorClient(MIRROR_NODE_ADDRESS);
		
		// Build Hedera testnet client 
	/*	Client client = Client.forTestnet();
		client.setOperator(OPERATOR_ID, OPERATOR_KEY);
				
		try{
        
			final TransactionId transactionId = new ConsensusTopicCreateTransaction().execute(client);       
			final ConsensusTopicId topicId = transactionId.getReceipt(client).getConsensusTopicId();

			System.out.println("Your topic ID is: " +topicId);
			
	        
			//waiting for consensus to reach mirror node
       	    System.out.println("waiting for consensus to reach mirrornode");
       	    
	        Thread.sleep(9000);
	  */     		
	      /*  new MirrorConsensusTopicQuery()
	        .setTopicId(topicId)
	        .subscribe(mirrorClient, resp -> {
	        	String messageAsString = new String(resp.message, StandardCharsets.UTF_8);

	        	System.out.println(resp.consensusTimestamp + " received topic message: " + messageAsString);
	                },
	                    // On gRPC error, print the stack trace
	                 Throwable::printStackTrace);*/
	        		
       	/*    System.out.println("after wait");
		
			//Submit a message to a topic
       	    new ConsensusMessageSubmitTransaction()
  	        .setTopicId(topicId)
   	        .setMessage("hello, HCS! ")
  	        .execute(client)
          .getReceipt(client);  
       	    
	        Thread.sleep(9000);

			// test dragonglass api
			OkHttpClient myclient = new OkHttpClient().newBuilder()
					.build();
			
			Request request = new Request.Builder()
					//.url("https://api.dragonglass.me/hedera/api/v1/topics/" + "0.0.35650" + "/messages")
					.url("https://api-testnet.dragonglass.me/hedera/api/v1/topics/" + "0.0.51047" + "/messages")
					.method("GET", null)
					//.addHeader("X-API-KEY", "1eb33c6f-21c7-31f5-a0fe-8bd052ef58fd")
					.addHeader("X-API-KEY", "cf86a1d3-fd82-37d5-8902-3e6bb1c1e263")
					.build();
			
			Response response = myclient.newCall(request).execute();
			okhttp3.ResponseBody responseBody = response.body();
			
			Gson gson = new Gson(); 
			
			String jsonstring = responseBody.string();
			 
			DragonGlassResponse dgr = gson.fromJson(jsonstring, DragonGlassResponse.class);  
			 
			System.out.println("dgr --> " + dgr.toString());
			System.out.println("response OK --> " + jsonstring);
			
			response.close();
			
	            }catch(Exception e){
	             	try{
	         	Thread.sleep(3000);}catch(Exception ff){}
	       	e.printStackTrace();
	        }
		*/
	
        return "vendorpage";
    }
    
    
    
    
    // added session & vendor id 03.06.2020
    @GetMapping("/vendorpage")
    public String vendorpage(Model model, Authentication authentication, HttpSession session,@RequestParam(value = "reload", required = false) String reload, @RequestParam(value = "smsg", required = false) String smsg ) {
String myrole="";
    	
    	UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		for(GrantedAuthority ga : userDetails.getAuthorities()){
			myrole = ga.getAuthority();
		}
		
		System.err.println("vendorpage is called. my role is " + myrole);

		//

		if(myrole.length()!=0 && myrole.compareTo("Customer")==0)	return "redirect:/homepage";
    	
		// added vendorid
		
		String vendorid = userDetails.getUsername();
		session.setAttribute("vendorid", vendorid);
		
		System.out.println("added session attribute vendorid = " + userDetails.getUsername());
		
		
		if(reload!=null && reload.length()!=0)	{
			if(model.getAttribute("allvendors") == null)	System.out.println("get attributes return null");
			else	System.out.println("model attribute not null");
			
			model.addAttribute("bookkeepings", session.getAttribute("bookkeepings"));
			model.addAttribute("allvendors", session.getAttribute("allvendors"));
			model.addAttribute("tpoints", session.getAttribute("tpoints"));
			model.addAttribute("tamount", session.getAttribute("tamount"));
			model.addAttribute("ttransaction", session.getAttribute("ttransaction"));

			if(smsg!=null) System.out.println("smsg is " + smsg);
			else	System.out.println("smsg is null");
			
			if(smsg!=null&&smsg.length()!=0){
				model.addAttribute("smsg",smsg);
			}
			return "vendorpage";
		}

		
		// Step 1 Get all vendors in the database
		
		System.out.println("Step 1: Get all roles with name Vendor");
				
		List<com.vogella.javaweb.model.Role> roles = roleRepository.findByName("Vendor");
				
	    System.out.println("there are " + roles.size() + " records with Role Vendor");

		// Step 2 Get all vendors in the database
		List<Collection<com.vogella.javaweb.model.Role>> myroles = new ArrayList<Collection<com.vogella.javaweb.model.Role>>();
				
		for(Role r : roles){
			Collection<Role> myrole1 = new ArrayList<Role>();
			System.out.println("added role id = " + r.getId());			
			myrole1.add(r);
			myroles.add(myrole1);
		}
				
				
		System.out.println("Step 2: Get all vendors with role Vendor -> myroles.size = " + myroles.size());

		List<Customer> vendors = userService.getVendorByRole(myroles);
				
		for(Customer cm : vendors){
			if(vendorid.length()==0 && cm!=null){
				vendorid = cm.getEmail();
				break;
			}
					//System.out.println("vendor: " + cm.getEmail());
		}
				
		model.addAttribute("allvendors", vendors);
		session.setAttribute("allvendors", vendors);
		
		Integer total_points=0;
		Integer total_amount=0;
		Integer total_transaction=0;
		
		
		// read hedera response using dragonglass
		try{			
    		Customer vendor = userService.getVendorByEmail(vendorid);
        	Set<Bookkeeping> bookkeepings = new HashSet<Bookkeeping>();
    		
    		for(Topic tp : vendor.getTopics()){
    			String topicId = tp.getTopicId();
    			//Collection<HederaSecret> hs = hederaSecretRepository.findByTopicAndCustomerId(tp,vendorid);
    			Collection<HederaSecret> hs = hederaSecretRepository.findByTopic(tp);

        		for(HederaSecret hcs : hs){     			
        			System.out.println("secretKey = " + hcs.getSecretKey() + " customerId = " + hcs.getCustomerId() + " HCS_id = " + hcs.getPointTopic());
        			if(hcs.getPointTopic()!=null&&hcs.getPointTopic().length()!=0){
        				OkHttpClient myclient = new OkHttpClient().newBuilder()
        						.build();
        				Request request = new Request.Builder()
        						//.url("https://api.dragonglass.me/hedera/api/v1/topics/" + "0.0.35650" + "/messages")
        						.url("https://api-testnet.dragonglass.me/hedera/api/v1/topics/" + hcs.getPointTopic() + "/messages")
        						.method("GET", null)
        						//.addHeader("X-API-KEY", "1eb33c6f-21c7-31f5-a0fe-8bd052ef58fd")
        						.addHeader("X-API-KEY", "cf86a1d3-fd82-37d5-8902-3e6bb1c1e263")
        						.build();
        				
        				Response response = myclient.newCall(request).execute();
        				okhttp3.ResponseBody responseBody = response.body();
        			
        				Gson gson = new Gson(); 

        				String jsonstring = responseBody.string();
        				
        				DragonGlassResponse dgr = gson.fromJson(jsonstring, DragonGlassResponse.class);
        				
        				HCSResponse[] hcsresponse = dgr.getData();
        				
        				if(hcsresponse == null)	continue;
        				
                    	//Set<Bookkeeping> bookkeepings = new HashSet<Bookkeeping>();
        				
        				for(HCSResponse hcsr : hcsresponse){
        					
        					String message = hcsr.getMessage();
        					//System.out.println("hcsr -> " + message);
        		        	String messageAsString = new String(message.getBytes(), StandardCharsets.UTF_8);

        		        	String readableText = fromHexString(messageAsString);
        		        	
        		        	System.out.println("received topic message: " + readableText + " at " + hcsr.getConsensusTime());

        		        	if(isMessageValid(readableText)){
        		        		Bookkeeping bk = createBookkeeping(readableText,hcsr.getConsensusTime());
        		        		//bookkeepings.add(bk);
        		        		
        		        		if(hcs.getCustomerId().compareTo(tp.getCreator())==0){
        		        			total_points+=Integer.parseInt(bk.getPoints());
        		        			total_amount+=Integer.parseInt(bk.getAmount());
        		        			total_transaction+=Integer.parseInt(bk.getTransaction());
        		        		}else	bookkeepings.add(bk);
        		        		
        		        		/*System.out.println("user is " + extractUser(readableText) +
        		        		" points is " + extractPoints(readableText) + 
        		        		" amount is " + extractAmount(readableText) +
        		        		" transaction is " + extractTransaction(readableText));*/
        		        	}
        				}
        				

        				//System.out.println("dgr --> " + dgr.toString());
        				//System.out.println("response OK --> " + jsonstring);
        				
        				response.close();
        			}
        		}
        		
				model.addAttribute("bookkeepings",bookkeepings);
				session.setAttribute("bookkeepings",bookkeepings);

				model.addAttribute("tpoints",total_points.toString());
				session.setAttribute("tpoints", total_points.toString());
				model.addAttribute("tamount",total_amount.toString());
				session.setAttribute("tamount", total_amount.toString());
				model.addAttribute("ttransaction",total_transaction.toString());
				session.setAttribute("ttransaction", total_transaction.toString());
				
    		}
			
			/*
			OkHttpClient myclient = new OkHttpClient().newBuilder()
					.build();
		
			Request request = new Request.Builder()
					//.url("https://api.dragonglass.me/hedera/api/v1/topics/" + "0.0.35650" + "/messages")
					.url("https://api-testnet.dragonglass.me/hedera/api/v1/topics/" + "0.0.51047" + "/messages")
					.method("GET", null)
					//.addHeader("X-API-KEY", "1eb33c6f-21c7-31f5-a0fe-8bd052ef58fd")
					.addHeader("X-API-KEY", "cf86a1d3-fd82-37d5-8902-3e6bb1c1e263")
					.build();
		
			Response response = myclient.newCall(request).execute();
			okhttp3.ResponseBody responseBody = response.body();
		
			Gson gson = new Gson(); 
		
			String jsonstring = responseBody.string();
		 
			DragonGlassResponse dgr = gson.fromJson(jsonstring, DragonGlassResponse.class);  
		 
			System.out.println("dgr --> " + dgr.toString());
			System.out.println("response OK --> " + jsonstring);
		
			response.close();*/
		}catch(Exception e){
			System.err.println("Error while reading hedera response");
			e.printStackTrace();
		}
		
		
		
		//final MirrorClient mirrorClient = new MirrorClient(MIRROR_NODE_ADDRESS);
		
		// Build Hedera testnet client 
	/*	Client client = Client.forTestnet();
		client.setOperator(OPERATOR_ID, OPERATOR_KEY);
				
		try{
        
			final TransactionId transactionId = new ConsensusTopicCreateTransaction().execute(client);       
			final ConsensusTopicId topicId = transactionId.getReceipt(client).getConsensusTopicId();

			System.out.println("Your topic ID is: " +topicId);
			
	        
			//waiting for consensus to reach mirror node
       	    System.out.println("waiting for consensus to reach mirrornode");
       	    
	        Thread.sleep(9000);
	  */     		
	      /*  new MirrorConsensusTopicQuery()
	        .setTopicId(topicId)
	        .subscribe(mirrorClient, resp -> {
	        	String messageAsString = new String(resp.message, StandardCharsets.UTF_8);

	        	System.out.println(resp.consensusTimestamp + " received topic message: " + messageAsString);
	                },
	                    // On gRPC error, print the stack trace
	                 Throwable::printStackTrace);*/
	        		
       	/*    System.out.println("after wait");
		
			//Submit a message to a topic
       	    new ConsensusMessageSubmitTransaction()
  	        .setTopicId(topicId)
   	        .setMessage("hello, HCS! ")
  	        .execute(client)
          .getReceipt(client);  
       	    
	        Thread.sleep(9000);

			// test dragonglass api
			OkHttpClient myclient = new OkHttpClient().newBuilder()
					.build();
			
			Request request = new Request.Builder()
					//.url("https://api.dragonglass.me/hedera/api/v1/topics/" + "0.0.35650" + "/messages")
					.url("https://api-testnet.dragonglass.me/hedera/api/v1/topics/" + "0.0.51047" + "/messages")
					.method("GET", null)
					//.addHeader("X-API-KEY", "1eb33c6f-21c7-31f5-a0fe-8bd052ef58fd")
					.addHeader("X-API-KEY", "cf86a1d3-fd82-37d5-8902-3e6bb1c1e263")
					.build();
			
			Response response = myclient.newCall(request).execute();
			okhttp3.ResponseBody responseBody = response.body();
			
			Gson gson = new Gson(); 
			
			String jsonstring = responseBody.string();
			 
			DragonGlassResponse dgr = gson.fromJson(jsonstring, DragonGlassResponse.class);  
			 
			System.out.println("dgr --> " + dgr.toString());
			System.out.println("response OK --> " + jsonstring);
			
			response.close();
			
	            }catch(Exception e){
	             	try{
	         	Thread.sleep(3000);}catch(Exception ff){}
	       	e.printStackTrace();
	        }
		*/
		
		
    	//System.err.println("login success!!");
		if(smsg!=null&&smsg.length()!=0){
			model.addAttribute("smsg",smsg);
		}
		
        return "vendorpage";
    }

    // binding cancel
    @RequestMapping(value="/bindingCancel", method = RequestMethod.POST)
    @ResponseBody
    public String bindingCancel
      (final HttpServletRequest request, HttpSession session, final Model model,  @RequestParam("email") final String email) {
      
        //Locale locale = request.getLocale();
    	String myid = (String)session.getAttribute("vendorid");
    	
    	// don't allow to cancel self
    	if(myid.compareTo(email)==0)	return "redirect:/vendorpage?reload=true&smsg=cannotCancelSelf";
    	
    	Customer invitor = userService.getVendorByEmail(myid);
    	Customer invitee = userService.getVendorByEmail(email);
    	
    	
		//remove topic of invitee
				
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(invitee);
				
        Collection<Topic> topics = topicRepository.findByCustomersIn(customers);
            	            	
        for(Topic topic : topics){
        	if(topic.getCreator().compareTo(email)==0)	invitor.removeTopic(topic);
        }
            	
        //remove topic of invitor
        List<Customer> customers01 = new ArrayList<Customer>();
        customers01.add(invitor);
        Collection<Topic> topics01 = topicRepository.findByCustomersIn(customers01);
            	
        for(Topic topic01 : topics01){
           if(topic01.getCreator().compareTo(myid)==0)	invitee.removeTopic(topic01);
        }
            	
            	
       userService.saveRegisteredUser(invitee);
       userService.saveRegisteredUser(invitor);
       
       // send email to inform invitor
   		try{
   			final SimpleMailMessage epost = constructEmailMessage04(email,myid);
   			mailSender.send(epost);
   			System.out.println("cancel sent");
   		}catch(Exception e){
   			System.out.println("error while sending cancel email");
   			e.printStackTrace();
   		}
			               
       return "redirect:/vendorpage?reload=true&smsg=bindingCancel";       
    }
    
  //for email validation
    @GetMapping("/bindingConfirm")
    public String bindingConfirm
      (final HttpServletRequest request, HttpSession session, final Model model, @RequestParam("inviteid") final String inviteid, @RequestParam("email") final String email) {
      
        //Locale locale = request.getLocale();
    	String myid = (String)session.getAttribute("vendorid");
    	Customer invitee = userService.getVendorByEmail(myid);
    	Customer invitor = userService.getVendorByEmail(email);
    	
    	Collection<Invitee> invitees = inviteeRepository.findByCustomerAndInviteId(invitor,inviteid);

    	if(invitees == null || invitees.size()==0){
    		System.out.println("request invalid. cannot find invitees to match with request");
    		return "redirect:/error";
    	}
    	
    	 // if invitee found
    	List<Long> ids = new ArrayList<Long>();
    	
        for(Invitee invitee01 : invitees){
			if(invitee01.getInviteId().compareTo(inviteid) == 0){
				//bind topic with invitee
				ids.add(invitee01.getId());
				
				List<Customer> customers = new ArrayList<Customer>();
				customers.add(invitor);
				
            	Collection<Topic> topics = topicRepository.findByCustomersIn(customers);
            	
            	System.out.println("added topic to repository");

            	//invitee.setTopics(topics); 
            	
            	for(Topic topic : topics){
            		invitee.setTopic(topic);
            	}
            	
            	//bind topic with invitor
            	List<Customer> customers01 = new ArrayList<Customer>();
            	customers01.add(invitee);
            	Collection<Topic> topics01 = topicRepository.findByCustomersIn(customers01);

            	//invitor.setTopics(topics01); 
            	
            	for(Topic topic01 : topics01){
            		invitor.setTopic(topic01);
            	}
            	
            	
            	userService.saveRegisteredUser(invitee);
            	userService.saveRegisteredUser(invitor);

				break;
			}
		}  
        
        for(Long id : ids){
        	// remove from invitee repository
    		userService.removeInvitee(id);
    	}
        
        // send email to inform invitor
    	try{
    		final SimpleMailMessage epost = constructEmailMessage03(email,myid);
    		mailSender.send(epost);
        	System.out.println("confirmation sent");
    	}catch(Exception e){
    		System.out.println("error while sending confirmation email");
    		e.printStackTrace();
    	}

    	//model.addAttribute("smsg","binding success.");
                
        return "redirect:/vendorpage?smsg=bindingSuccess";       
    }
    
    
    
    
    //for email validation
    @GetMapping("/registrationConfirm")
    public String confirmRegistration
      (final HttpServletRequest request, final Model model, @RequestParam("token") final String token) {
      
        //Locale locale = request.getLocale();
         
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            String message = "Invalid token";
        	//String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "redirect:/verificationfailed";
            //return "redirect:/badUser?lang=" + locale.getLanguage();
        }
         
        Customer user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        
        //testing token regen
        //if(true){
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
        	String messageValue = "Your registration token has expired. Please regenerate a new token.";
        	//String messageValue = messages.getMessage("auth.message.expired", null, locale);
            //model.addAttribute("message", messageValue);
        	HttpSession session = request.getSession();
        	session.setAttribute("message", messageValue);        	
            session.setAttribute("expired",true);
            session.setAttribute("token", token);
        	
            //to support expired token
            //model.addAttribute("expired",true);
            //model.addAttribute("token",token);
            
            System.err.println("redirect to badUser2");
            
            // testing token regen
            return "redirect:/badUser2";
            //return "redirect:/verificationfailed";
            
            //return "redirect:/badUser?lang=" + locale.getLanguage();
        } 
         
        user.setEnabled(true);
        
        String myrole = "";
       
        // if user is vendor then proceed with hedera setup
        for(Role role : user.getRoles()){
			myrole = role.getName();
		}
        
        if(myrole.compareTo("Vendor") == 0){
        
        // create topic id and send to hedera
    
        	// Build Hedera testnet client 
     		Client client = Client.forTestnet();
     		client.setOperator(OPERATOR_ID, OPERATOR_KEY);
     				
     		try{
             
     			final TransactionId transactionId = new ConsensusTopicCreateTransaction().execute(client);       
     			final ConsensusTopicId topicId = transactionId.getReceipt(client).getConsensusTopicId();

     			System.out.println("Your topic ID is: " +topicId);
     			     	        
            	System.out.println("waiting for consensus to reach mirrornode");
            	    
     	        Thread.sleep(9000);
     	        		
            	//System.out.println("after wait");
            	    
     			// save topic id, this is the main topic id for this vendor
            	System.out.println("set topic id to Topic" + topicId.toString());

            	Topic topic = new Topic(topicId.toString(),user.getEmail());
            	topicRepository.save(topic);
            	
            	System.out.println("added topic to repository");

            	Set<Topic> topics = new HashSet<Topic>();
            	topics.add(topic);
            	user.setTopics(topics); 
            	
            	// for debug only
            	//System.out.println("topics size is " + user.getTopics().size());
            	
            	user.setTopic(topic);
            	
            //	System.out.println("topics size is " + user.getTopics().size());
            	
            	//System.out.println("added topic to user");
     			
     	            }catch(Exception e){
     	             	try{
     	         	Thread.sleep(3000);}catch(Exception ff){}
     	       	e.printStackTrace();
     	        }
        
        }
        
     // for debug only
    	/*for(Topic tp : user.getTopics()){
			System.out.println("2 topics is " + tp.getTopicId());
		}*/
        
        
        
        userService.saveRegisteredUser(user); 
                
        return "redirect:/accountcreated";
        
        //return "redirect:/login";
        //return "redirect:/login?lang=" + request.getLocale().getLanguage(); 
    }
    
    private String randomString(){
    	int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
     
        String generatedString = random.ints(leftLimit, rightLimit + 1)
          .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
          .limit(targetStringLength)
          .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
          .toString();
     
        System.out.println("generated Secret Key = " + generatedString);
        
        return generatedString;
    }
    
    private Ed25519PrivateKey  genSecretKey() {
      try{	
    	
      return Ed25519PrivateKey.generate();
      }catch(Exception e){
    	  System.err.println("error while generating secret key");
    	  e.printStackTrace();
      }
      /*  int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
     
        String generatedString = random.ints(leftLimit, rightLimit + 1)
          .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
          .limit(targetStringLength)
          .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
          .toString();
     
        System.out.println("generated Secret Key = " + generatedString);
        
        return generatedString;*/
      return null;
    }
    
    private String buildHederaPayload(String secretKey, String customerId, String points, String amount, String transaction) {
        // use space as delimeter     
        return secretKey + " " + customerId + " " + points + " " + amount + " " + transaction;
    }
    
    @RequestMapping(value="/binding", method = RequestMethod.POST)
    @ResponseBody
    public String binding(@RequestParam String vendorid,HttpSession session,HttpServletRequest request){    	
    	if(vendorid==null || vendorid.length()==0)	return "error";
    	
    	String myid = (String)session.getAttribute("vendorid");
    	Customer me = userService.getVendorByEmail(myid);
    	String inviteeid = vendorid;
    	
    	Collection<Invitee> invitees = inviteeRepository.findByCustomerAndInviteeId(me, inviteeid);
    	// can invite only once
    	if(invitees!=null && invitees.size()>0)	return "vendorpage?reload=true&smsg=invitationSent";
    	
	    String appUrl = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
    	
    	String inviteid = randomString();
    	
        final String inviteUrl = appUrl + "/bindingConfirm?inviteid=" + inviteid + "&email=" + myid;

    	System.out.println("Me: " + me.getEmail() + " send request to " + inviteeid + " with inviteid = " + inviteid);

    	Invitee invitee = new Invitee(inviteid, inviteeid, me);
    	inviteeRepository.save(invitee);

    	try{
    		final SimpleMailMessage email = constructEmailMessage02(me.getEmail(), inviteeid, inviteUrl);
    		mailSender.send(email);
        	System.out.println("invitation sent");
    	}catch(Exception e){
    		System.out.println("error while sending email invite");
    		e.printStackTrace();
    	}
    	
        return "vendorpage?reload=true&smsg=invitationSent";
    }
    
    
    @RequestMapping(value="/updatepoints", method = RequestMethod.POST)
    @ResponseBody
    public String updatepoints(@RequestParam String points, @RequestParam String amount,HttpSession session){
    	System.out.println("points is " + points + "amount is " + amount);
    	
    	if(points.length()==0)	return "error";
    	
    	try{
    		Integer mypoints = Integer.parseInt(points);
    		Integer myamount = Integer.parseInt(amount);
    		System.out.println("mypoints = " + mypoints);
    		
    		// write to hedera
    		String customerId = (String)session.getAttribute("customerid");
    		System.out.println("update points for customer " + customerId);
    		
    		String vendorId = (String)session.getAttribute("vendorid");
    		System.out.println("update points by vendor " + vendorId);
    		
    		Customer vendor = userService.getVendorByEmail(vendorId);
    		
    		// check first whether the point is minus
    		Integer vendor1_currentpoints=0;
			Integer vendor1_currentamount=0;
			Integer vendor1_currenttransaction=0;
        	
			Integer customer1_currentpoints=0;
			Integer customer1_currentamount=0;
			Integer customer1_currenttransaction=0;
    		
    		for(Topic tp : vendor.getTopics()){
    			// 0. get the old value first	
    	        System.out.println("0 getting old value...");
    	        
    	        try{
    	        	
	    			String topicId = tp.getTopicId();
	    			//Collection<HederaSecret> hs = hederaSecretRepository.findByTopicAndCustomerId(tp,vendorid);
	    			//Collection<HederaSecret> hs = hederaSecretRepository.findByTopicAndCustomerId(tp,vendorId);
	    			Collection<HederaSecret> hs = hederaSecretRepository.findByTopic(tp);
                	Set<Bookkeeping> bookkeepings = new HashSet<Bookkeeping>();
	    			
                	
	    			
	    			for(HederaSecret hcs : hs){     
	        			if(hcs.getCustomerId().compareTo(customerId)==0||hcs.getCustomerId().compareTo(vendorId)==0){
	        			
	        			System.out.println("secretKey = " + hcs.getSecretKey() + " customerId = " + hcs.getCustomerId() + " HCS_id = " + hcs.getPointTopic());
	        			if(hcs.getPointTopic()!=null&&hcs.getPointTopic().length()!=0){
	        				OkHttpClient myclient = new OkHttpClient().newBuilder()
	        						.build();
	        				Request request02 = new Request.Builder()
	        						//.url("https://api.dragonglass.me/hedera/api/v1/topics/" + "0.0.35650" + "/messages")
	        						.url("https://api-testnet.dragonglass.me/hedera/api/v1/topics/" + hcs.getPointTopic() + "/messages")
	        						.method("GET", null)
	        						//.addHeader("X-API-KEY", "1eb33c6f-21c7-31f5-a0fe-8bd052ef58fd")
	        						.addHeader("X-API-KEY", "cf86a1d3-fd82-37d5-8902-3e6bb1c1e263")
	        						.build();
	        				
	        				Response response = myclient.newCall(request02).execute();
	        				okhttp3.ResponseBody responseBody = response.body();
	        			
	        				Gson gson = new Gson(); 

	        				String jsonstring = responseBody.string();
	        				
	        				DragonGlassResponse dgr = gson.fromJson(jsonstring, DragonGlassResponse.class);
	        				
	        				HCSResponse[] hcsresponse = dgr.getData();
	        				if(hcsresponse == null)	continue;

	        				
	        				for(HCSResponse hcsr : hcsresponse){
	        					
	        					String message = hcsr.getMessage();
	        					//System.out.println("hcsr -> " + message);
	        		        	String messageAsString = new String(message.getBytes(), StandardCharsets.UTF_8);

	        		        	String readableText = fromHexString(messageAsString);
	        		        	
	        		        	System.out.println("received topic message: " + readableText + " at " + hcsr.getConsensusTime());

	        		        	if(isMessageValid(readableText)){
	        		        		Bookkeeping bk = createBookkeeping(readableText,hcsr.getConsensusTime());
	        		        		bookkeepings.add(bk);
	        		        		/*System.out.println("user is " + extractUser(readableText) +
	        		        		" points is " + extractPoints(readableText) + 
	        		        		" amount is " + extractAmount(readableText) +
	        		        		" transaction is " + extractTransaction(readableText));*/
	        		        	}
	        				}
	        				
	        				//model.addAttribute("bookkeepings",bookkeepings);
	        				//session.setAttribute("bookkeepings", bookkeepings);
	        				//System.out.println("dgr --> " + dgr.toString());
	        				//System.out.println("response OK --> " + jsonstring);
	        				
	        				response.close();
	        			}
	        		}
	        		}

	    			for(Bookkeeping bk : bookkeepings){
	        			if(bk.getUser().compareTo(vendorId)==0){
	        				vendor1_currentpoints += Integer.parseInt(bk.getPoints());
	        				vendor1_currentamount += Integer.parseInt(bk.getAmount());
	        				vendor1_currenttransaction += Integer.parseInt(bk.getTransaction());
	        			}else if(bk.getUser().compareTo(customerId)==0){
	        				customer1_currentpoints += Integer.parseInt(bk.getPoints());
	        				customer1_currentamount += Integer.parseInt(bk.getAmount());
	        				customer1_currenttransaction += Integer.parseInt(bk.getTransaction());
	        			}else	continue;
	        		}
	    			
    	        }catch(Exception e){
    	        	System.out.println("problem while reading points");
    	        	e.printStackTrace();
    	        }

    		}
    		
    		customer1_currentpoints+=mypoints;
    		
    		if(customer1_currentpoints<0)	return "error";
    		

    		System.out.println("1 getting master id...");
    		
    		// 1. get topic id
    		for(Topic tp : vendor.getTopics()){
    			if(tp.getCreator().compareTo(vendorId)==0){
    				
    			// 0. get the old value first	
    	        System.out.println("0 getting old value...");
	
    	     // read hedera response using dragonglass
				try{			
		    			String topicId = tp.getTopicId();
		    			//Collection<HederaSecret> hs = hederaSecretRepository.findByTopicAndCustomerId(tp,vendorid);
		    			//Collection<HederaSecret> hs = hederaSecretRepository.findByTopicAndCustomerId(tp,vendorId);
		    			Collection<HederaSecret> hs = hederaSecretRepository.findByTopic(tp);

                    	Set<Bookkeeping> bookkeepings = new HashSet<Bookkeeping>();
		    			Integer vendor_currentpoints=0;
		    			Integer vendor_currentamount=0;
		    			Integer vendor_currenttransaction=0;
                    	
		    			Integer customer_currentpoints=0;
		    			Integer customer_currentamount=0;
		    			Integer customer_currenttransaction=0;
                    	
		        		for(HederaSecret hcs : hs){     
		        			if(hcs.getCustomerId().compareTo(customerId)==0||hcs.getCustomerId().compareTo(vendorId)==0){
		        			
		        			System.out.println("secretKey = " + hcs.getSecretKey() + " customerId = " + hcs.getCustomerId() + " HCS_id = " + hcs.getPointTopic());
		        			if(hcs.getPointTopic()!=null&&hcs.getPointTopic().length()!=0){
		        				OkHttpClient myclient = new OkHttpClient().newBuilder()
		        						.build();
		        				Request request02 = new Request.Builder()
		        						//.url("https://api.dragonglass.me/hedera/api/v1/topics/" + "0.0.35650" + "/messages")
		        						.url("https://api-testnet.dragonglass.me/hedera/api/v1/topics/" + hcs.getPointTopic() + "/messages")
		        						.method("GET", null)
		        						//.addHeader("X-API-KEY", "1eb33c6f-21c7-31f5-a0fe-8bd052ef58fd")
		        						.addHeader("X-API-KEY", "cf86a1d3-fd82-37d5-8902-3e6bb1c1e263")
		        						.build();
		        				
		        				Response response = myclient.newCall(request02).execute();
		        				okhttp3.ResponseBody responseBody = response.body();
		        			
		        				Gson gson = new Gson(); 

		        				String jsonstring = responseBody.string();
		        				
		        				DragonGlassResponse dgr = gson.fromJson(jsonstring, DragonGlassResponse.class);
		        				
		        				HCSResponse[] hcsresponse = dgr.getData();
		        				if(hcsresponse == null)	continue;

		        				
		        				for(HCSResponse hcsr : hcsresponse){
		        					
		        					String message = hcsr.getMessage();
		        					//System.out.println("hcsr -> " + message);
		        		        	String messageAsString = new String(message.getBytes(), StandardCharsets.UTF_8);

		        		        	String readableText = fromHexString(messageAsString);
		        		        	
		        		        	System.out.println("received topic message: " + readableText + " at " + hcsr.getConsensusTime());

		        		        	if(isMessageValid(readableText)){
		        		        		Bookkeeping bk = createBookkeeping(readableText,hcsr.getConsensusTime());
		        		        		bookkeepings.add(bk);
		        		        		/*System.out.println("user is " + extractUser(readableText) +
		        		        		" points is " + extractPoints(readableText) + 
		        		        		" amount is " + extractAmount(readableText) +
		        		        		" transaction is " + extractTransaction(readableText));*/
		        		        	}
		        				}
		        				
		        				//model.addAttribute("bookkeepings",bookkeepings);
		        				//session.setAttribute("bookkeepings", bookkeepings);
		        				//System.out.println("dgr --> " + dgr.toString());
		        				//System.out.println("response OK --> " + jsonstring);
		        				
		        				response.close();
		        			}
		        		}
		        		}
		        		
		        		for(Bookkeeping bk : bookkeepings){
		        			if(bk.getUser().compareTo(vendorId)==0){
		        				vendor_currentpoints += Integer.parseInt(bk.getPoints());
		        				vendor_currentamount += Integer.parseInt(bk.getAmount());
		        				vendor_currenttransaction += Integer.parseInt(bk.getTransaction());
		        			}else if(bk.getUser().compareTo(customerId)==0){
		        				customer_currentpoints += Integer.parseInt(bk.getPoints());
		        				customer_currentamount += Integer.parseInt(bk.getAmount());
		        				customer_currenttransaction += Integer.parseInt(bk.getTransaction());
		        			}else	continue;
		        		}
		        		
		    			String masterId = tp.getTopicId();
		    			System.out.println("master id is " + masterId);

		    			//2. generate SecretKey
		    			Ed25519PrivateKey submitKey = genSecretKey();
		    			Ed25519PublicKey submitPublicKey = submitKey.publicKey;
		    			
		        		System.out.println("2 generate submitKey success for customer");

	    				Client client = Client.forTestnet();
	    				client.setOperator(OPERATOR_ID, OPERATOR_KEY);
	    				
	    			    final TransactionId transactionId = new ConsensusTopicCreateTransaction()
	    			            .setTopicMemo("Streetpoints - update points")
	    			            .setSubmitKey(submitPublicKey)
	    			            .execute(client);

	         			final ConsensusTopicId topicId02 = transactionId.getReceipt(client).getConsensusTopicId();

	         			System.out.println("Your topic ID for customer is: " +topicId02);
		
	        			System.out.println("waiting for topic id to reach hedera node for customer");

	    		        Thread.sleep(9000);
	    		        
	            		System.out.println("3 generate HCS topic success for customer ");

	        			//4. create Hedera Secret
	        			hederaSecretRepository.removeByTopicAndCustomerId(tp,customerId);
	            		
	        			HederaSecret hederaSecret = new HederaSecret(submitKey.toString(),topicId02.toString(), tp, customerId, java.time.LocalDateTime.now());
	        			hederaSecretRepository.save(hederaSecret);

	        			System.out.println("4 save Hedera Secret success for customer");

	        			//5. build string for hedera
	        			customer_currentpoints+=mypoints;
	        			customer_currentamount+=myamount;
	        			customer_currenttransaction+=1;
	        			
	        			String payload = buildHederaPayload("Streetpoints", customerId, customer_currentpoints.toString(), customer_currentamount.toString(),customer_currenttransaction.toString());

	        			System.out.println("5 generate payload for customer success.. " + payload);

	        			  new ConsensusMessageSubmitTransaction()
	                      .setTopicId(topicId02)
	                      .setMessage(payload)
	                      .build(client)

	                      // The transaction is automatically signed by the payer.
	                      // Due to the topic having a submitKey requirement, additionally sign the transaction with that key.
	                      .sign(submitKey)

	                      .execute(client)
	                      .getReceipt(client);
	        			
	        			
	        		/*	new ConsensusMessageSubmitTransaction()
		  	        	.setTopicId(topicId)
		  	        	.setMessage(payload)
		  	        	.sign(submitKey)
		  	        	.execute(client)
		  	        	.getReceipt(client);*/
					
	        			System.out.println("6 write to hedera for customer success.. waiting for hedera to reach for consensus");

	        			//Thread.sleep(9000);
	        			

		    			//2. generate SecretKey
		    			Ed25519PrivateKey submitKey01 = genSecretKey();
		    			Ed25519PublicKey submitPublicKey01 = submitKey01.publicKey;

		        		System.out.println("2 generate submitKey successfor vendor ");
	    				
		        		Client client01 = Client.forTestnet();
	    				client01.setOperator(OPERATOR_ID, OPERATOR_KEY);

	    			    final TransactionId transactionId01 = new ConsensusTopicCreateTransaction()
	    			            .setTopicMemo("Streetpoints - update points")
	    			            .setSubmitKey(submitPublicKey01)
	    			            .execute(client01);

	         			final ConsensusTopicId topicId01 = transactionId01.getReceipt(client01).getConsensusTopicId();

	         			System.out.println("Your topic ID for vendor is: " +topicId01);
		
	        			System.out.println("waiting for topic id to reach hedera node for vendor");

	    		        Thread.sleep(9000);

	            		System.out.println("3 generate HCS topic success for vendor ");

	        			//4. create Hedera Secret
	        			hederaSecretRepository.removeByTopicAndCustomerId(tp,vendorId);

	        			HederaSecret hederaSecret01 = new HederaSecret(submitKey01.toString(),topicId01.toString(), tp, vendorId, java.time.LocalDateTime.now());
	        			hederaSecretRepository.save(hederaSecret01);
	        			
	        			System.out.println("4 save Hedera Secret success for vendor ");

	        			//5. build string for hedera
	        			vendor_currentpoints+=mypoints;
	        			vendor_currentamount+=myamount;
	        			vendor_currenttransaction+=1;

	        			String payload01 = buildHederaPayload("Streetpoints", vendorId, vendor_currentpoints.toString(), vendor_currentamount.toString(),vendor_currenttransaction.toString());

	        			System.out.println("5 generate payload for vendor success.. " + payload01);

	        			  new ConsensusMessageSubmitTransaction()
	                      .setTopicId(topicId01)
	                      .setMessage(payload01)
	                      .build(client01)

	                      // The transaction is automatically signed by the payer.
	                      // Due to the topic having a submitKey requirement, additionally sign the transaction with that key.
	                      .sign(submitKey01)

	                      .execute(client01)
	                      .getReceipt(client01);
	        			
	        			
	        		/*	new ConsensusMessageSubmitTransaction()
		  	        	.setTopicId(topicId)
		  	        	.setMessage(payload)
		  	        	.sign(submitKey)
		  	        	.execute(client)
		  	        	.getReceipt(client);*/
					
	        			System.out.println("6 write to hedera for vendor success.. waiting for hedera to reach for consensus");

	        			//Thread.sleep(9000);
				}catch(Exception e){
					System.err.println("Error while reading or writing on hedera nodes");
					e.printStackTrace();
				}	
    				    			
    			break;
    			}
    		}
    		
    	}catch(Exception e){
    		return "error";
    	}
    	
        return points;
    }
    
    private final SimpleMailMessage constructEmailMessage02(String email01, String email02, String inviteUrl) {
        final String recipientAddress = email02;
        final String subject = email01 + " invite you to join their loyalty program";
        final String confirmationUrl = inviteUrl;
        //final String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;
        final String message = email01 + " has invited you to join their loyalty program. Please click on the link below to join the program. You can then start to share the market and grow together :)";
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }
    
    private final SimpleMailMessage constructEmailMessage03(String email01, String email02) {
        final String recipientAddress = email01;
        final String subject = email02 + " accepted invitation to join your loyalty program!";
        final String message = email02 + " accepted invitation to join your loyalty program. You can now build customer loyalty and grow together :)";
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }
    
    private final SimpleMailMessage constructEmailMessage04(String email01, String email02) {
        final String recipientAddress = email01;
        final String subject = "The shared loyalty program is terminated by " + email02;
        final String message = "The shared loyalty program is terminated by " + email02 + ". All points distributed by " + email02 + " will no longer be exposed to you.";
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    

    @PostMapping("/registration")
    //@PostMapping for email validation
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
            BindingResult result, HttpServletRequest request) {  		
//    		BindingResult result) { //for email validation

        Customer existing = userService.findByEmail(userDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()) {
            return "registration";
        }

        //userService.save(userDto); // for email validation
        
        // for email validation
        try {
        	Customer registered = userService.save(userDto);
        	
            String appUrl = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
            
            System.err.println("appUrl=" + appUrl);
            
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, 
            		new Locale("en"),appUrl));
              //request.getLocale(), appUrl));
        }
        /*} catch (UserAlreadyExistException uaeEx) {
            ModelAndView mav = new ModelAndView("registration", "user", userDto);
            mav.addObject("message", "An account for that username/email already exists.");
            return mav;
        }*/ catch (RuntimeException ex) {
        	
        	ex.printStackTrace();
        	
            result.rejectValue("email", null, "Cannot verify this email");        
            return "registration";
        }
               
        return "redirect:/registration?success";
    }
    
    //for expired token
    private SimpleMailMessage constructResendVerificationTokenEmail
    (String contextPath, VerificationToken newToken, Customer user) {
      String confirmationUrl = 
        contextPath + "/regitrationConfirm.html?token=" + newToken.getToken();
      String message = "Below is your new verification link. Please click on the link to activate your account.";
      SimpleMailMessage email = new SimpleMailMessage();
      email.setSubject("Resend Registration Token");
      email.setText(message + " \r\n" + confirmationUrl);
      email.setFrom(env.getProperty("support.email"));
      email.setTo(user.getEmail());
      return email;
  }
}