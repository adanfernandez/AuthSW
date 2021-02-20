package model;

import java.util.Date;

public class JWT {
	private String email;
	private String token;
	private Date expiration;
	private Long idUsuario;
	
	public JWT() {}	
	
	public JWT(String email, String token, Date expiration, Long idUsuario) {
		this.email = email;	
		this.token = token;
		this.expiration=expiration;
		this.idUsuario = idUsuario;
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
	
	public Long getIdUsuario() {
		return idUsuario;
	}
	
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
}
