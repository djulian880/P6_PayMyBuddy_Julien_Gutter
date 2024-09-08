package com.openclassrooms.paymybuddyjg.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.openclassrooms.paymybuddyjg.model.Transaction;
import com.openclassrooms.paymybuddyjg.model.User;
import com.openclassrooms.paymybuddyjg.service.TransactionService;
import com.openclassrooms.paymybuddyjg.service.UserService;


@WebMvcTest(PayMyBuddyController.class)
public class PayMyBuddyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private TransactionService transactionService;

    private User mockUser;
    
    @BeforeEach
    public void setup() {
        // Configuration initiale si nécessaire
    	mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setUserName("testUser");
        mockUser.setPassword("testpass");
    }

    
    @Test
    @WithMockUser
    public void userLoginTest() throws Exception {
    	mockMvc.perform(formLogin("/login").user("user").password("user")).andExpect(authenticated());
    }
    
    
    @Test
   // @WithMockUser(username = "test@example.com", roles = "USER")
    public void testGetLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testGetLogoutPage() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testCustomLogout() throws Exception {
        mockMvc.perform(get("/custom-logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?logout=true"));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = "USER")
    public void testGetProfile() throws Exception {
        when(userService.getUserByEmail("test@example.com")).thenReturn(Optional.of(mockUser));

        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", mockUser));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = "USER")
    public void testUpdateProfile() throws Exception {
        when(userService.getUserByEmail("test@example.com")).thenReturn(Optional.of(mockUser));

        mockMvc.perform(post("/profile")
        		.with(csrf())
                .param("userName", "newUserName")
                .param("email", "new@example.com")
                .param("password", "newPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile?updateOK=true"));

        verify(userService).saveUser(any(User.class)); // Verifies that saveUser was called
    }

    @Test
    @WithMockUser
    public void testGetTransfers() throws Exception {
        when(userService.getUserByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
        mockMvc.perform(get("/transfer"))
                .andExpect(status().isOk());
    }

    
    @Test
    @WithMockUser(username = "test@example.com", roles = "USER")
    public void testPostTransfer() throws Exception {
        User mockSender = new User();
        mockSender.setEmail("test@example.com");
        User mockReceiver = new User();
        mockReceiver.setEmail("receiver@example.com");

        when(userService.getUserByEmail("test@example.com")).thenReturn(Optional.of(mockSender));
        when(userService.getUserByUserName("receiverUser")).thenReturn(Optional.of(mockReceiver));

        mockMvc.perform(post("/transfer")
        		.with(csrf())
                .param("amount", "100")
                .param("description", "Test transfer")
                .param("selectedRelation", "receiverUser"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/transfer"));

        verify(transactionService).saveTransaction(any(Transaction.class)); // Verifies that saveTransaction was called
    }

    @Test
    @WithMockUser(username = "test@example.com")
    public void testGetTransfers_UserFound() throws Exception {
        // Mocking the UserService and TransactionService
        
        User mockUser1 = new User();
        mockUser1.setEmail("test2@example.com");
        mockUser1.setUserName("friend1");
        mockUser1.setPassword("testpass1");
        
        User mockUser2 = new User();
        mockUser2.setEmail("test2@example.com");
        mockUser2.setUserName("friend2");
        mockUser2.setPassword("testpass2");
    	 	

    	mockUser.getConnections().add(mockUser1);
        mockUser.getConnections().add(mockUser2);
        
        Transaction mockTransaction1 = new Transaction();
        mockTransaction1.setSender(mockUser);
        mockTransaction1.setReceiver(mockUser1);
        Transaction mockTransaction2 = new Transaction();
        mockTransaction2.setSender(mockUser);
        mockTransaction2.setReceiver(mockUser2);
        when(userService.getUserByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
        when(transactionService.getTransactionsBySender(mockUser)).thenReturn(Arrays.asList(mockTransaction1, mockTransaction2));

        // Perform the GET request and check the response
        mockMvc.perform(get("/transfer")
        		.with(csrf()))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("relations"))
            .andExpect(model().attribute("relations", Arrays.asList("friend1", "friend2")))
            .andExpect(model().attributeExists("transactions"))
            .andExpect(view().name("transfer")); // Assure-toi que la vue attendue est 'transfer'
    }

    @WithMockUser(username = "test@example.com")
    public void testGetTransfers_UserNotFound() throws Exception {
        // Simuler le cas où l'utilisateur n'est pas trouvé
        when(userService.getUserByEmail("test@example.com")).thenReturn(Optional.empty());

        // Perform the GET request and check the response
        mockMvc.perform(get("/transfer")
        		.with(csrf()))
            .andExpect(status().isOk())
            .andExpect(model().attributeDoesNotExist("relations"))
            .andExpect(model().attributeDoesNotExist("transactions"))
            .andExpect(view().name("transfer")); // Assure-toi que la vue attendue est 'transfer'
    }
    
    @Test
    @WithMockUser
    public void testGetSignin() throws Exception {
        mockMvc.perform(get("/signin"))
                .andExpect(status().isOk());
    }

    
    @Test
    @WithMockUser(username = "test@example.com", roles = "USER")
    public void testPostSignin() throws Exception {
        mockMvc.perform(post("/signin")
        		.with(csrf())
                .param("userName", "newUser")
                .param("email", "newuser@example.com")
                .param("password", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(userService).saveUser(any(User.class)); // Verifies that saveUser was called
    }

    @Test
    @WithMockUser
    public void testGetRelations() throws Exception {
        mockMvc.perform(get("/relation"))
                .andExpect(status().isOk());
    }

    
    @Test
    @WithMockUser(username = "test@example.com", roles = "USER")
    public void testPostRelations() throws Exception {
        when(userService.getUserByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
        when(userService.getUserByEmail("relation@example.com")).thenReturn(Optional.of(new User()));

        mockMvc.perform(post("/relation")
        		.with(csrf())
                .param("email", "relation@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/relation?addOK=true"));

        verify(userService).saveUser(any(User.class)); // Verifies that saveUser was called
    }
}
