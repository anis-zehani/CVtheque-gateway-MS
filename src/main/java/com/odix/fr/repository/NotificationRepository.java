package com.odix.fr.repository;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.odix.fr.model.Etat;
import com.odix.fr.model.Notification;
import com.odix.fr.model.PartenaireTemporaire;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
	

	// La liste des candidats pour une Opportunité donnée
	@Query(value = "SELECT * FROM Notification n  "
			+ "WHERE n.etat_notification like 'True' "
			+ "AND n.id IN "
			+ "(SELECT id_notification FROM notification_destinataire nd WHERE nd.id_destinataire = ?1) ", nativeQuery = true)
	List<Notification> findByIdDestinataireAndEtatNotification(@Param("idDestinataire") UUID idDestinataire, @Param("etatNotification") Etat etatNotification);

	@Modifying
	@Transactional
	@Query(value = "UPDATE Notification "
			+ "SET etat_notification = 'False' "
			+ "WHERE id = ?1 ", nativeQuery = true)
	void deactivateNotification(@Param("idNotification") UUID idNotification);
	
	Notification findByPartenaireTemporaireNotification(@Param("partenaireTemporaire") PartenaireTemporaire partenaireTemporaire);
	
}
