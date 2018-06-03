package com.github.hatimiti.spring.data.cassandra;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.QueryOptions;
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
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

import static com.datastax.driver.core.PoolingOptions.DEFAULT_HEARTBEAT_INTERVAL_SECONDS;
import static com.datastax.driver.core.QueryOptions.DEFAULT_CONSISTENCY_LEVEL;
import static com.datastax.driver.core.QueryOptions.DEFAULT_FETCH_SIZE;
import static com.datastax.driver.core.SocketOptions.DEFAULT_CONNECT_TIMEOUT_MILLIS;
import static com.datastax.driver.core.SocketOptions.DEFAULT_READ_TIMEOUT_MILLIS;
import static com.datastax.driver.core.policies.LatencyAwarePolicy.Builder.*;
import static java.lang.Boolean.parseBoolean;

@Configuration
@EnableCassandraRepositories
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
		cluster.setLoadBalancingPolicy(createLoadBalancingPolicy());
		cluster.setRetryPolicy(createRetryPolicy());
		cluster.setReconnectionPolicy(createReconnectionPolicy());
		cluster.setSocketOptions(createSocketOptions());
		cluster.setQueryOptions(createQueryOptions());
		cluster.setPoolingOptions(createPoolingOptions());
		cluster.setJmxReportingEnabled(parseBoolean(env.getProperty("cassandra.jmxReportingEnabled")));
		cluster.setMetricsEnabled(parseBoolean(env.getProperty("cassandra.metricsEnabled")));
		return cluster;
	}

	private RetryPolicy createRetryPolicy() {
		return new LoggingRetryPolicy(DefaultRetryPolicy.INSTANCE);
	}

	private PoolingOptions createPoolingOptions() {
		final PoolingOptions o = new PoolingOptions();
		o.setHeartbeatIntervalSeconds(env.getProperty("cassandra.heartbeatIntervalSeconds",
				Integer.class, DEFAULT_HEARTBEAT_INTERVAL_SECONDS));
		return o;
	}

	private QueryOptions createQueryOptions() {
		final QueryOptions o = new QueryOptions();
		o.setConsistencyLevel(ConsistencyLevel.valueOf(
				env.getProperty("cassandra.consistencyLevel", DEFAULT_CONSISTENCY_LEVEL.name())));
		o.setFetchSize(env.getProperty("cassandra.fetchSize", Integer.class, DEFAULT_FETCH_SIZE));
		return o;
	}

	private ReconnectionPolicy createReconnectionPolicy() {
		return new ConstantReconnectionPolicy(
				env.getProperty("cassandra.reconnectionDelayMS", Integer.class, 1000));
	}

	private SocketOptions createSocketOptions() {
		return new SocketOptions()
				.setConnectTimeoutMillis(env.getProperty("cassandra.connectTimeoutMS",
						Integer.class, DEFAULT_CONNECT_TIMEOUT_MILLIS))
				.setReadTimeoutMillis(env.getProperty("cassandra.readTimeoutMS",
						Integer.class, DEFAULT_READ_TIMEOUT_MILLIS));
	}

	@Bean
	public CassandraMappingContext mappingContext() {
		return new CassandraMappingContext();
	}

	@Override
	protected String getKeyspaceName() {
		return env.getProperty("cassandra.keyspace");
	}

	private LoadBalancingPolicy createLoadBalancingPolicy() {
		final String policy = env.getProperty("cassandra.loadBalancingPolisy", "");
		final LoadBalancingPolicy child;
		switch (policy) {
			case "RoundRobinPolicy":
				child = createRoundRobinPolicy();
				break;
			case "DCAwareRoundRobinPolicy":
			default:
				child = createDCAwareRoundRobinPolicy();
				break;
		}
		return wrapByHostFilterPolicy(
				wrapByLatencyAwarePolicy(
						wrapByTokenAwarePolicy(child)));
	}

	private DCAwareRoundRobinPolicy createDCAwareRoundRobinPolicy() {
		final DCAwareRoundRobinPolicy.Builder builder = DCAwareRoundRobinPolicy.builder();
		final String localDC = env.getProperty("cassandra.localDc", "");
		if (!StringUtils.isEmpty(localDC)) {
			builder.withLocalDc(localDC);
		}
		final int usedHostsPerRemoteDc = env.getProperty("cassandra.DCAwareRoundRobinPolicy.usedHostsPerRemoteDc", Integer.class, 0);
		if (0 < usedHostsPerRemoteDc) {
			builder.withUsedHostsPerRemoteDc(usedHostsPerRemoteDc);
		}
		if (!parseBoolean(env.getProperty("cassandra.DCAwareRoundRobinPolicy.allowRemoteDCsForLocalConsistencyLevel", ""))) {
			builder.allowRemoteDCsForLocalConsistencyLevel();
		}
		return builder.build();
	}

	private RoundRobinPolicy createRoundRobinPolicy() {
		final RoundRobinPolicy r = new RoundRobinPolicy();
		return r;
	}

	private LoadBalancingPolicy wrapByHostFilterPolicy(final LoadBalancingPolicy child) {
		if (!parseBoolean(env.getProperty("cassandra.HostFilterPolicy.enabled", ""))) {
			return child;
		}

		final String whiteList = env.getProperty("cassandra.HostFilterPolicy.fromDCWhiteList", "");

		if (!StringUtils.isEmpty(whiteList)) {
			return HostFilterPolicy.fromDCWhiteList(child, StringUtils.commaDelimitedListToSet(whiteList));
		}

		final String blackList = env.getProperty("cassandra.HostFilterPolicy.fromDCBlackList", "");
		if (!StringUtils.isEmpty(blackList)) {
			return HostFilterPolicy.fromDCBlackList(child, StringUtils.commaDelimitedListToSet(blackList));
		}

		return child;
	}

	private LoadBalancingPolicy wrapByTokenAwarePolicy(final LoadBalancingPolicy child) {
		if (!parseBoolean(env.getProperty("cassandra.TokenAwarePolicy.enabled", ""))) {
			return child;
		}
		final TokenAwarePolicy p = new TokenAwarePolicy(child);
		return p;
	}

	private LoadBalancingPolicy wrapByLatencyAwarePolicy(final LoadBalancingPolicy child) {

		if (!parseBoolean(env.getProperty("cassandra.LatencyAwarePolicy.enabled", ""))) {
			return child;
		}

		final LatencyAwarePolicy.Builder builder = LatencyAwarePolicy.builder(child);
		final int nanoToMS = 1000 * 1000;

		builder.withExclusionThreshold(env.getProperty("cassandra.LatencyAwarePolicy.exclusionThreshold",
				Double.class, DEFAULT_EXCLUSION_THRESHOLD));
		builder.withMininumMeasurements(env.getProperty("cassandra.LatencyAwarePolicy.mininumMeasurements",
				Integer.class, DEFAULT_MIN_MEASURE));
		builder.withRetryPeriod(env.getProperty("cassandra.LatencyAwarePolicy.retryPeriodMS",
				Long.class, DEFAULT_RETRY_PERIOD_NANOS * nanoToMS), TimeUnit.MILLISECONDS);
		builder.withScale(env.getProperty("cassandra.LatencyAwarePolicy.scaleMS",
				Long.class, DEFAULT_SCALE_NANOS * nanoToMS), TimeUnit.MILLISECONDS);
		builder.withUpdateRate(env.getProperty("cassandra.LatencyAwarePolicy.updateRateMS",
				Long.class, DEFAULT_UPDATE_RATE_NANOS * nanoToMS), TimeUnit.MILLISECONDS);

		return builder.build();
	}
}
