package com.onlineBanking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="receiver")

public class Receiver {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	private String nom;
	private String email;
	private String mobile;
	private Long accountNumber;
	
	 @ManyToOne
	    @JoinColumn(name = "user_id")
	    @JsonIgnore
	    private UserDetails userDetails;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
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

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public Receiver() {
		super();
		
	}
	public Receiver(String nom, String email, String mobile, Long accountNumber) {
		super();
		this.nom = nom;
		this.email = email;
		this.mobile = mobile;
		this.accountNumber = accountNumber;
	}

	public Receiver(Long id, String nom, String email, String mobile, Long accountNumber, UserDetails userDetails) {
		super();
		this.id = id;
		this.nom = nom;
		this.email = email;
		this.mobile = mobile;
		this.accountNumber = accountNumber;
		this.userDetails = userDetails;
	}
	
	
}
