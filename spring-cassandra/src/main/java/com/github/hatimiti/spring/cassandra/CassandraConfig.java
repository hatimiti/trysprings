package com.github.hatimiti.spring.cassandra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.SimpleThreadScope;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;

@Configuration
@PropertySource("classpath:cassandra.properties")
public class CassandraConfig extends AbstractCassandraConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(CassandraConfig.class);

	@Autowired
	private Environment env;

	@Bean
	public CassandraClusterFactoryBean cluster() {
		CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
		cluster.setContactPoints(env.getProperty("cassandra.contactpoints"));
		cluster.setPort(Integer.parseInt(env.getProperty("cassandra.port")));
		return cluster;
	}

	@Bean
	public CassandraMappingContext mappingContext() {
	    new SimpleThreadScope();
		return new CassandraMappingContext();
	}

	@Override
	protected String getKeyspaceName() {
		return env.getProperty("cassandra.keyspace");
	}

}
