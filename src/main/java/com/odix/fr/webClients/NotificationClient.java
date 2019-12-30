package com.odix.fr.webClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.odix.fr.model.POJONotification;
import com.odix.fr.model.PartenaireTemporaire;

import feign.Headers;

@FeignClient("notifications-MS")
public interface NotificationClient {

	@PostMapping("/api/notification/generateSimpleNotification")
	@Headers("Content-Type: application/json")
	void generateSimpleNotification(@RequestBody POJONotification pojoNotification);
	
	@PostMapping("/api/notification/deactivateNotificationsByPartenaireTemporaire")
	@Headers("Content-Type: application/json")
	void deactivateNotificationsByPartenaireTemporaire(@RequestBody PartenaireTemporaire partenaireTemporaire);
}
