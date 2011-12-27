package com.ash2k.example.cassandra_it.it;

import java.util.Collections;
import java.util.List;

import me.prettyprint.cassandra.service.ThriftKsDef;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;

/**
 * This test creates keyspace before other integration tests run.
 * 
 * @author Mikhail Mazursky
 */
public class CassandraIT {

	public static final String CLUSTER = "Test Cluster";
	public static final String KEYSPACE = "Keyspace_test";
	public static final String HOST = System
			.getProperty("cassandra.listenAddress")
			+ ':'
			+ System.getProperty("cassandra.rpcPort");

	private static final Logger log = LoggerFactory
			.getLogger(CassandraIT.class);

	@BeforeTest
	public void beforeTest() throws Exception {
		log.info("Creating keyspace \"{}\" in cluster \"{}\"", KEYSPACE,
				CLUSTER);

		Cluster cluster = HFactory.getOrCreateCluster(CLUSTER, HOST);
		List<ColumnFamilyDefinition> cfDefs = Collections.emptyList();
		KeyspaceDefinition newKeyspace = HFactory.createKeyspaceDefinition(
				KEYSPACE, ThriftKsDef.DEF_STRATEGY_CLASS, 1, cfDefs);

		cluster.addKeyspace(newKeyspace, true);
		log.info("Keyspace \"{}\" created", KEYSPACE);
	}
}
