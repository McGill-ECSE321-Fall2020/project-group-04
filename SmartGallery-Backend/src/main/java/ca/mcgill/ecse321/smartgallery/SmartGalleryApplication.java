package ca.mcgill.ecse321.smartgallery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@SpringBootApplication
public class SmartGalleryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartGalleryApplication.class, args);
	}
	
	@RequestMapping("/")
  	public String greeting(){
    		return "Hello world!";
  	}

}
