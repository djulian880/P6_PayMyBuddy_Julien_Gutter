package com.openclassrooms.paymybuddyjg.model;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class UserTest {

	@Test
	public void testGettersAndSetters() {

		User user1=new User();
		user1.setEmail("test1@test.com");
		user1.setId_user(1);
		user1.setPassword("passtest1");
		user1.setUserName("username1");

		assertThat(user1.getEmail(), is("test1@test.com"));
		assertThat(user1.getId_user(), is(1));
		assertThat(user1.getPassword(), is("passtest1"));
		assertThat(user1.getUserName(), is("username1"));

	}

	@Test
	public void testEqualsAndHashCode() {
		
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
		


		assertThat(user1, is(user2));
		assertThat(user1.hashCode(), is(user2.hashCode()));
	}

	@Test
	public void testToString() {
		User user1=new User();
		user1.setEmail("test1@test.com");
		user1.setId_user(1);
		user1.setPassword("passtest1");
		user1.setUserName("username1");

		assertThat(user1.toString(), containsString("email=test1@test.com"));
		assertThat(user1.toString(), containsString("id_user=1"));
		assertThat(user1.toString(), containsString("password=passtest1"));
		assertThat(user1.toString(), containsString("userName=username1"));

	}
}
