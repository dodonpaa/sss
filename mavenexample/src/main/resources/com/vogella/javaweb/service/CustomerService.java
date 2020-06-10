// for login hedera

package com.vogella.javaweb.service;

import com.vogella.javaweb.model.Customer;
import com.vogella.javaweb.model.VerificationToken;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.vogella.javaweb.UserRegistrationDto;

import com.vogella.javaweb.model.Role;

import java.util.Collection;
import java.util.List;


public interface CustomerService extends UserDetailsService {
    Customer save(UserRegistrationDto registration);

    Customer findByEmail(String email);
    
    //for email validation
    Customer getUser(String verificationToken);
    
    Customer getVendorByEmail(String email);
    
    void saveRegisteredUser(Customer user);
 
    void createVerificationToken(Customer user, String token);
 
    VerificationToken getVerificationToken(String VerificationToken);
    
    VerificationToken generateNewVerificationToken(String token);
    
    List<Customer> getVendorByRole(List<Collection<Role>> roles);
    
    void removeInvitee(Long id);
    //for email validation
}
