package net.cartola.emissorfiscal.util;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @date 8 de out. de 2021
 * @author robson.costa
 */
public final class PredicateUtil {

	
	
	/**
	 * Predicate usado, para Filtrar os elementos de uma lista, por uma PROPRIEDADE ESPECIFICA;
	 * 
	 * TODO: Verificar se isso pode dar b.o, caso seja usado em vários lugares diferentes
	 * 
	 * @param <T>
	 * @param KeyExtractor
	 * @return
	 */
	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> KeyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(KeyExtractor.apply(t));
	}
	
	
	
}
