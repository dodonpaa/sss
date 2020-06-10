// for login hedera
package com.vogella.javaweb.service;

import com.vogella.javaweb.model.Customer;
import com.vogella.javaweb.model.Topic;
import com.vogella.javaweb.model.Role;
import com.vogella.javaweb.repository.CustomerRepository;
import com.vogella.javaweb.UserRegistrationDto;
//for email validation
import com.vogella.javaweb.repository.VerificationTokenRepository;
import com.vogella.javaweb.model.VerificationToken;


import com.vogella.javaweb.repository.RoleRepository;
import com.vogella.javaweb.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.List;

import com.vogella.javaweb.repository.InviteeRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private VerificationTokenRepository tokenRepository;
    
    @Autowired
    private InviteeRepository inviteeRepository;

    public Customer findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Customer save(UserRegistrationDto registration) {
        Customer user = new Customer();
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setEmail(registration.getEmail());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setRoles(Arrays.asList(new Role(registration.getMyrole())));
        //user.setRoles(Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }
    
    //for email validation
    @Override
    public Customer getUser(String verificationToken) {
        Customer user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }
    
    //for email validation
    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }
    
    //for email validation
    @Override
    public void saveRegisteredUser(Customer user) {
    	
    	//System.out.println("saveRegisteredUser");
    	
    	//System.out.println("topic size is " + user.getTopics().size());
    	
    	// for debug only
    	/*for(Topic tp : user.getTopics()){
			System.out.println("topics is " + tp.getTopicId());
		}*/
    	
        userRepository.save(user);
    }
    
    //for email validation
    @Override
    @Transactional
    public void removeInvitee(Long id) {
    	
    	//System.out.println("saveRegisteredUser");
    	
    	//System.out.println("topic size is " + user.getTopics().size());
    	
    	// for debug only
    	/*for(Topic tp : user.getTopics()){
			System.out.println("topics is " + tp.getTopicId());
		}*/
    	System.out.println("removing invitee id = " + id);
    	try{
    		inviteeRepository.removeById(id);
    	}catch(Exception e){
    		System.out.println("cannot remove invitee");
    		e.printStackTrace();
    	}
    }
 
    //for email validation
    @Override
    public void createVerificationToken(Customer user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }
    
    //for expired token
    @Override
    public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID()
            .toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }
    
    // call by vendor only
    public Customer getVendorByEmail(String email) throws UsernameNotFoundException {
        Customer user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return user;
    }
    
    // get vendors by roles only
    public List<Customer> getVendorByRole(List<Collection<Role>> roles) throws UsernameNotFoundException {
        List<Customer> vendors = new ArrayList<Customer>();
		for(Collection<Role> r : roles){
			Customer vendor = userRepository.findByRolesIn(r);
			
			if(vendor == null)	{
				//System.out.println("vendor is null");
				continue;
			}
			//else	System.out.println("vendor name = " + vendor.getEmail());
			
			vendors.add(vendor);
		}
    	
		//System.out.println("vendors.size = " + vendors.size());
		
        if (vendors == null) {
            throw new UsernameNotFoundException("Vendors not found.");
        }
        return vendors;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
            user.getPassword(),
            user.getEnabled(), // for email validation
            true, //for email validation
            true, //for email validation
            true, //for email validation
            mapRolesToAuthorities(user.getRoles()));
    }

    private Collection < ? extends GrantedAuthority > mapRolesToAuthorities(Collection < Role > roles) {
        Collection <SimpleGrantedAuthority> sgalist = new ArrayList<SimpleGrantedAuthority>();
    	Iterator<Role> it = roles.iterator();
    	while(it.hasNext()){
    		Role r = it.next();
    		SimpleGrantedAuthority sga = new SimpleGrantedAuthority(r.getName());
    		sgalist.add(sga);
    	}
    	
    	return sgalist;
    	/*return roles.stream()
            .map(role - > new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());*/
    }
}