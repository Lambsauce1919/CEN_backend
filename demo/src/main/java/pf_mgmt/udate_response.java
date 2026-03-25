package pf_mgmt;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import pf_mgmt.pf_mgmt_json.RGData;

@RestController
public class udate_response {
	
	@Autowired
	JdbcTemplate jd;
	
	private final String rUSQL = "SELECT home_address, email, name FROM users WHERE username ILIKE ?";
	// ILIKE = allows case-insensitive matching.
	
	// test
	
	@SuppressWarnings("unused")
	@GetMapping("/user/{id}")
	ResponseEntity<RGData> requestUD(@PathVariable("id") String username){
		
		System.out.println("Retrieved user information request on user: " + username + "... Beginning search.");
		
		RGData ud;
		
		try {
			ud = jd.queryForObject(rUSQL, (rs, a) -> { 
				
				String hadd = formatValue(rs.getString("home_address"));
			    String mail = formatValue(rs.getString("email"));
			    String name = formatValue(rs.getString("name"));
		        
		        
		        System.out.println("Name: " + name + " | " + "eMail: " + mail + " | " + "Address: " + hadd);
		        
		        return new RGData(null, null, hadd, mail, name);
		    }, username);
			
			} catch (EmptyResultDataAccessException e) { 
				System.out.println("No matches were found.. Returning NOT FOUND status.");
				return ResponseEntity.notFound().build();  // No results were found for this user name
		}
		
		System.out.println("Discovered information relating to the requested user successfully, returning it to the API caller...");
		
		return ResponseEntity.ok(ud); // Return the packaged data.
		
	}
	
	
	
	@PostMapping("/updateInfo")
	ResponseEntity<String> updateInfo(@RequestBody HashMap<Integer, String> payload, @RequestHeader("Authorization") String sToken){
		String type = null;
		String value = null;
		if (payload.containsKey(1)) {  // User name change
		type = "username";
		value = payload.get(1);
		} else if (payload.containsKey(2)) { // New password
		type = "password";	
		value = payload.get(2);
		
		if(!value.matches("^(?=.*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>])(?=.{9,}).*$")) {
			System.out.println("User attempted to register with an unqualified password.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your password does not meet the set requirements, please try again."); // Server rejected this data.
		}
		
		} else if (payload.containsKey(3)) { // New name
		type = "name";
		value = payload.get(3);	
		} else if (payload.containsKey(4)) { // New home address.
		type = "home_address";
		value = payload.get(4);
		}
		
		 String updateField = "UPDATE users SET " + type + " = ? WHERE stoken = ?";
		 
		 int updated = jd.update(updateField, value, sToken);
		 
		 if (updated < 1) {
				// There were no matches for the provided information. 
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			} 
		 
		return ResponseEntity.ok("Successfull");
		
	}
	
	
	private String formatValue(String value) { // Handle any cases where data got added and reflected NULL
	    if (value == null || value.trim().isEmpty() || value.equalsIgnoreCase("null")) {
	        return "N/A";
	    }
	    return value;
	}
	

}
