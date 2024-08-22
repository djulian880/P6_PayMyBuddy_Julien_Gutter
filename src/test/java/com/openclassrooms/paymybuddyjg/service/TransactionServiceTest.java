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

import com.openclassrooms.paymybuddyjg.model.Transaction;
import com.openclassrooms.paymybuddyjg.model.User;
import com.openclassrooms.paymybuddyjg.repository.TransactionRepository;

@SpringBootTest
public class TransactionServiceTest {
	@Autowired
	private TransactionService transactionService;

	@MockBean
	private TransactionRepository transactionRepository;

	private Transaction mockTransaction = new Transaction();
	private ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	
	@BeforeEach
	private void setUp() throws Exception {
		


		mockTransaction.setDescription("DescriptionTest");;
		mockTransaction.setAmount(12.3);
		mockTransaction.setId_Transaction(1);
		User sender=new User();
		sender.setEmail("test1@test.com");
		sender.setId_user(1);
		sender.setPassword("passtest1");
		sender.setUserName("username1");
		mockTransaction.setSender(sender);
		User receiver=new User();
		receiver.setEmail("test2@test.com");
		receiver.setId_user(2);
		receiver.setPassword("passtest2");
		receiver.setUserName("username2");
		mockTransaction.setReceiver(receiver);
	
		Mockito.when(transactionRepository.findById(1)).thenReturn(Optional.of(mockTransaction));
		Mockito.when(transactionRepository.save(mockTransaction)).thenReturn(mockTransaction);

		
		Transaction transaction1 = new Transaction();
		transaction1.setDescription("DescriptionTest");;
		transaction1.setAmount(12.3);
		transaction1.setId_Transaction(1);

		transaction1.setSender(sender);

		transaction1.setReceiver(receiver);
		Transaction transaction2 = new Transaction();
		transaction2.setDescription("DescriptionTest");;
		transaction2.setAmount(12.3);
		transaction2.setId_Transaction(1);
		transaction2.setSender(sender);
		transaction2.setReceiver(receiver);
		

		transactions.add(transaction1);
		transactions.add(transaction2);
		Mockito.when(transactionRepository.findAll()).thenReturn(transactions);
	}

	@Test
	public void testgetTransaction() throws Exception {

		assertEquals(Optional.of(mockTransaction), transactionService.getTransactionById(1));
	}

	@Test
	public void testdeleteTransaction() throws Exception {

		transactionService.deleteTransactionById(3);
	}

	@Test
	public void testsaveTransaction() throws Exception {

		assertEquals(mockTransaction, transactionService.saveTransaction(mockTransaction));
	}


	@Test
	public void testgetTransactions() throws Exception {

		assertEquals(transactions, transactionService.getTransactions());
	}
}
