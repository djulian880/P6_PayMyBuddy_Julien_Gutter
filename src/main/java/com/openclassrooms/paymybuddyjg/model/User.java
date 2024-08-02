package com.openclassrooms.paymybuddyjg.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


 
@Entity
@Table(name = "User")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idUser")
	private int idUser;
 
	@Column(name = "username")
	private String userName;
 
	@Column(name = "email")
	private String email;
 
	@Column(name = "password")
	private String password;
	
	
}
