package pf_mgmt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import pf_mgmt.pf_mgmt_json.RGData;

@RestController
public class udate_response {
	
	@Autowired
	JdbcTemplate jd;
	
	private final String rUSQL = "SELECT home_address, email, name FROM users WHERE username ILIKE ?";
	// ILIKE = allows case-insensitive matching.
	
	
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
		    }, username); // <--- Add this parameter here!
			
			} catch (EmptyResultDataAccessException e) { 
				System.out.println("No matches were found.. Returning NOT FOUND status.");
				return ResponseEntity.notFound().build();  // No results were found for this user name
		}
		
		System.out.println("Discovered information relating to the requested user successfully, returning it to the API caller...");
		
		return ResponseEntity.ok(ud); // Return the packaged data.
		
		
		
		
	}
	
	private String formatValue(String value) { // Handle any cases where data got added and reflected NULL
	    if (value == null || value.trim().isEmpty() || value.equalsIgnoreCase("null")) {
	        return "N/A";
	    }
	    return value;
	}
	

}
