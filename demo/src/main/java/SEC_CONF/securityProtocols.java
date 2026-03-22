package SEC_CONF;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class securityProtocols {

	// Circumvents CSRF protection to allow for a state-less server.
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    return http
	        .csrf(csrf -> csrf.disable()) 
	        .authorizeHttpRequests(auth -> auth
	            .anyRequest().permitAll() // Allow everything globally
	        )
	        .build();
	}
	
	
	@Bean
	public DataSource dataSource() {
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("org.postgresql.Driver");
	    dataSource.setUrl("jdbc:postgresql://localhost:5432/{your-DB-name-here}");
	    dataSource.setUsername("postgres");
	    dataSource.setPassword("");
	    return dataSource;
	}
	
	// This filter should NEVER be passed into PROD FYI. This is just for logic testing.
	
	// To temporarily bypass Spring boots generated password conundrum, there is a security user in the
	// applications.properties.
	
}
