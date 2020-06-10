// for login hedera
package com.vogella.javaweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.vogella.javaweb.security.CustomAuthenticationFailureHandler;
import com.vogella.javaweb.security.VendorAuthenticationFailureHandler;


import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.vogella.javaweb.service.CustomerService;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig /*extends WebSecurityConfigurerAdapter*/ {
    
	@Autowired
    private CustomerService customerService;
	
	@Autowired
	private static CustomAuthenticationFailureHandler  authenticationFailureHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //for multiple login
    @Configuration
    @Order(2)
    public static class App1ConfigurationAdapter extends WebSecurityConfigurerAdapter {

        public App1ConfigurationAdapter() {
            super();
        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {
        	
        	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        	
            http./*csrf().disable().*/antMatcher("/*").authorizeRequests().antMatchers("/resources/**", "/registration","/registrationConfirm","/badUser*","/barcodes*","/resendRegistrationToken","/accountcreated","/verificationfailed","/login*","/js/**","/css/**","/img/**","/webjars/**").permitAll().anyRequest().authenticated()
                    // log in
            		//.and().formLogin().loginPage("/login").permitAll()
                    .and().formLogin().loginPage("/login").defaultSuccessUrl("/homepage",true).loginProcessingUrl("/user_login").failureUrl("/login?error").failureHandler(new CustomAuthenticationFailureHandler()).permitAll()
                    // logout
                    .and().logout()/*.invalidateHttpSession(true)*/.clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")/*.logoutUrl("/user_logout")*/.permitAll();
        }
        
        @Bean
        public AuthenticationManager customAuthenticationManager() throws Exception {
            return authenticationManager();
        }
    }
    
  //for multiple login
    @Configuration
    @Order(1)
    public static class App2ConfigurationAdapter extends WebSecurityConfigurerAdapter {

        public App2ConfigurationAdapter() {
            super();
        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            
        	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        	
        	http./*csrf().disable().*/antMatcher("/vendor*").authorizeRequests().antMatchers("/resources/**", "/registration","/registrationConfirm","/badUser*","/resendRegistrationToken","/accountcreated","/verificationfailed","/barcodes*","/vendors*","/js/**","/css/**","/img/**","/webjars/**").permitAll().anyRequest().authenticated()
                    // log in
            		//.and().formLogin().loginPage("/vendors").permitAll()
                    .and().formLogin().loginPage("/vendors").defaultSuccessUrl("/vendorpage",true).loginProcessingUrl("/vendor_login").failureUrl("/vendors?error").failureHandler(new VendorAuthenticationFailureHandler()).permitAll()
                    // logout
                    .and().logout().clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/vendorlogout")).logoutSuccessUrl("/vendors?logout")/*logoutUrl("/vendor_logout")*/.permitAll();
        }
        
     // @Bean
       // public AuthenticationManager customAuthenticationManager2() throws Exception {
         //   return authenticationManager();
        //}
    }
    
    
    
    
    //added antmatcher for user, 
 /*   @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/user*") 
            .authorizeRequests()
                .antMatchers("/resources/**", "/registration","/js/**","/css/**","/img/**","/webjars/**").permitAll()
                .anyRequest().hasRole("Customer") //
               // .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/user_login") //
                .permitAll()
                .and()
            .logout()
            	.logoutUrl("/user_logout") //
                .permitAll();
    }*/

   // @Bean
    //public AuthenticationManager customAuthenticationManager() throws Exception {
      //  return authenticationManager();
    //}

    //@Autowired
    //public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      //  auth.userDetailsService(customerService).passwordEncoder(bCryptPasswordEncoder());
    //}
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(customerService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
    
    //@Override
    //protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      //  auth.authenticationProvider(authenticationProvider());
    //}
}