package net.cartola.emissorfiscal;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import net.cartola.emissorfiscal.util.ValidationHelper;

/**
 * @date 26 de jul. de 2021
 * @author robson.costa
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ValidationHelperTest {
	
	private List<String> listString = null;
	
	
	@Test
	public void test01_collectionEVaziaOuNula() {
		boolean listIsVaziaOrNull = ValidationHelper.collectionIsEmptyOrNull(listString);
		assertTrue(listIsVaziaOrNull);
		assertNull(listString);
	}
	
	@Test
	public void test02_collectionNaoDeveSerVaziaNemNula() {
		listString = Arrays.asList("Just a test");
		
		boolean isListNotEmptyOuNula = ValidationHelper.collectionNotEmptyOrNull(listString);
		assertTrue(isListNotEmptyOuNula);
		assertNotNull(listString);
	}
	
	@Test
	public void test03_mapaDeveSerNull() {
		Map<Integer, String> mapNull = null;
		
		boolean isMapNull = ValidationHelper.isMapNull(mapNull);
		assertTrue(isMapNull);
		assertNull(mapNull);
	}
	
	@Test
	public void test04_mapaDeveSerVazioOuNulo() {
		Map<Integer, String> mapVazio = null;

		boolean isMapVazio = ValidationHelper.isMapEmptyOrNull(mapVazio);
		assertTrue(isMapVazio);
		assertNull(mapVazio);
	}
	
	@Test
	public void test05_mapaNaoDeveSerVazioOuNulo() {
		Map<Integer, String> mapVazio = new HashMap<>();
		mapVazio.put(1, "Just a test bro");
		
		boolean isMapVazio = ValidationHelper.isMapNotEmptyOrNull(mapVazio);
		assertTrue(isMapVazio);
		assertNotNull(mapVazio);
	}
}
