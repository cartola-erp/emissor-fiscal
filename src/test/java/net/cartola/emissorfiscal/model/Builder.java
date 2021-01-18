package net.cartola.emissorfiscal.model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *	5 de nov de 2019
 *	@author gregory.feijon
 */

public abstract class Builder<T> {
	
	private static final Logger LOG = LoggerFactory.getLogger(Builder.class);

	public abstract T build();
	
	protected T clone(T t) {
		T newT = null;
		Field lastField = null;
		Object lastValue = null;
		try {
			@SuppressWarnings("unchecked")
			Class<T> clazz = (Class<T>) t.getClass();
			newT = clazz.newInstance();
			Method[] methodsArray = clazz.getDeclaredMethods();
			
			List<Method> methods = Arrays.asList(methodsArray);
			List<Method> getters = methods.stream().filter(m->
				 (m.getName().toLowerCase().startsWith("get")
						 || m.getName().toLowerCase().startsWith("is"))).collect(Collectors.toList());
			List<Method> setters = methods.stream().filter(m->
			 (m.getName().toLowerCase().startsWith("set"))).collect(Collectors.toList());
			
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				lastField = field;
				int mod = field.getModifiers();
				if (!java.lang.reflect.Modifier.isStatic(mod)
						&& !java.lang.reflect.Modifier.isFinal(mod)) {
					Optional<Method> opGetter = etter(field.getName(), getters);
					Optional<Method> opSetter = etter(field.getName(), setters);
					if (opGetter.isPresent() && opSetter.isPresent()) {
						Method setter = opSetter.get();
						Method getter = opGetter.get();
						Object value = getter.invoke(t);
						lastValue = value;
						String setterName = setter.getName();
						String getterName = getter.getName();
						LOG.debug("{}:{}:{}", setterName, getterName, value);
						if (value != null) {
							setter.invoke(newT, value);
						}
					}
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			LOG.error("{}:{}", lastField, lastValue);
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return newT;
	}

	private Optional<Method> etter(String name, List<Method> etters) {
		Optional<Method> first = etters.stream().filter(e -> e.getName().toLowerCase().endsWith(name.toLowerCase()))
				.findFirst();
		return first;
	}
	
}
