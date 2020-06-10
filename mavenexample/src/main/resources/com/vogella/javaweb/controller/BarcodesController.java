package com.vogella.javaweb.controller;

//import com.vogella.javaweb.generators.QRGenBarcodeGenerator;
import com.vogella.javaweb.generators.ZxingBarcodeGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import java.awt.image.BufferedImage;

import org.springframework.stereotype.Controller;

import org.springframework.core.io.ClassPathResource;

import java.nio.file.Files;
import java.io.IOException;

import org.springframework.ui.Model;


@Controller
@RequestMapping("/barcodes")
public class BarcodesController {

    //Zxing library
    @PostMapping(value = "/zxing/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public String /*ResponseEntity<BufferedImage>*/ zxingQRCode(HttpSession session,Model model) throws Exception {
        String barcode = "barcode";
        String myname = (String)session.getAttribute("myname");
        
        if(myname!=null)	barcode = myname;
        
    	return okResponse(ZxingBarcodeGenerator.generateQRCodeImage(barcode),myname,session,model);
    }
    
    //Zxing library
    @GetMapping(value = "/zxing/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public String /*ResponseEntity<BufferedImage>*/ zxingQRCode1(HttpSession session,Model model) throws Exception {
        String barcode = "barcode";
        
        String myname = (String)session.getAttribute("myname");
        
        if(myname!=null)	barcode = myname;
        
        session.setAttribute("myqr", "image/" + myname);
        
        System.err.println("username = " + myname);
        
    	return okResponse(ZxingBarcodeGenerator.generateQRCodeImage(barcode),myname,session,model);
    }

    //QRGen

    //@PostMapping(value = "/qrgen/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    //public ResponseEntity<BufferedImage> qrgenQRCode(@RequestBody String barcode) throws Exception {
      //  return okResponse(QRGenBarcodeGenerator.generateQRCodeImage(barcode));
    //}
    
    private String /*ResponseEntity<BufferedImage>*/ okResponse(BufferedImage image,String myname, HttpSession session, Model model) {
    	
    	//write buffered image to file
    	try{
    		
    		String dir = System.getProperty("user.dir");
    		System.out.println("dir = " + dir);
    		
    		//File outputfile = null;
    		
    		/*try{
    			File outputfile = new ClassPathResource("elfeeqr.jpg").getFile();
    			if (!outputfile.exists()) {
                    outputfile.createNewFile();
                }
    		
    		}catch(Exception e){
    			
    		}*/
    		String filename = myname + ".jpg";
    		
    		File outputfile = new File("src/main/resources/static/img/" + filename);
    		System.out.println("create qr file to " + "src/main/resources/static/img/" + filename);
    		
    		ImageIO.write(image, "jpg", outputfile);
    	}catch(Exception e){
    		System.err.println("Error writing file");
    		e.printStackTrace();
    	}
    	
		model.addAttribute("bookkeepings", session.getAttribute("bookkeepings"));
		model.addAttribute("allvendors", session.getAttribute("allvendors"));
		session.setAttribute("qrcodegen","true");

    	    	
        return "homepage2";
    	
    	//return new ResponseEntity<>(image, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/zxing/image/{imageName}")
    @ResponseBody
    public byte[] getImage(@PathVariable(value = "imageName") String imageName) throws IOException {

    	String dir = System.getProperty("user.dir");
		System.out.println("dir = " + dir);
		System.out.println("imageName = " + imageName);
    	   	
        File serverFile = new File("src/main/resources/static/img/" + imageName + ".jpg");

        return Files.readAllBytes(serverFile.toPath());
    }
}