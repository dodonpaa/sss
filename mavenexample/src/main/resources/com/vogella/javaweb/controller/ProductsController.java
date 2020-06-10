package com.vogella.javaweb.controller;

import com.vogella.javaweb.model.Product;
import com.vogella.javaweb.repository.ProductRepository;
import com.vogella.javaweb.repository.OrderRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Elfee on 10/12/17.
 */
 
@Controller
public class ProductsController {
 
    @Autowired
    ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

 
    @RequestMapping("/product/{id}")
    public String product(@PathVariable Long id, Model model){
    	Optional<Product> optionalEntitya =  productRepository.findById(id);
        Product myproduct = optionalEntitya.get();
    	
        model.addAttribute("product", myproduct);
        return "product";
    }
 
    @RequestMapping(value = "/products",method = RequestMethod.GET)
    public String productsList(Model model){
        model.addAttribute("products", productRepository.findAll());
        return "products";
    }
    
    @RequestMapping(value = "/checkout",method = RequestMethod.GET)
    public String checkout(Model model){
        model.addAttribute("products", productRepository.findAll());
        return "checkout2";
    }
    
   /** @RequestMapping(value = "/blog",method = RequestMethod.GET)
    public String blog(Model model){
        model.addAttribute("products", productRepository.findAll());
        return "blog";
    }**/
    
    @RequestMapping(value = "/tracking",method = RequestMethod.GET)
    public String tracking(Model model){
        model.addAttribute("products", productRepository.findAll());
        return "tracking";
    }
    
    @RequestMapping(value = "/coupon",method = RequestMethod.GET)
    public String coupon(Model model){
        model.addAttribute("products", productRepository.findAll());
        return "coupon";
    }
    
    @RequestMapping(value = "/step",method = RequestMethod.GET)
    public String step(Model model){
        model.addAttribute("products", productRepository.findAll());
        return "step";
    }
    
    @RequestMapping(value = "/aboutus",method = RequestMethod.GET)
    public String aboutus(Model model){
        model.addAttribute("products", productRepository.findAll());
        return "aboutus";
    }
    
    @RequestMapping(value = "/thankyou",method = RequestMethod.GET)
    public String thankyou(Model model){
        model.addAttribute("products", productRepository.findAll());
        return "thankyou";
    }
    
    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String home(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "hybel2";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/hybel",method = RequestMethod.GET)
    public String hybel(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "homelisting";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/listing1",method = RequestMethod.GET)
    public String listing1(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "listing01";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/listing2",method = RequestMethod.GET)
    public String listing2(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "listing02";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/listing3",method = RequestMethod.GET)
    public String listing3(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "listing03";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/listing4",method = RequestMethod.GET)
    public String listing4(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "listing04";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/listing5",method = RequestMethod.GET)
    public String listing5(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "listing05";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/listing6",method = RequestMethod.GET)
    public String listing6(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "listing06";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/listing7",method = RequestMethod.GET)
    public String listing7(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "listing07";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/listing8",method = RequestMethod.GET)
    public String listing8(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "listing08";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/listing9",method = RequestMethod.GET)
    public String listing9(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "listing09";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/listing10",method = RequestMethod.GET)
    public String listing10(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "listing10";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/listing11",method = RequestMethod.GET)
    public String listing11(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "listing11";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/listing12",method = RequestMethod.GET)
    public String listing12(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "listing12";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/listing13",method = RequestMethod.GET)
    public String listing13(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "listing13";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/listing14",method = RequestMethod.GET)
    public String listing14(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "listing14";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/listing15",method = RequestMethod.GET)
    public String listing15(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "listing15";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/listing16",method = RequestMethod.GET)
    public String listing16(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "listing16";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/listing17",method = RequestMethod.GET)
    public String listing17(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "listing17";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/listing18",method = RequestMethod.GET)
    public String listing18(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "listing18";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/listing19",method = RequestMethod.GET)
    public String listing19(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "listing19";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/listing20",method = RequestMethod.GET)
    public String listing20(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "listing20";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/listing21",method = RequestMethod.GET)
    public String listing21(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "listing21";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/listing22",method = RequestMethod.GET)
    public String listing22(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "listing22";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/listing23",method = RequestMethod.GET)
    public String listing23(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "listing23-th";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/about",method = RequestMethod.GET)
    public String about(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "aboutus2";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/service",method = RequestMethod.GET)
    public String service(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "service";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/question1",method = RequestMethod.GET)
    public String question1(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "question1";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/question2",method = RequestMethod.GET)
    public String question2(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "question2";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/question3",method = RequestMethod.GET)
    public String question3(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "question3";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/question4",method = RequestMethod.GET)
    public String question4(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "question4";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/question5",method = RequestMethod.GET)
    public String question5(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "question5";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
    
    @RequestMapping(value = "/question6",method = RequestMethod.GET)
    public String question6(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "question6";
        //return "hybelhome";
        //return "hybel";
        //return "meehashop";
        //return "orders";
    }
 
 
 
    @RequestMapping(value = "/saveproduct", method = RequestMethod.POST)
    @ResponseBody
    public String saveProduct(@RequestBody Product product) {
        productRepository.save(product);
        return product.getProductId().toString();
    }
 
}