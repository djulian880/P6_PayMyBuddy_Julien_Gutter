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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

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

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	/**
	 * /login Custom login page
	 * 
	 */
	@GetMapping("/login")
	public void getLoginPage() {

	}

	/**
	 * /logout Custom logoutpage, used to confirm logout by user
	 * 
	 */
	@GetMapping("/logout")
	public void getLogoutPage() {

	}

	/**
	 * /custom-logout Specific endpoint to unlog by the controller the user
	 * 
	 */
	@GetMapping("/custom-logout")
	public String customLogout() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		} else {
			logger.error("Erreur dans la déconnexion de l'utilisateur courant");
		}
		return "redirect:/login?logout=true";
	}

	/**
	 * /profile GET - Show the actual UserName and Email with additional field for
	 * password in order to validate them for update.
	 */
	@GetMapping("/profile")
	public void getProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		Optional<User> user = userService.getUserByEmail(userDetails.getUsername());
		if (user.isPresent()) {
			User userFound = user.get();
			// Evite l'affichage du mot de passe de l'utilisateurm
			userFound.setPassword("");
			model.addAttribute("user", userFound);
		} else {
			logger.error("Impossible de récupérer l'utilisateur connecté");
		}
	}

	/**
	 * /profile POST - update the actual user fields
	 * 
	 */
	@PostMapping("/profile")
	public String updateProfile(Model model, @AuthenticationPrincipal UserDetails userDetails,
			@ModelAttribute User userUpdated) {
		Optional<User> user = userService.getUserByEmail(userDetails.getUsername());
		if (user.isPresent()) {
			User userLogged = user.get();
			if (userUpdated.getUserName().isEmpty() || userUpdated.getEmail().isEmpty()
					|| userUpdated.getPassword().isEmpty()) {
				return "redirect:/profile?error=true";
				
			} else {
				userLogged.setEmail(userUpdated.getEmail());
				userLogged.setUserName(userUpdated.getUserName());
				userLogged.setPassword(passwordEncoder.encode(userUpdated.getPassword()));
				userService.saveUser(userLogged);
				return "redirect:/profile?updateOK=true";
			}
		} else {
			logger.error("Impossible de récupérer l'utilisateur connecté");
			return "redirect:/profile";
		}
	}

	/**
	 * /transfer GET - Show the list of transfer made and fields to make a new
	 * transfer
	 */
	@GetMapping("/transfer")
	public void getTransfers(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		Optional<User> user = userService.getUserByEmail(userDetails.getUsername());
		if (user.isPresent()) {
			User userFound = user.get();

			// Build the list of relations for displaying
			List<String> relations = new ArrayList<String>();
			for (int i = 0; i < userFound.getConnections().size(); i++) {
				relations.add(userFound.getConnections().get(i).getUserName());
			}
			model.addAttribute("relations", relations);

			// Prepare the list of transactions for displaying
			List<Transaction> listTransactions = new ArrayList<Transaction>();
			Iterator<Transaction> iterator = transactionService.getTransactionsBySender(userFound).iterator();
			while (iterator.hasNext()) {
				Transaction element = iterator.next();
				listTransactions.add(element);
			}
			model.addAttribute("transactions", listTransactions);

		} else {
			logger.error("Impossible de récupérer l'utilisateur connecté");
		}
	}

	/**
	 * /transfer POST - Create a new transfer with the parameters passed.
	 */
	@PostMapping("/transfer")
	public String getTransfers(Model model, @ModelAttribute TransactionDTO transactionDTO,
			@AuthenticationPrincipal UserDetails userDetails) {
		Optional<User> user = userService.getUserByEmail(userDetails.getUsername());
		if (user.isPresent()) {
			User userFound = user.get();
			Optional<User> userRelation = userService.getUserByUserName(transactionDTO.selectedRelation);
			if (userRelation.isPresent()) {
				User userReceiver = userRelation.get();
				Transaction transaction = new Transaction();
				transaction.setAmount(transactionDTO.amount);
				transaction.setDescription(transactionDTO.description);
				transaction.setSender(userFound);
				transaction.setReceiver(userReceiver);
				transactionService.saveTransaction(transaction);
				logger.info("Transaction correctement enregistrée");
				return "redirect:/transfer";
			} else {
				logger.debug("Utilisateur receveur non récupéré: " + transactionDTO.selectedRelation);
			}
		} else {
			logger.error("Impossible de récupérer l'utilisateur connecté");
		}
		return "redirect:/transfer";
	}

	/**
	 * /signin GET - Enable a new user to sign in, this page is accessed by the
	 * login page in case of failure of login.
	 * 
	 */
	@GetMapping("/signin")
	public void getSignin(Model model) {

	}

	/**
	 * /signin POST - Register a new user with the username, email and password
	 * given.
	 * 
	 */
	@PostMapping("/signin")
	public String postSignin(Model model, @ModelAttribute User newUser) {
		Optional<User> user = userService.getUserByEmail(newUser.getEmail());
		if (user.isPresent()) {
			logger.info("Utilisateur déjà existant.");
			return "/signin?error=true";

		} else {
			newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
			userService.saveUser(newUser);
			logger.info("Nouvel utilisateur créé.");
			return "redirect:/login";
		}
	}

	/**
	 * /relation GET - Shows a form to add a new relation by email.
	 * 
	 */
	@GetMapping("/relation")
	public void getRelations(Model model) {

	}

	/**
	 * /relation POST - Adds the current email given in the relation's list.
	 * 
	 */
	@PostMapping("/relation")
	public String postRelations(Model model, @AuthenticationPrincipal UserDetails userDetails,
			@RequestParam("email") String email) {
		Optional<User> user = userService.getUserByEmail(userDetails.getUsername());
		if (user.isPresent()) {
			User loggedUser = user.get();
			Optional<User> searchedRelation = userService.getUserByEmail(email);
			if (searchedRelation.isPresent()) {
				User relation = searchedRelation.get();
				if (loggedUser.getConnections().contains(relation)) {
					logger.debug("Relation déjà existante");
					return "redirect:/relation?alreadyPresent=true";
				} else {
					loggedUser.getConnections().add(relation);
					userService.saveUser(loggedUser);
					logger.info("Relation ajoutée.");
				}
				return "redirect:/relation?addOK=true";
			} else {
				logger.debug("Relation non trouvée");
				return "redirect:/relation?error=true";
			}
		} else {
			logger.error("Impossible de récupérer l'utilisateur connecté");
			return "redirect:/relation";
		}
	}

}
