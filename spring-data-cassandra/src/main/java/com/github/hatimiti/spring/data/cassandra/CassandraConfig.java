package com.github.hatimiti.spring.data.cassandra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;

@Configuration
@PropertySource("classpath:cassandra.properties")
public class CassandraConfig extends AbstractCassandraConfiguration {

	@Autowired
	private Environment env;

	@Bean
    @Override
	public CassandraClusterFactoryBean cluster() {
		final CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
		cluster.setContactPoints(env.getProperty("cassandra.contactpoints"));
		cluster.setPort(Integer.parseInt(env.getProperty("cassandra.port")));
		return cluster;
	}

	@Bean
	public CassandraMappingContext mappingContext() {
		return new CassandraMappingContext();
	}

	@Override
	protected String getKeyspaceName() {
		return env.getProperty("cassandra.keyspace");
	}

}