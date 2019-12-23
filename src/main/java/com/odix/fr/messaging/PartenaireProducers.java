package com.odix.fr.messaging;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.odix.fr.model.Partenaire;

@Service
public class PartenaireProducers {
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
    @Autowired
    public KafkaTemplate<String, String> kafkaTemplate;

    public void addPartenaireProducer(Partenaire partenaire) {
        try{
			String value = OBJECT_MAPPER.writeValueAsString(partenaire);
			System.out.print(String.format("#### -> addPartenaireProducer : Gateway-MS -> %s", value + "\n"));
			this.kafkaTemplate.send("add-partenaire-topic", value);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void editPartenaireProducer(Partenaire partenaire) {
        try{
			String value = OBJECT_MAPPER.writeValueAsString(partenaire);
			System.out.print(String.format("#### -> editPartenaireProducer : Gateway-MS -> %s", value + "\n"));
			this.kafkaTemplate.send("edit-partenaire-topic", value);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void deletePartenaireProducer(UUID idPartenaire) {
        try{
			System.out.print(String.format("#### -> deletePartenaireProducer : Gateway-MS -> %s", idPartenaire + "\n"));
			this.kafkaTemplate.send("delete-partenaire-topic", idPartenaire.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }
}
