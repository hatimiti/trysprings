package com.github.hatimiti.spring.cassandra;

import com.github.hatimiti.spring.common.Utils;
import org.cassandraunit.spring.CassandraDataSet;
import org.cassandraunit.spring.CassandraUnitDependencyInjectionTestExecutionListener;
import org.cassandraunit.spring.EmbeddedCassandra;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestExecutionListeners(
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
        listeners = { CassandraUnitDependencyInjectionTestExecutionListener.class })
@CassandraDataSet(keyspace = "showcase", value = "setup.cql")
@EmbeddedCassandra(timeout = 60000)
@EnableCassandraRepositories
@SpringJUnitConfig
public class LoginEventRepositoryTest {

    @Autowired
    LoginEventRepository loginEventRepository;

    @BeforeAll
    static void beforeAll() {
        System.setProperty("cassandra.native_transport_port", "9042");
    }

    @Test
    public void testFindByPersonIdAndEventTime() {
        Optional<LoginEvent> event1 = loginEventRepository
                .findByPersonIdAndEventTime("abc", Utils.toDate("20180512185300"));
        assertDoesNotThrow(event1::get);

        Optional<LoginEvent> event2 = loginEventRepository
                .findByPersonIdAndEventTime("xxx", Utils.toDate("20180512185300"));
        assertThrows(NoSuchElementException.class, event2::get);
    }

    @Import(CassandraConfig.class)
    @Configuration
    static class TestConfig {
    }

}
