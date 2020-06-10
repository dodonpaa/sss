package com.vogella.javaweb;

import com.vogella.javaweb.model.Product;
import com.vogella.javaweb.repository.CustomerRepository;
import com.vogella.javaweb.repository.OrderRepository;
import com.vogella.javaweb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// for qr gen
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;

// for login hedera
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.awt.image.BufferedImage;


@SpringBootApplication
public class SpringcoffeeshopApplication implements CommandLineRunner {

	@Autowired
    ProductRepository productRepository;

	@Autowired
    CustomerRepository customerRepository;

	@Autowired
    OrderRepository orderRepository;



    public static void main(String[] args) {
		SpringApplication.run(SpringcoffeeshopApplication.class, args);
	}


    @Override
    public void run(String... strings) throws Exception {

    	// cleanup database, TODO: comment out later
    	//orderRepository.deleteAll();
    	//productRepository.deleteAll();

        Product doctor = new Product();
        doctor.setProductName("Doctor Costume");
        doctor.setProductPrice(100.0);

        Product sailor = new Product();
        sailor.setProductName("Sailor Costume");
        sailor.setProductPrice(100.0);
        
        Product pirate = new Product();
        pirate.setProductName("Pirate Costume");
        pirate.setProductPrice(100.0);
/* do not save product
        productRepository.save(doctor);
        productRepository.save(sailor);
        productRepository.save(pirate);*/
    }
    
    // for qr gen
    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }
}
