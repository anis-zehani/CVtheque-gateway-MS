package com.odix.fr.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.odix.fr.model.Utilisateur;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, UUID> {
	
	Utilisateur findUtilisateurById(@Param("id") UUID id);
	
	Utilisateur findUtilisateurByUsername(@Param("username") String username);
	
	Utilisateur findUtilisateurByEmail(@Param("email") String email);
	
	// Utilisateur via son role
	@Query(value = "SELECT * FROM utilisateur u WHERE u.dtype like ?1 ", nativeQuery = true)
	Utilisateur findUtilisateurByDtype(@Param("role") String role);
	
	@Query(value = "SELECT dtype FROM utilisateur u WHERE u.username like ?1 ", nativeQuery = true)
	String findUtilisateurRoleByUsername(@Param("username") String username);
}
