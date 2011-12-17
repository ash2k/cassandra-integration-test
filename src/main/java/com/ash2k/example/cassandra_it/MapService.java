package com.ash2k.example.cassandra_it;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;

/**
 * Service that can persist and load maps of strings.
 * 
 * @author Mikhail Mazursky
 */
public class MapService {
	private final ColumnFamilyTemplate<String, String> template;
	private final String columnFamily;
	private static final Serializer<String> ss = StringSerializer.get();

	public MapService(ColumnFamilyTemplate<String, String> template) {
		this.template = template;
		columnFamily = template.getColumnFamily();
	}

	public void persist(String key, Map<String, String> map) {
		Mutator<String> mutator = template.createMutator();
		for (Entry<String, String> item : map.entrySet()) {
			mutator.addInsertion(key, columnFamily, HFactory.createColumn(
					item.getKey(), item.getValue(), ss, ss));
		}
		mutator.execute();
	}

	public Map<String, String> load(String key) {
		ColumnFamilyResult<String, String> res = template.queryColumns(key);
		if (!res.hasResults()) {
			return Collections.emptyMap();
		}
		Map<String, String> map = new HashMap<String, String>();
		for (String name : res.getColumnNames()) {
			map.put(name, res.getString(name));
		}
		return map;
	}
}
