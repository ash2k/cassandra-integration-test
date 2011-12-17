package com.ash2k.example.cassandra_it.it;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.factory.HFactory;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ash2k.example.cassandra_it.MapService;

/**
 * This is an integration test for {@link MapService} class.
 * 
 * It's far from good coverage but it's just a demo ;)
 * 
 * @author Mikhail Mazursky
 */
public class MapServiceIT {

	static final String COLUMN_FAMILY = "maps";
	ColumnFamilyTemplate<String, String> template;
	MapService mapService;

	/**
	 * Creates required column family before tests in this class run.
	 */
	@BeforeClass
	public void beforeClass() {
		Cluster cluster = HFactory.getOrCreateCluster(CassandraIT.CLUSTER,
				CassandraIT.HOST);

		ColumnFamilyDefinition cfDef = HFactory.createColumnFamilyDefinition(
				CassandraIT.KEYSPACE, COLUMN_FAMILY, ComparatorType.UTF8TYPE);
		cluster.addColumnFamily(cfDef, true);

		Keyspace keyspace = HFactory.createKeyspace(CassandraIT.KEYSPACE,
				cluster);

		template = new ThriftColumnFamilyTemplate<String, String>(keyspace,
				COLUMN_FAMILY, StringSerializer.get(), StringSerializer.get());
	}

	@BeforeMethod
	public void beforeMethod() {
		mapService = new MapService(template);
	}

	@Test
	public void loadShouldLoadPersistedMap() {
		// arrange
		String key = "the_key";
		Map<String, String> map = new HashMap<String, String>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		mapService.persist(key, map);

		// act
		Map<String, String> loaded = mapService.load(key);

		// assert
		assertEquals(loaded, map);
	}
}
