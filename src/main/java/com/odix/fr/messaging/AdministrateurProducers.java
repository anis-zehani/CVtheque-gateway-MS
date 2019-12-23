package com.odix.fr.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.odix.fr.model.Administrateur;

@Service
public class AdministrateurProducers {
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
    @Autowired
    public KafkaTemplate<String, String> kafkaTemplate;

    public void addAdministrateurProducer(Administrateur administrateur) {
        try{
			String value = OBJECT_MAPPER.writeValueAsString(administrateur);
			System.out.print(String.format("#### -> addAdministrateurProducer : Gateway-MS -> %s", value + "\n"));
			this.kafkaTemplate.send("add-administrateur-topic", value);
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }

}
