package com.openclassrooms.paymybuddyjg.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.openclassrooms.paymybuddyjg.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Utilise une vraie base de données pour les tests
@ActiveProfiles("test") // Profil de test
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        // Given
        User user = new User();
        user.setUserName("JohnDoe");
        user.setEmail("johndoe@example.com");
        user.setPassword("password123");

        // When
        User savedUser = userRepository.save(user);

        // Then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId_user()).isGreaterThan(0);
        assertThat(savedUser.getUserName()).isEqualTo("JohnDoe");
        assertThat(savedUser.getEmail()).isEqualTo("johndoe@example.com");
    }

    @Test
    public void testFindById() {
        // Given
        User user = new User();
        user.setUserName("JaneDoe");
        user.setEmail("janedoe@example.com");
        user.setPassword("password123");

        User savedUser = userRepository.save(user);

        // When
        Optional<User> foundUser = userRepository.findById(savedUser.getId_user());

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUserName()).isEqualTo("JaneDoe");
        assertThat(foundUser.get().getEmail()).isEqualTo("janedoe@example.com");
    }

    @Test
    public void testUpdateUser() {
        // Given
        User user = new User();
        user.setUserName("JohnSmith");
        user.setEmail("johnsmith@example.com");
        user.setPassword("password123");

        User savedUser = userRepository.save(user);

        // When
        savedUser.setEmail("john.smith@newemail.com");
        User updatedUser = userRepository.save(savedUser);

        // Then
        assertThat(updatedUser.getEmail()).isEqualTo("john.smith@newemail.com");
    }

    @Test
    public void testDeleteUser() {
        // Given
        User user = new User();
        user.setUserName("AliceDoe");
        user.setEmail("alicedoe@example.com");
        user.setPassword("password123");

        User savedUser = userRepository.save(user);
        int userId = savedUser.getId_user();

        // When
        userRepository.deleteById(userId);
        Optional<User> deletedUser = userRepository.findById(userId);

        // Then
        assertThat(deletedUser).isNotPresent();
    }

    @Test
    public void testUserConnections() {
        // Given
        User user1 = new User();
        user1.setUserName("User1");
        user1.setEmail("user1@example.com");
        user1.setPassword("password1");

        User user2 = new User();
        user2.setUserName("User2");
        user2.setEmail("user2@example.com");
        user2.setPassword("password2");

        User user3 = new User();
        user3.setUserName("User3");
        user3.setEmail("user3@example.com");
        user3.setPassword("password3");

        user1.getConnections().add(user2);
        user1.getConnections().add(user3);

        // When
        User savedUser = userRepository.save(user1);

        // Then
        assertThat(savedUser.getConnections()).hasSize(2);
        assertThat(savedUser.getConnections()).extracting("userName").containsExactlyInAnyOrder("User2", "User3");
    }
}
