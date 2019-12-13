package com.odix.fr.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.odix.fr.model.Utilisateur;
import com.odix.fr.service.UtilisateurService;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	UtilisateurService utilisateurService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Utilisateur utilisateur = utilisateurService.getUtilisateurByUsername(username);
		
		if (utilisateur == null) {
			throw new UsernameNotFoundException("Utilisateur not found with username: " + username);
		}
		
		//Je récupére la GrantedAuthority de le BDD, colonne dtype : Ex 'ROLE_ADMINISTRATEUR'
		
		Set<GrantedAuthority> ROLES = new HashSet<>(); 
		
		ROLES.add(new SimpleGrantedAuthority(utilisateurService.getUtilisateurRoleByUsername(username)));

		return new org.springframework.security.core.userdetails.User(
				utilisateur.getUsername(), 
				utilisateur.getPassword(), 
				ROLES);
	}
}
