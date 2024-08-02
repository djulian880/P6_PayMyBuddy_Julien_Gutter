package com.openclassrooms.paymybuddyjg.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Connection")
public class Connection {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idConnection")
	private int idTransaction;
 
 
	@Column(name = "user_id_1")
	private int userId1;
	
	@Column(name = "user_id_2")
	private int userId2;
}

