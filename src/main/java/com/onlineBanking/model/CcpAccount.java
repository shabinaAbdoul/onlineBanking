package com.onlineBanking.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="ccpAccount")

public class CcpAccount {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String numIban;
	
	private Double balance;
	 
	  @OneToMany(mappedBy = "ccpAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    @JsonIgnore
	    private List<CcpOperation> ccpOperation;

	public Long getId() {
		return id;
	}

	public String getNumIban() {
		return numIban;
	}

	public void setNumIban(String numIban) {
		this.numIban = numIban;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public List<CcpOperation> getCcpOperation() {
		return ccpOperation;
	}

	public void setCcpOperation(List<CcpOperation> ccpOperation) {
		this.ccpOperation = ccpOperation;
	}

}
