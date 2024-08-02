package com.openclassrooms.paymybuddyjg.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idTransaction")
	private int idTransaction;
 
	@Column(name = "description")
	private String description;
 
	@Column(name = "amount")
	private Double amount;
 
	@Column(name = "sender_Id")
	private int senderId;
	
	@Column(name = "receiver_Id")
	private int receiverId;
}
