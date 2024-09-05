package com.openclassrooms.paymybuddyjg.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.openclassrooms.paymybuddyjg.DTO.TransactionDTO;
import com.openclassrooms.paymybuddyjg.model.Transaction;
import com.openclassrooms.paymybuddyjg.model.User;
import com.openclassrooms.paymybuddyjg.service.TransactionService;
import com.openclassrooms.paymybuddyjg.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Controller
public class PayMyBuddyController {

	@Autowired
	UserService userService;
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;    
	    
	private static Logger logger = LoggerFactory.getLogger(PayMyBuddyController.class);
	
	@GetMapping("/login")
	public void getLoginPage() {
	
    }
	
	@GetMapping("/logout")
	public void getLogoutPage() {
	
    }
	
    @GetMapping("/custom-logout")
    public String customLogout() {
        // Obtenir le contexte de sécurité actuel
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth != null) {
            // Utiliser SecurityContextLogoutHandler pour déconnecter l'utilisateur
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        // Redirection après la déconnexion
        return "redirect:/login?logout=true";
    }
    
	@GetMapping("/profile")
	public void getProfile(Model model,@AuthenticationPrincipal UserDetails userDetails) {
		Optional<User> user = userService.getUserByEmail(userDetails.getUsername());
		if (user.isPresent()) {
			User userFound=user.get();
			userFound.setPassword("");
			model.addAttribute("user",userFound);
		} else {
		//TODO
		}
    }
	
	@PostMapping("/profile")
	public String updateProfile(Model model,@AuthenticationPrincipal UserDetails userDetails,@ModelAttribute User userUpdated) {
		Optional<User> user = userService.getUserByEmail(userDetails.getUsername());
		if (user.isPresent()) {
			User userLogged=user.get();
			
			
			if(userUpdated.getUserName().isEmpty() || userUpdated.getEmail().isEmpty() || userUpdated.getPassword().isEmpty()) {
				return "redirect:/profile?error=true";
			}
			else {
				
				userLogged.setEmail(userUpdated.getEmail());
				userLogged.setUserName(userUpdated.getUserName());
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String cryptedPassword=passwordEncoder.encode(userUpdated.getPassword());
				
				
				
				userLogged.setPassword(cryptedPassword);
				userService.saveUser(userLogged);
				return "redirect:/profile?updateOK=true";
			}
			
			
	
		} else {
		//TODO
			return "redirect:/profile";
		}
    }
	
	@GetMapping("/transfer")
	public void getTransfers(Model model,@AuthenticationPrincipal UserDetails userDetails) {
		Optional<User> user = userService.getUserByEmail(userDetails.getUsername());
		if (user.isPresent()) {
			User userFound=user.get();
			
			
			List<String> relations = new ArrayList<String>();
			for(int i=0;i<userFound.getConnections().size();i++) {
				relations.add(userFound.getConnections().get(i).getUserName());
			}
			model.addAttribute("relations", relations);
			System.out.println("Contenu: "+userFound.getConnections().size()+" telations:"+relations.size());
			
			
			
			List <Transaction> listTransactions=new ArrayList<Transaction>();
			Iterator<Transaction> iterator =transactionService.getTransactionsBySender(userFound).iterator();
			while (iterator.hasNext()) {
			    Transaction element = iterator.next();
			    listTransactions.add(element);
			}
			model.addAttribute("transactions",listTransactions);
			
		} else {
			//TODO
		}
    }
	
	@PostMapping("/transfer")
	public String getTransfers(Model model,@ModelAttribute TransactionDTO transactionDTO,@AuthenticationPrincipal UserDetails userDetails) {
		System.out.println("Contenu: "+transactionDTO.description+" "+transactionDTO.selectedRelation+" "+transactionDTO.amount);
		
		Optional<User> user = userService.getUserByEmail(userDetails.getUsername());
		if (user.isPresent()) {
			System.out.println("Utilisateur authetifié récupéré");
			User userFound=user.get();
			
			Transaction transaction=new Transaction();
			transaction.setAmount(transactionDTO.amount);
			transaction.setDescription(transactionDTO.description);
			transaction.setSender(userFound);
			
			System.out.println("Recherche utilisateur receveur "+transactionDTO.selectedRelation);
			Optional<User> userRelation = userService.getUserByUserName(transactionDTO.selectedRelation);
			if (userRelation.isPresent()) {
				System.out.println("Utilisateur receveur récupéré");
				User userReceiver=userRelation.get();
				transaction.setReceiver(userReceiver);
				transactionService.saveTransaction(transaction);
				return "redirect:/transfer";
			}
			
			else {
				//TODO
				System.out.println("Utilisateur receveur non récupéré: "+transactionDTO.selectedRelation);
			}

		} else {
			//TODO
		}
		return "redirect:/transfer";
    }
	
	@GetMapping("/signin")
	public void getSignin(Model model) {
	
    }
	
	
	@PostMapping("/signin")
	public String postSignin(Model model,@ModelAttribute User newUser) {
		System.out.println("Contenu: "+newUser.getUserName()+" "+newUser.getEmail()+" "+newUser.getPassword());
		// System.out.println("Contenu: "+responseFormat.username+" "+responseFormat.mail+" "+responseFormat.pass);
			Optional<User> user = userService.getUserByEmail(newUser.getEmail());
			if (user.isPresent()) {
				//TODO
				return "/signin?error=true";
				
			} else {
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String cryptedPassword=passwordEncoder.encode(newUser.getPassword());
				newUser.setPassword(cryptedPassword);
				userService.saveUser(newUser);
				return "redirect:/login";
			}
		 
    }
	
	@GetMapping("/relation")
	public void getRelations(Model model) {
		
    }
	
	@PostMapping("/relation")
	public String postRelations(Model model,@AuthenticationPrincipal UserDetails userDetails,@RequestParam("email") String email) {
		System.out.println(" POST relation");
		Optional<User> user = userService.getUserByEmail(userDetails.getUsername());
		if (user.isPresent()) {
			User loggedUser=user.get();
			System.out.println(" Utilisateur loggé:"+loggedUser.getUserName());
			//TODO
		
				Optional<User> searchedRelation = userService.getUserByEmail(email);
				System.out.println(" Recherche relation: "+email);
				if (searchedRelation.isPresent()) {
					System.out.println(" Relation trouvée");
					User relation=searchedRelation.get();
					
					if(loggedUser.getConnections().contains(relation)) {
						return "redirect:/relation?alreadyPresent=true";
					}else {
						loggedUser.getConnections().add(relation);
						userService.saveUser(loggedUser);
					}

					return "redirect:/relation?addOK=true";
				}else {
					System.out.println(" Relation pas trouvée");
					return "redirect:/relation?error=true";
				}		
		} else {
			System.out.println(" Utilisateur loggé pas trouvé");
			return "redirect:/relation";
		}
    }

	
}
