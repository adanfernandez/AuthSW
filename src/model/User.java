package model;

import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"email"})})
public class User {
	@Id
	@GeneratedValue
	private long id;
	
	@NotNull
	private String name;
	
    @Column(name = "email")
    @NotNull
	private String email;

    @NotNull
	private String surname;
	
	private String phone;
	
	@NotNull
	private String password;
	
	private boolean deleted;
	
	public User() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public boolean isDeleted() {
		return deleted;
	}

	public User(long id, String name, String email, String surname, String phone, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.surname = surname;
		this.phone = phone;
		this.password = password;
	}	
	
	
}
