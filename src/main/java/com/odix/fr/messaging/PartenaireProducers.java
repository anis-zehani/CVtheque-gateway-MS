package com.odix.fr.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.odix.fr.model.Partenaire;

@Service
public class PartenaireProducers {
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
    @Autowired
    public KafkaTemplate<String, String> kafkaTemplate;

    public void addPartenaireProducer(Partenaire partenaire) {
        try{
			String value = OBJECT_MAPPER.writeValueAsString(partenaire);
			System.out.print(String.format("#### -> addPartenaireProducer : Gateway -> %s", value + "\n"));
			this.kafkaTemplate.send("add-partenaire-topic", value);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
