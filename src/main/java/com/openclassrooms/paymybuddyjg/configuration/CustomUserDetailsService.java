package com.openclassrooms.paymybuddyjg.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddyjg.service.UserService;


@Service
public class CustomUserDetailsService implements UserDetailsService {

	private static Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("Recherche utilisatueur: "+username);

		Optional<com.openclassrooms.paymybuddyjg.model.User> userreturned = userService.getUserByEmail(username);
		com.openclassrooms.paymybuddyjg.model.User userFound=null;
		if (userreturned.isPresent()) {
			userFound=userreturned.get();
			
		} else {
			logger.error("Utilisateur pas trouvé: "+username);
			throw new UsernameNotFoundException("Utilisateur avec l'email " + username + " non trouvé");
		}
		logger.debug(" utilisatueur trouvé: "+userFound.getEmail()+" pass:"+userFound.getPassword() );
		return new User(userFound.getEmail(), userFound.getPassword(), getGrantedAuthorities("USER"));
	}

	private List<GrantedAuthority> getGrantedAuthorities(String role) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		return authorities;
	}
}