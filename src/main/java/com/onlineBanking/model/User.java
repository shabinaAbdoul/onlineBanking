package com.onlineBanking.model;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



@Entity
@Table(name="user", uniqueConstraints = @UniqueConstraint(columnNames="userName"))

public class User {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	private String userName;
	private String password;	
	private boolean enabled;
	 @OneToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "userId", referencedColumnName = "id")
	   
	private UserDetails userDetails;
	
	public User(String userName, String password, boolean enabled, UserDetails userDetails) {
		super();
		this.userName = userName;
		this.password = password;
		this.enabled = enabled;
		this.userDetails = userDetails;
	}
	public User(Long id, String userName, String password, boolean enabled, UserDetails userDetails) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.enabled = enabled;
		this.userDetails = userDetails;
	}
	public User() {
		super();
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public UserDetails getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(UserDetails userDetails2) {
		this.userDetails = userDetails2;
	}
	
	
	
	
	
	
	
	
}
