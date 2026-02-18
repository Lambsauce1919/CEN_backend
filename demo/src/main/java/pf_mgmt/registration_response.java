package pf_mgmt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import SEC_CONF.passEncoders;
import pf_mgmt.pf_mgmt_json.RGData;

@RestController
public class registration_response {

	@Autowired
	private JdbcTemplate jd;
	
	@Autowired
	passEncoders pe;
	
	private final String insertNewUser = "INSERT INTO users (username, password, home_address, email, name, utoken) VALUES (?, ?, ?, ?, ?, ?)";

	@PostMapping("/REGISTER")
	ResponseEntity<String> registerUser(@RequestBody RGData r){
		
	// Generally speaking you want to ensure that a user is entering a legitimate email domain, for the sake and simplicity of this project
		// I am opting to not include these restrictions.
		
	// Ensure the password meets global requirements.
	if(!r.getPassword().matches("^(?=.*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>])(?=.{9,}).*$")) {
		System.out.println("User attempted to register with an unqualified password.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your password does not meet the set requirements, please try again."); // Server rejected this data.
	}
	
	System.out.println("Recieved username: " + r.getUsername());
	
	// Generate a user token to associate the users information, books, money, etc with their account. This token is to NEVER BE SHARED on the CLIENT SIDE
	
	String user_token = pe.gSToken();
	
	String password = pe.passwordEncoder().encode(r.getPassword()); 
	
	// Check for conflicting user names	
	try {
	    jd.update(insertNewUser, r.getUsername(), password, r.gethaddress(), r.getemail(), r.getName(), user_token);
	    System.out.println("User: " + r.getUsername() + " has sucessfully been registered to this Library.");
	    return ResponseEntity.ok("User registered successfully");
	} catch (DuplicateKeyException e) {
	    return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken.");
	} catch (Exception e) {
	    e.printStackTrace();
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error.");
	}
	
	// If everything turns out fine, return the success message
		
		
	}
	
	
	
	
}
