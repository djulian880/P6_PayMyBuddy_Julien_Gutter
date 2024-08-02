package com.openclassrooms.paymybuddyjg.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddyjg.model.User;
import com.openclassrooms.paymybuddyjg.repository.UserRepository;

@Service
public class UserService {

	
	@Autowired
	private UserRepository userRepository;
	
	public Iterable<User> getProducts() {
		return userRepository.findAll();
	}
	
	public Optional<User> getUserById(Integer id) {
		return userRepository.findById(id);
	}
}
