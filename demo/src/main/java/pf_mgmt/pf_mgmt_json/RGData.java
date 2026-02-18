package pf_mgmt.pf_mgmt_json;
//This JSON holds all required data for registration.

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Optimized JSON Data Object for User Profile Management.
 * Includes Jackson annotations to ensure perfect mapping between 
 * Database, API, and Client.
 */


public class RGData {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;
    
    @JsonProperty("haddress")
    private String haddress;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("name")
    private String name;

  // MANDATORY FOR JACKSON AND RETROFIT
    public RGData() {
    }

    public RGData(String username, String password, String haddress, String email, String name) {
        this.username = username;
        this.password = password;
        this.haddress = haddress;
        this.email = email;
        this.name = name;
    }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String gethaddress() { return haddress; }
    public void sethaddress(String haddress) { this.haddress = haddress; }

    public String getemail() { return email; }
    public void setemail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
