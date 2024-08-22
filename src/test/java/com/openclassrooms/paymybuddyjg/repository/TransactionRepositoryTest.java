package com.openclassrooms.paymybuddyjg.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.openclassrooms.paymybuddyjg.model.Transaction;
import com.openclassrooms.paymybuddyjg.model.User;

@DataJpaTest
	@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Utilisation d'une vraie base de données pour les tests
	@ActiveProfiles("test") // Profil de test (vous pouvez configurer un profil spécifique pour les tests)
	public class TransactionRepositoryTest {

	    @Autowired
	    private TransactionRepository transactionRepository;

	 @Test
	    public void testSaveTransaction() {
	        // Given
	        User sender = new User(); // Supposons que User a un constructeur vide
	        sender.setId_user(1); // Assurez-vous que ces ID existent dans la base de données
	        User receiver = new User();
	        receiver.setId_user(2);

	        Transaction transaction = new Transaction();
	        transaction.setDescription("Test transaction");
	        transaction.setAmount(100.0);
	        transaction.setSender(sender);
	        transaction.setReceiver(receiver);

	        // When
	        Transaction savedTransaction = transactionRepository.save(transaction);

	        // Then
	        assertThat(savedTransaction).isNotNull();
	        assertThat(savedTransaction.getId_Transaction()).isGreaterThan(0);
	        assertThat(savedTransaction.getSender()).isEqualTo(sender);
	        assertThat(savedTransaction.getReceiver()).isEqualTo(receiver);
	    }

	    @Test
	    public void testFindById() {
	        // Given
	        User sender = new User();
	        sender.setId_user(1);
	        User receiver = new User();
	        receiver.setId_user(2);

	        Transaction transaction = new Transaction();
	        transaction.setDescription("Test transaction");
	        transaction.setAmount(100.0);
	        transaction.setSender(sender);
	        transaction.setReceiver(receiver);

	        Transaction savedTransaction = transactionRepository.save(transaction);

	        // When
	        Optional<Transaction> foundTransaction = transactionRepository.findById(savedTransaction.getId_Transaction());

	        // Then
	        assertThat(foundTransaction).isPresent();
	        assertThat(foundTransaction.get().getDescription()).isEqualTo("Test transaction");
	        assertThat(foundTransaction.get().getSender().getId_user()).isEqualTo(1);
	        assertThat(foundTransaction.get().getReceiver().getId_user()).isEqualTo(2);
	    }

	    @Test
	    public void testDeleteTransaction() {
	        // Given
	        User sender = new User();
	        sender.setId_user(1);
	        User receiver = new User();
	        receiver.setId_user(2);

	        Transaction transaction = new Transaction();
	        transaction.setDescription("Test transaction");
	        transaction.setAmount(100.0);
	        transaction.setSender(sender);
	        transaction.setReceiver(receiver);

	        Transaction savedTransaction = transactionRepository.save(transaction);
	        int transactionId = savedTransaction.getId_Transaction();

	        // When
	        transactionRepository.deleteById(transactionId);
	        Optional<Transaction> deletedTransaction = transactionRepository.findById(transactionId);

	        // Then
	        assertThat(deletedTransaction).isNotPresent();
	    }
}
