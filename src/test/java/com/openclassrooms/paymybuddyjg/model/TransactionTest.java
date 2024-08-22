package com.openclassrooms.paymybuddyjg.model;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;



public class TransactionTest {

	@Test
	public void testGettersAndSetters() {
		Transaction transaction = new Transaction();
		transaction.setDescription("DescriptionTest");;
		transaction.setAmount(12.3);
		transaction.setId_Transaction(1);
		User sender=new User();
		sender.setEmail("test1@test.com");
		sender.setId_user(1);
		sender.setPassword("passtest1");
		sender.setUserName("username1");
		transaction.setSender(sender);
		User receiver=new User();
		receiver.setEmail("test2@test.com");
		receiver.setId_user(2);
		receiver.setPassword("passtest2");
		receiver.setUserName("username2");
		transaction.setReceiver(receiver);
		assertThat(transaction.getDescription(), is("DescriptionTest"));
		assertThat(transaction.getAmount(), is(12.3));
		assertThat(transaction.getId_Transaction(), is(1));
		assertThat(transaction.getSender(), is(sender));
		assertThat(transaction.getReceiver(), is(receiver));
	}

	@Test
	public void testEqualsAndHashCode() {
		Transaction transaction1 = new Transaction();
		transaction1.setDescription("DescriptionTest");;
		transaction1.setAmount(12.3);
		transaction1.setId_Transaction(1);
		User sender=new User();
		sender.setEmail("test1@test.com");
		sender.setId_user(1);
		sender.setPassword("passtest1");
		sender.setUserName("username1");
		transaction1.setSender(sender);
		User receiver=new User();
		receiver.setEmail("test2@test.com");
		receiver.setId_user(2);
		receiver.setPassword("passtest2");
		receiver.setUserName("username2");
		transaction1.setReceiver(receiver);
		Transaction transaction2 = new Transaction();
		transaction2.setDescription("DescriptionTest");;
		transaction2.setAmount(12.3);
		transaction2.setId_Transaction(1);
		transaction2.setSender(sender);
		transaction2.setReceiver(receiver);

		assertThat(transaction1, is(transaction2));
		assertThat(transaction1.hashCode(), is(transaction2.hashCode()));
	}

	@Test
	public void testToString() {
		Transaction transaction = new Transaction();
		transaction.setDescription("DescriptionTest");;
		transaction.setAmount(12.3);
		transaction.setId_Transaction(1);

		assertThat(transaction.toString(), containsString("description=DescriptionTest"));
		assertThat(transaction.toString(), containsString("id_Transaction=1"));
		assertThat(transaction.toString(), containsString("amount=12.3"));

	}
}
