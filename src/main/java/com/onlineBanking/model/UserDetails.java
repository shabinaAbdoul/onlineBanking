package com.onlineBanking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



@Entity
@Table(name="userDetails", uniqueConstraints = @UniqueConstraint(columnNames="email"))

public class UserDetails {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	private String firstName;
	private String lastName;
	private String email;
	private String mobile;
	private String address;
	private String ville;	
	private String codePostal;
	
	
	@OneToOne
	    private CcpAccount ccpAccount;

	    @OneToOne
	    private LivretA livretA;
	
	public UserDetails(Long id, String firstName, String lastName, String email, String mobile, String address,
			String ville, String codePostal) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobile = mobile;
		this.address = address;
		this.ville = ville;
		this.codePostal = codePostal;
				
	}
	
	
	public UserDetails(String firstName, String lastName, String email, String mobile, String address, String ville,
			String codePostal) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobile = mobile;
		this.address = address;
		this.ville = ville;
		this.codePostal = codePostal;
		
		
	}


	public UserDetails() {
		// TODO Auto-generated constructor stub
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	


	
	 public CcpAccount getCcpAccount() {
		return ccpAccount;
	}


	public void setCcpAccount(CcpAccount ccpAccount) {
		this.ccpAccount = ccpAccount;
	}


	public LivretA getLivretA() {
		return livretA;
	}


	public void setLivretA(LivretA livretA) {
		this.livretA = livretA;
	}
	
	 
	
	
	

}
