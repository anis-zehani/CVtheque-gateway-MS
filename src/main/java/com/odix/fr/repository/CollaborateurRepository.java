package com.odix.fr.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.odix.fr.model.Collaborateur;

@Repository
public interface CollaborateurRepository extends JpaRepository<Collaborateur, UUID> {
	
	Collaborateur findByIdentite(@Param("identite") String identite);
	
	Collaborateur findByUsername(@Param("username") String username);

}
