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
@Table(name="ccpOperation")

public class CcpOperation {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;   
    private String description;
    private String type;
    private String status;
    private Double amount;
    private Double availableBalance;
	 
	 @Temporal(TemporalType.TIMESTAMP)
	 private  Date creationDateTime;


	    public CcpOperation() {
		
	}


		@ManyToOne
	    @JoinColumn(name = "ccp_id")
	    private CcpAccount ccpAccount;


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


		public CcpAccount getCcpAccount() {
			return ccpAccount;
		}


		public void setCcpAccount(CcpAccount ccpAccount) {
			this.ccpAccount = ccpAccount;
		}


		public CcpOperation( String description, String type, String status, Double amount,
				Double availableBalance, CcpAccount ccpAccount) {
			super();
			
			this.description = description;
			this.type = type;
			this.status = status;
			this.amount = amount;
			this.availableBalance = availableBalance;
			this.ccpAccount = ccpAccount;
		}


		public CcpOperation(Long id, String description, String type, String status, Double amount,
				Double availableBalance, Date creationDateTime, CcpAccount ccpAccount) {
			super();
			this.id = id;
			this.description = description;
			this.type = type;
			this.status = status;
			this.amount = amount;
			this.availableBalance = availableBalance;
			this.creationDateTime = creationDateTime;
			this.ccpAccount = ccpAccount;
		}


	
	
}
