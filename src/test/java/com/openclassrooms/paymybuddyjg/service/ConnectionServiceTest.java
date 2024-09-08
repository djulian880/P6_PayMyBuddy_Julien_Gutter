package com.openclassrooms.paymybuddyjg.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.openclassrooms.paymybuddyjg.model.Connection;
import com.openclassrooms.paymybuddyjg.repository.ConnectionRepository;



@SpringBootTest
public class ConnectionServiceTest {
	@Autowired
	private ConnectionService connectionService;

	@MockBean
	private ConnectionRepository connectionRepository;

	private Connection mockConnection = new Connection();
	private ArrayList<Connection> connections = new ArrayList<Connection>();
	
	@BeforeEach
	private void setUp() throws Exception {
		
		mockConnection.setUserId1(1);
		mockConnection.setUserId2(2);
		mockConnection.setId_Connection(3);

	
		Mockito.when(connectionRepository.findById(3)).thenReturn(Optional.of(mockConnection));
		Mockito.when(connectionRepository.save(mockConnection)).thenReturn(mockConnection);

		
		
		Connection connection1 = new Connection();
		connection1.setUserId1(1);
		connection1.setUserId1(2);
		connection1.setId_Connection(3);
		Connection connection2 = new Connection();
		connection2.setUserId1(1);
		connection2.setUserId1(2);
		connection2.setId_Connection(3);
		

		connections.add(connection1);
		connections.add(connection2);
		Mockito.when(connectionRepository.findAll()).thenReturn(connections);
	}

	@Test
	public void testgetConnection() throws Exception {

		assertEquals(Optional.of(mockConnection), connectionService.getConnectionById(3));
	}

	@Test
	public void testdeleteConnection() throws Exception {

		connectionService.deleteConnectionById(3);
	}

	@Test
	public void testsaveConnection() throws Exception {

		assertEquals(mockConnection, connectionService.saveConnection(mockConnection));
	}


	@Test
	public void testgetConnections() throws Exception {

		assertEquals(connections, connectionService.getConnections());
	}

}
