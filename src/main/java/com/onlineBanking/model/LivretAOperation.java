package com.onlineBanking.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name="livretAOperation")

public class LivretAOperation {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;   
    private String description;
    private String type;
    private String status;
    private Double amount;
    private Double availableBalance;
	 
	 @Temporal(TemporalType.TIMESTAMP)
	 private   Date creationDateTime;


	    @ManyToOne
	    @JoinColumn(name = "la_id")
	    private LivretA livretA;


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getDescription() {
			return description;
		}


		public void setDescription(String description) {
			this.description = description;
		}


		public String getType() {
			return type;
		}


		public void setType(String type) {
			this.type = type;
		}


		public String getStatus() {
			return status;
		}


		public void setStatus(String status) {
			this.status = status;
		}


		public Double getAmount() {
			return amount;
		}


		public void setAmount(Double amount) {
			this.amount = amount;
		}


		public Double getAvailableBalance() {
			return availableBalance;
		}


		public void setAvailableBalance(Double availableBalance) {
			this.availableBalance = availableBalance;
		}


		public Date getCreationDateTime() {
			return creationDateTime;
		}


		public void setCreationDateTime(Date creationDateTime) {
			this.creationDateTime = creationDateTime;
		}


		public LivretA getLivretA() {
			return livretA;
		}


		public void setLivretA(LivretA livretA) {
			this.livretA = livretA;
		}


		public LivretAOperation( String description, String type, String status, Double amount,
				Double availableBalance, LivretA livretA) {
			super();
			this.description = description;
			this.type = type;
			this.status = status;
			this.amount = amount;
			this.availableBalance = availableBalance;
			this.livretA = livretA;
		}


		public LivretAOperation(Long id, String description, String type, String status, Double amount,
				Double availableBalance, Date creationDateTime, LivretA livretA) {
			super();
			this.id = id;
			this.description = description;
			this.type = type;
			this.status = status;
			this.amount = amount;
			this.availableBalance = availableBalance;
			this.creationDateTime = creationDateTime;
			this.livretA = livretA;
		}

	    public LivretAOperation() {
		
	}


}


	
	

