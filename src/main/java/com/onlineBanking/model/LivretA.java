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
@Table(name="livretA")

public class LivretA {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	private Double balance;
	 
	  @OneToMany(mappedBy = "livretA", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    @JsonIgnore
	    private List<LivretAOperation> livretAOperation;

	public Long getId() {
		return id;
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

	public List<LivretAOperation> getLivretAOperation() {
		return livretAOperation;
	}

	public void setLivretAOperation(List<LivretAOperation> livretAOperation) {
		this.livretAOperation = livretAOperation;
	}

	
	 

	
	
	
	
}
