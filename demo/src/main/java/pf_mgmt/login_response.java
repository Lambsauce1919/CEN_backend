package pf_mgmt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import SEC_CONF.passEncoders;
import pf_mgmt.pf_mgmt_json.LoginData;

@RestController
public class login_response {
	
	@Autowired
	private JdbcTemplate jd;
	
	@Autowired
	passEncoders pe;
	
	@PostMapping("/LOGIN")
	ResponseEntity<LoginData> loginUser(@RequestBody LoginData ld){
		
		// Remove any whitespace
		String username = ld.getUsername().trim();
		String password = ld.getPassword().trim(); 
		
		System.out.println("Retrieved user information | username :  " + username + " | Password: " + password);
		
		// Create a session token.
		String sToken = pe.gSToken();
		
		// Query the database for the user-name & password.
		
		@SuppressWarnings("unused")
		List<String> results = jd.query("SELECT password FROM users WHERE username = ?", 
			    (rs, rowNum) -> rs.getString("password"), username);

			if (results.isEmpty()) {
			    // No user found with that username
			    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		
		
		if(!pe.passwordEncoder().matches(password, results.get(0))) {
			// Typically you'd want some more identifying information like IP log, time of incident, etc.
			System.out.println("There was an attempted login of user: " + ld.getUsername() + ", however, they failed the password check");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
			
		int updated = jd.update("UPDATE users SET stoken = ? WHERE username = ?" 
				, sToken, username);
		
		if (updated < 1) {
			// There were no matches for the provided information. 
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} 
		
		// Build the object with new data.
		LoginData rd = new LoginData(true, sToken);
		
		System.out.println("Returning values: login boolean: " + true + " and session token: " + sToken);
		
		System.out.println("Sending the data back.");
		
		// Return the object
		return ResponseEntity.ok(rd);		
		
		
	}

}
