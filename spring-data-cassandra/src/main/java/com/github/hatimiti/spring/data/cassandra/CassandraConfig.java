package com.github.hatimiti.spring.data.cassandra;

import com.datastax.driver.core.SocketOptions;
import com.datastax.driver.core.policies.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.thymeleaf.util.StringUtils;

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
		cluster.setLoadBalancingPolicy(createDCAwareRoundRobinPolicy());
		cluster.setRetryPolicy(createRetryPolicy());
		cluster.setReconnectionPolicy(createReconnectionPolicy());
		cluster.setSocketOptions(createSocketOptions());
		return cluster;
	}

	private DCAwareRoundRobinPolicy createDCAwareRoundRobinPolicy() {
		final DCAwareRoundRobinPolicy.Builder builder = DCAwareRoundRobinPolicy.builder();
		final String localDC = env.getProperty("cassandra.localDc", "");
		if (!StringUtils.isEmpty(localDC)) {
			builder.withLocalDc(localDC);
		}
		builder.withUsedHostsPerRemoteDc(env.getProperty("cassandra.usedHostsPerRemoteDc", Integer.class, 0));
		if (env.getProperty("cassandra.allowRemoteDCsForLocalConsistencyLevel", Boolean.class, true)) {
			builder.allowRemoteDCsForLocalConsistencyLevel();
		}
		return builder.build();
	}

	private RetryPolicy createRetryPolicy() {
		return new LoggingRetryPolicy(DefaultRetryPolicy.INSTANCE);
	}

	private ReconnectionPolicy createReconnectionPolicy() {
		return new ConstantReconnectionPolicy(
				env.getProperty("cassandra.reconnectionDelayMS", Integer.class, 1000));
	}

	private SocketOptions createSocketOptions() {
		return new SocketOptions()
				.setConnectTimeoutMillis(env.getProperty("cassandra.connectTimeoutMS", Integer.class, 1000))
				.setReadTimeoutMillis(env.getProperty("cassandra.readTimeoutMS", Integer.class, 1000));
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
