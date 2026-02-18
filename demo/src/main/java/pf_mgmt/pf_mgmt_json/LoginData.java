package pf_mgmt.pf_mgmt_json;
// - Brenden
public class LoginData {

	private String username;
	private String password;
	
	private boolean authenticated;
	private String token;
	
	public LoginData() {
		
	}
	
	// Why this is important, Jackson calls new LoginData(), w/o empty constructor it will fail and cause an
	// InvalidDefinitionException
	
	public boolean isAuthenticated() {
		return authenticated;
	}
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public LoginData(String username, String password) { // This is for the initial API call, information from the user is sent over.
		super();
		this.username = username;
		this.password = password;
	}
	
	public LoginData(boolean authenticated, String token) { // This is on-reponse information from the back-end.
		super();
		this.authenticated = authenticated;
		this.token = token;
	}
	
	
	
}
