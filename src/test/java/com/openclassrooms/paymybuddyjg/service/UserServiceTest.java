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

import com.openclassrooms.paymybuddyjg.model.User;
import com.openclassrooms.paymybuddyjg.model.User;
import com.openclassrooms.paymybuddyjg.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {
	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	private User mockUser = new User();
	private ArrayList<User> users = new ArrayList<User>();
	
	@BeforeEach
	private void setUp() throws Exception {
		
		mockUser.setEmail("test1@test.com");
		mockUser.setId_user(1);
		mockUser.setPassword("passtest1");
		mockUser.setUserName("username1");


	
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
		Mockito.when(userRepository.save(mockUser)).thenReturn(mockUser);

		User user1=new User();
		user1.setEmail("test1@test.com");
		user1.setId_user(1);
		user1.setPassword("passtest1");
		user1.setUserName("username1");

		User user2=new User();
		user2.setEmail("test1@test.com");
		user2.setId_user(1);
		user2.setPassword("passtest1");
		user2.setUserName("username1");
		

		users.add(user1);
		users.add(user2);
		Mockito.when(userRepository.findAll()).thenReturn(users);
	}

	@Test
	public void testgetUser() throws Exception {

		assertEquals(Optional.of(mockUser), userService.getUserById(1));
	}

	@Test
	public void testdeleteUser() throws Exception {

		userService.deleteUserById(3);
	}

	@Test
	public void testsaveUser() throws Exception {

		assertEquals(mockUser, userService.saveUser(mockUser));
	}


	@Test
	public void testgetUsers() throws Exception {

		assertEquals(users, userService.getUsers());
	}
}
