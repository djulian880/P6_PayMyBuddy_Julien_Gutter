package com.openclassrooms.paymybuddyjg.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddyjg.model.Connection;
import com.openclassrooms.paymybuddyjg.model.Transaction;
import com.openclassrooms.paymybuddyjg.repository.ConnectionRepository;

@Service
public class ConnectionService {
	@Autowired
	private ConnectionRepository connectionRepository;
	
	public Iterable<Connection> getConnections() {
		return connectionRepository.findAll();
	}
	
	public Optional<Connection> getConnectionById(Integer id) {
		return connectionRepository.findById(id);
	}
	
	public Connection saveConnection(Connection connection) {
		return connectionRepository.save(connection);
	}
	
	public void deleteConnectionById(Integer id) {
		connectionRepository.deleteById(id);
	}

}
