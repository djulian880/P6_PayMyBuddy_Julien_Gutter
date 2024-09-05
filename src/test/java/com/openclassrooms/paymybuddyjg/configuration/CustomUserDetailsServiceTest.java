package com.openclassrooms.paymybuddyjg.configuration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
// import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.openclassrooms.paymybuddyjg.service.UserService;
import com.openclassrooms.paymybuddyjg.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomUserDetailsServiceTest {
	@Mock
    private UserService userService;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    public void setup() {
        // Initialiser les mocks avant chaque test
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername_UserFound() {
        // Créer un utilisateur fictif
    	User userMock = new User();
        userMock.setEmail("test@example.com");
        userMock.setPassword("password123");

        // Simuler le comportement de userService.getUserByEmail
        when(userService.getUserByEmail("test@example.com")).thenReturn(Optional.of(userMock));

        // Appeler la méthode loadUserByUsername et vérifier le résultat
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("test@example.com");

        // Vérifier que l'utilisateur a été trouvé et que les détails sont corrects
        assertNotNull(userDetails);
        assertEquals("test@example.com", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));

        // Vérifier que le service a été appelé une fois
        verify(userService, times(1)).getUserByEmail("test@example.com");
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Simuler le comportement de userService pour qu'il retourne un utilisateur absent
        when(userService.getUserByEmail("unknown@example.com")).thenReturn(Optional.empty());

        // Appeler la méthode loadUserByUsername et vérifier qu'une exception est lancée
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("unknown@example.com");
        });

        // Vérifier que le service a été appelé une fois
        verify(userService, times(1)).getUserByEmail("unknown@example.com");
    }
}
