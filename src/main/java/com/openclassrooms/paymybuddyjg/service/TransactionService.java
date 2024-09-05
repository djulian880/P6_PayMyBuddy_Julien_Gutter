package com.openclassrooms.paymybuddyjg.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddyjg.model.Transaction;
import com.openclassrooms.paymybuddyjg.model.User;
import com.openclassrooms.paymybuddyjg.repository.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	public Iterable<Transaction> getTransactions() {
		return transactionRepository.findAll();
	}
	
	public Optional<Transaction> getTransactionById(Integer id) {
		return transactionRepository.findById(id);
	}

	public Transaction saveTransaction(Transaction transaction) {
		return transactionRepository.save(transaction);
	}
	
	public void deleteTransactionById(Integer id) {
		transactionRepository.deleteById(id);
	}
	
	public Iterable<Transaction> getTransactionsBySender(User sender) {
		return transactionRepository.findBySender(sender);
	}
}
