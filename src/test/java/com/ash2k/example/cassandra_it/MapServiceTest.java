package com.ash2k.example.cassandra_it;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertTrue;

import java.util.Map;

import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * This is a unit test for {@link MapService} class.
 * 
 * It's far from good coverage but it's just a demo ;)
 * 
 * @author Mikhail Mazursky
 */
public class MapServiceTest {

	static final String KEY = "key1";

	@Mock
	ColumnFamilyTemplate<String, String> template;
	@Mock
	ColumnFamilyResult<String, String> result;
	MapService mapService;

	@BeforeMethod
	public void beforeMethod() {
		MockitoAnnotations.initMocks(this);
		when(result.hasNext()).thenReturn(false);

		mapService = new MapService(template);
	}

	@Test
	public void loadShouldQueryByProvidedKey() {
		// arrange
		when(template.queryColumns(KEY)).thenReturn(result);

		// act
		mapService.load(KEY);

		// assert
		verify(template).queryColumns(KEY);
	}

	@Test
	public void loadShouldReturnEmptyMapWhenNoResults() {
		// arrange
		when(template.queryColumns(KEY)).thenReturn(result);

		// act
		Map<String, String> map = mapService.load(KEY);

		// assert
		assertTrue(map.isEmpty());
	}
}
