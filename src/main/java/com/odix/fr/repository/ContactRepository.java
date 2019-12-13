package com.odix.fr.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.odix.fr.model.Contact;
import com.odix.fr.model.Utilisateur;

@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID> {
	
	Contact findByIdentite(@Param("identite") String identite);
	
	Contact findByEmail(@Param("email") String email);
	
	List<Contact> findAllByUtilisateur(@Param("utilisateur") Utilisateur utilisateur);
	
}
