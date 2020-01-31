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
public abstract class ValidationHelper {
	
	private static Predicate<Collection<?>> predicateCollection;
	private static Predicate<Object> predicateIsNull;
	
	public static boolean collectionEmpty(Collection<?> entities) {
		if (predicateCollection == null) {
			criaPredicateCollectionVazia();
		}
		return predicateCollection.test(entities);
	}
	
	private static void criaPredicateCollectionVazia() {
		predicateCollection = collection -> collection == null || collection.isEmpty();
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

	private static Predicate<Boolean> criaPredicateProcessaErros() {
		Predicate<Boolean> predicate = p -> !p;
		return predicate;
	}
}

