package com.odix.fr.service;

import java.util.UUID;

import com.odix.fr.model.Utilisateur;

public interface UtilisateurService {
	
	public Utilisateur getUtilisateurById(UUID id);
	
	public Utilisateur getUtilisateurByUsername(String username);
	
	public Utilisateur getUtilisateurByEmail(String email);
	
	public Utilisateur getUtilisateurByRole(String role);
	
	public Boolean sendEmailResetPassword(String email);
	
	public Utilisateur resetPasswordUtilisateur(String email, String password);
	
	public String getUtilisateurRoleByUsername(String username);
	
}
