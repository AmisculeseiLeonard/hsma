package com.healthcaresocialmedia.relationshipservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.neo4j.config.EnableNeo4jAuditing;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableNeo4jRepositories
@EnableNeo4jAuditing
public class RelationshipServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RelationshipServiceApplication.class, args);
	}

}
