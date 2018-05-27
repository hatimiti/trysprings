package com.github.hatimiti.spring.data.cassandra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * References:
 *  * Docker-Cassandra: https://hub.docker.com/_/cassandra/
 *  * spring-data-cassandra: https://github.com/spring-projects/spring-data-cassandra
 *  *                      : https://docs.spring.io/spring-data/cassandra/docs/current/reference/html/
 *  * sample: https://github.com/springframeworkguru/spring-boot-cassandra-example
 */
@SpringBootApplication
public class Main {
	public static void main(final String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
