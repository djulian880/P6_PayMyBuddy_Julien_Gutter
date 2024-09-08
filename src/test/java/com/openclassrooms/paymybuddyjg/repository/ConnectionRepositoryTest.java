package com.openclassrooms.paymybuddyjg.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.openclassrooms.paymybuddyjg.model.Connection;
/*
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // Utiliser la configuration réelle de la base de données
@ActiveProfiles("test")  // Utiliser le profil de test pour charger 'application-test.properties'
*/
public class ConnectionRepositoryTest {
/*
    @Autowired
    private ConnectionRepository connectionRepository;

    @Test
    public void testSaveAndFindById() {
        // Given
        Connection connection = new Connection();
        connection.setUserId1(1);
        connection.setUserId2(2);
        connectionRepository.save(connection);

        // When
        Optional<Connection> found = connectionRepository.findById(connection.getId_Connection());

        // Then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getUserId1()).isEqualTo(1);
        assertThat(found.get().getUserId2()).isEqualTo(2);
    }

    /*
    @Test
    public void testUpdateConnection() {
        // Given
        Connection connection = new Connection();
        connection.setUserId1(1);
        connection.setUserId2(2);
        connectionRepository.save(connection);

        // When
        connection.setUserId1(3);  // Updating userId1
        connectionRepository.save(connection);
        Optional<Connection> updated = connectionRepository.findById(connection.getId_Connection());

        // Then
        assertThat(updated.isPresent()).isTrue();
        assertThat(updated.get().getUserId1()).isEqualTo(3);
        assertThat(updated.get().getUserId2()).isEqualTo(2);
    }

    @Test
    public void testDeleteConnection() {
        // Given
        Connection connection = new Connection();
        connection.setUserId1(1);
        connection.setUserId2(2);
        connectionRepository.save(connection);

        // When
        connectionRepository.deleteById(connection.getId_Connection());
        Optional<Connection> found = connectionRepository.findById(connection.getId_Connection());

        // Then
        assertThat(found.isEmpty()).isTrue();
    }

    @Test
    public void testExistenceOfConnection() {
        // Given
        Connection connection = new Connection();
        connection.setUserId1(1);
        connection.setUserId2(2);
        connectionRepository.save(connection);

        // When
        boolean exists = connectionRepository.existsById(connection.getId_Connection());

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    public void testFindAllConnections() {
        // Given
        Connection connection1 = new Connection();
        connection1.setUserId1(1);
        connection1.setUserId2(2);
        connectionRepository.save(connection1);

        Connection connection2 = new Connection();
        connection2.setUserId1(3);
        connection2.setUserId2(4);
        connectionRepository.save(connection2);

        // When
        Iterable<Connection> connections = connectionRepository.findAll();

        // Then
        assertThat(connections).hasSize(2);
    }*/
}
