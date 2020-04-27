package com.odix.fr.webClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.odix.fr.model.POJONotification;
import com.odix.fr.model.PartenaireTemporaire;

import feign.Headers;

@FeignClient(name = "notifications-ms:8004", fallback = NotificationClient.NotificationClientFallback.class)
public interface NotificationClient {

	@PostMapping("/api/notification/generateSimpleNotification")
	@Headers("Content-Type: application/json")
	void generateSimpleNotification(@RequestBody POJONotification pojoNotification);
	
	@PostMapping("/api/notification/deactivateNotificationsByPartenaireTemporaire")
	@Headers("Content-Type: application/json")
	void deactivateNotificationsByPartenaireTemporaire(@RequestBody PartenaireTemporaire partenaireTemporaire);
	
	@Component
    public static class NotificationClientFallback {
  
        public void generateSimpleNotification(@RequestBody POJONotification pojoNotification) {
            System.out.println("generateSimpleNotification : "+pojoNotification.toString());
        }
        
        public void deactivateNotificationsByPartenaireTemporaire(@RequestBody PartenaireTemporaire partenaireTemporaire) {
            System.out.println("deactivateNotificationsByPartenaireTemporaire : "+partenaireTemporaire.toString());
        }
    }
}
