package com.openclassrooms.paymybuddyjg.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import lombok.Data;


@Data 
@DynamicUpdate
@Entity
@Table(name = "User")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_user;
 
	@Column(name = "username")
	private String userName;
 
	@Column
	private String email;
 
	@Column(name = "password")
	private String password;
	
	@OneToMany(
			   cascade = CascadeType.ALL, 
			   orphanRemoval = true, 
			   fetch = FetchType.EAGER)
	@JoinTable(
			name = "Connection",
			joinColumns = @JoinColumn(name = "User_id_1"), 	
			inverseJoinColumns = @JoinColumn(name = "User_id_2")
	)
	List<User> connections = new ArrayList<>();
	
}
