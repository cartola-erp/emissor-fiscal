package net.cartola.emissorfiscal.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * 31 de jan de 2020
 * @author robson.costa
 */
public final class ValidationHelper {
	
	private ValidationHelper() {}
	
	private static final Predicate<Collection<?>> predicateCollection = criaPredicateCollectionVazia();;
	private static final Predicate<Object> predicateIsNull = criaPredicateIsNull();
	
	private static final Predicate<Map<?, ?>> predicateMapNull = criaPredicateMapNull();
	private static final Predicate<Map<?, ?>> predicateMapEmptyOrNull = criaPredicateMapVazio();
	
	public static boolean collectionIsEmptyOrNull(Collection<?> entities) {
		return predicateCollection.test(entities);
	}
	
	public static boolean collectionNotEmptyOrNull(Collection<?> entities) {
		return !collectionIsEmptyOrNull(entities);
	}
	
	public static boolean isMapNull(Map<?, ?> map) {
		return predicateMapNull.test(map);
	}
	
	public static boolean isMapEmptyOrNull(Map<?, ?> map) {
		return predicateMapEmptyOrNull.test(map);
	}

	public static boolean isMapNotEmptyOrNull(Map<?, ?> map) {
		return !predicateMapEmptyOrNull.test(map);
	}
	
	public static List<String> processaErros(Map<String, Boolean> map) {
		Predicate<Boolean> p = criaPredicateProcessaErros();
		List<String> erros = new LinkedList<>();
		map.forEach((mensagem, v) -> {
			if (p.test(v)) {
				erros.add(mensagem);
			}
		});
		return erros;
	}

	
	// ================= CRIANDO OS PREDICATES =========================
	private static Predicate<Boolean> criaPredicateProcessaErros() {
		Predicate<Boolean> predicate = p -> p;
		return predicate;
	}
	
	private static Predicate<Map<?, ?>> criaPredicateMapNull() {
		return  map -> map == null;
	}

	
	private static Predicate<Map<?, ?>> criaPredicateMapVazio() {
		return map -> criaPredicateMapNull().test(map) || map.isEmpty();
	}
	
	private static Predicate<Collection<?>> criaPredicateCollectionVazia() {
		return collection -> collection == null || collection.isEmpty();
	}
	
	private static Predicate<Object> criaPredicateIsNull() {
		return p -> p == null;
	}
	
}

