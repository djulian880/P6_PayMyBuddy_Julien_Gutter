package com.openclassrooms.paymybuddyjg.model;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;


public class ConnectionTest {
	
	@Test
	public void testGettersAndSetters() {
		Connection connection = new Connection();
		connection.setUserId1(1);
		connection.setUserId2(2);
		connection.setId_Connection(3);

		assertThat(connection.getUserId1(), is(1));
		assertThat(connection.getUserId2(), is(2));
		assertThat(connection.getId_Connection(), is(3));
	}

	@Test
	public void testEqualsAndHashCode() {
		Connection connection1 = new Connection();
		connection1.setUserId1(1);
		connection1.setUserId1(2);
		connection1.setId_Connection(3);
		Connection connection2 = new Connection();
		connection2.setUserId1(1);
		connection2.setUserId1(2);
		connection2.setId_Connection(3);

		assertThat(connection1, is(connection2));
		assertThat(connection1.hashCode(), is(connection2.hashCode()));
	}

	@Test
	public void testToString() {
		Connection connection = new Connection();
		connection.setUserId1(1);
		connection.setUserId2(2);
		connection.setId_Connection(3);

		assertThat(connection.toString(), containsString("userId1=1"));
		assertThat(connection.toString(), containsString("userId2=2"));
		assertThat(connection.toString(), containsString("id_Connection=3"));
	}
}
