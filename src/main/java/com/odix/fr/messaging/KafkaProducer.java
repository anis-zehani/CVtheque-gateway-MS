package com.odix.fr.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

	public static final String TOPIC1 = "administrateurAdded-topic";

    @Autowired
    public KafkaTemplate<String, String> kafkaTemplate;

    public void produceAdministrateurAdded(String message) {
        System.out.print(String.format("#### -> administrateurAdded Produced message -> %s", message + "\n"));
        this.kafkaTemplate.send(TOPIC1, message);
    }
}