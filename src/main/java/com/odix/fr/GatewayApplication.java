package com.odix.fr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.odix.fr.service.AdministrateurService;

@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class GatewayApplication {
	
	@Autowired
	private final AdministrateurService administrateurService;
	
	
	public GatewayApplication(AdministrateurService administrateurService) {
		super();
		this.administrateurService = administrateurService;
		//Si SuperAdmin n'existe pas, je le met en place
		this.administrateurService.verifyOrAddAdmin();

	}

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

}
