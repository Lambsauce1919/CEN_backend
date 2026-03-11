package CEN_LAUNCH;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
	    "CEN_LAUNCH",
	    "SEC_CONF",
		"Wishlist_backend",
	    "pf_mgmt"        
	    "SEC_CONF",      
	    "pf_mgmt",
		"Book_Browsing"
	})


public class LaBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(LaBackendApplication.class, args);
		System.out.println("Started successfully");
	}
	
	
}
