package com.openclassrooms.paymybuddyjg.model;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@DynamicUpdate
@Entity
@Table(name = "Transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_Transaction;
 
	@Column(name = "description")
	private String description;
 
	@Column(name = "amount")
	private Double amount;
 
	 @OneToOne
	 @JoinColumn(name = "sender_id", referencedColumnName = "id_user")
	 private User sender;

	 @OneToOne
	    @JoinColumn(name = "receiver_id", referencedColumnName = "id_user")
	 private User receiver;

}
