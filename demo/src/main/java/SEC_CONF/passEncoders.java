package SEC_CONF;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class passEncoders {

	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder(); 
	} // Returns a Hashed password.

	private static final SecureRandom secureRandom = new SecureRandom(); 
	private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

	
	public String gSToken() { // Generate a secure, randomized, session token.
		byte[] randomBytes = new byte[36]; // Increased security and length (orig 24, thanks overstack).
	    secureRandom.nextBytes(randomBytes);
	    return base64Encoder.encodeToString(randomBytes);	
	}
	
	
}
