package jdw.chat.common.config;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

/**
 * This interfaces provides methods for uniform access to configurations. 
 * 
 * @author Daniel Wieland
 * @since Apr 29, 2015
 * @version 1.0.0
 *
 */
public interface Configuration {
	/**
	 * Returns the value associated with the given key.
	 * If the key is not found a lookup in the default 
	 * section will be made. If nothing is found or the 
	 * types mismatch null will be returned.
	 * 
	 * @param key key
	 * @return associated value or null.
	 */
	default String getString(final String key) {
		return get(String.class, key);
	}	

	/**
	 * Returns the value associated with the given key.
	 * If the key is not found a lookup in the default 
	 * section will be made. If nothing is found or the 
	 * types mismatch null will be returned.
	 * 
	 * @param key key
	 * @return associated value or null.
	 */
	default double getDouble(final String key) {
		return get(Double.class, key);
	}

	/**
	 * Returns the value associated with the given key.
	 * If the key is not found a lookup in the default 
	 * section will be made. If nothing is found or the 
	 * types mismatch null will be returned.
	 * 
	 * @param key key
	 * @return associated value or null.
	 */
	default float getFloat(final String key) {
		return get(Float.class, key);
	}

	/**
	 * Returns the value associated with the given key.
	 * If the key is not found a lookup in the default 
	 * section will be made. If nothing is found or the 
	 * types mismatch null will be returned.
	 * 
	 * @param key key
	 * @return associated value or null.
	 */
	default int getInteger(final String key) {
		return get(Integer.class, key);
	}

	/**
	 * Returns the value associated with the given key.
	 * If the key is not found a lookup in the default 
	 * section will be made. If nothing is found or the 
	 * types mismatch null will be returned.
	 * 
	 * @param key key
	 * @return associated value or null.
	 */
	default byte getByte(final String key) {
		return get(Byte.class, key);
	}

	/**
	 * Returns the value associated with the given key.
	 * If the key is not found a lookup in the default 
	 * section will be made. If nothing is found or the 
	 * types mismatch null will be returned.
	 * 
	 * @param key key
	 * @return associated value or null.
	 */
	default boolean getBoolean(final String key) {
		return get(Boolean.class, key);
	}
	
	/**
	 * Returns the value associated with the given key.
	 * If the key is not found a lookup in the default 
	 * section will be made. If nothing is found or the 
	 * types mismatch null will be returned.
	 * 
	 * @param key key
	 * @return associated value or null.
	 */
	Object get(String key);
	/**
	 * Returns the value associated with the given key.
	 * If the key is not found a lookup in the default 
	 * section will be made. If nothing is found or the 
	 * types mismatch null will be returned.
	 * 
	 * @param clazz type of the value
	 * @param key key
	 * @return associated value or null.
	 */
	<T> T get(Class<T> clazz, String key);
	/**
	 * Associates the given key with the provided value.
	 * If the provided value is null this will delete the key.
	 * 
	 * @param key key
	 * @param o value
	 */
	void set(String key, Object o);
	/**
	 * Removes the given key and the associated value.
	 * 
	 * @param key key to remove
	 */
	void remove(String key);
	/**
	 * Clears this section. 
	 * All key value pairs will be removed.
	 */
	void clear();
	
	/**
	 * Returns the default configuration.
	 * 
	 * @return default configuration or null
	 */
	Configuration getDefaults();
	/**
	 * Sets the default configuration.
	 * 
	 * @param section default configuration
	 */
	void setDefaults(Configuration section);
	
	default void loadConfigurationValues(final Object obj) {
		if (obj == null) {
			return;
		}
		
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();		
		for (Field field : fields) {
			if (!field.isAnnotationPresent(Config.class)) {
				continue;
			} else if (Modifier.isFinal(field.getModifiers())) {
				throw new UnsupportedOperationException("Cannot change final fields: "
						+ clazz.getName() + " @ " + field.getName());
			}
			
			field.setAccessible(true);

			Config config = field.getAnnotation(Config.class);
			String key = config.key();
			if (key == null || key.isEmpty()) {
				key = field.getName(); 
			}
			
			Class<?> fieldType = field.getType();
			
			Object value = get(fieldType, key);
			try {
				field.set(obj, value);
			} catch (IllegalArgumentException e) {
				// must never happen (it cannot happen)
				LogManager.getLogger().catching(Level.ERROR, e);
			} catch (IllegalAccessException e) {
				// must also never happen (if it does you are screwed)
				LogManager.getLogger().catching(Level.ERROR, e);
			}
		}
	}
}
