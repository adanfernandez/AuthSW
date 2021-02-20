package model;

import java.util.Date;

public class JWT {
	private String email;
	private String token;
	private Date expiration;
	
	public JWT() {}	
	
	public JWT(String email, String token, Date expiration) {
		this.email = email;	
		this.token = token;
		this.expiration=expiration;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getExpiration() {
		return expiration;
	}
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	
}
