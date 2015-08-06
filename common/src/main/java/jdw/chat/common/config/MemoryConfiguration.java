package jdw.chat.common.config;

import java.util.HashMap;

/**
 * A simple configuration using the RAM to store the data.
 * 
 * @author Daniel Wieland
 * @since Apr 29, 2015
 * @version 1.0.0
 *
 */
public class MemoryConfiguration implements Configuration {
	/** map used to store the key value pairs. */
	protected HashMap<String, Object> map;
	/** default values. */
	protected Configuration defaults;
	
	/**
	 * Creates a new configuration with no values and no defaults set.
	 */
	public MemoryConfiguration() {
		this(null);
	}
	
	/**
	 * Creates a new configuration using the provided defaults.
	 * 
	 * @param defaults defaults.
	 */
	public MemoryConfiguration(final Configuration defaults) {
		this.map = new HashMap<>();
		this.defaults = defaults;
	}
	
	@Override
	public final Object get(final String key) {
		if (map.containsKey(key)) {
			return map.get(key);
		} else if (defaults != null) {
			return defaults.get(key);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public final <T> T get(final Class<T> clazz, final String key) {
		if (key == null) {
			return null;
		}
		
		try {			
			return (T) get(key);
		} catch (ClassCastException e) {
			return null;
		}
	}

	@Override
	public final void set(final String key, final Object value) {
		if (key == null) {
			return;
		}
		
		if (value == null) {
			remove(key);
		} else {
			map.put(key, value);
		}
	}

	@Override
	public final void remove(final String key) {
		map.remove(key);
	}

	@Override
	public final void clear() {
		map.clear();
	}

	@Override
	public final Configuration getDefaults() {
		return defaults;
	}

	@Override
	public final void setDefaults(final Configuration defaults) {
		this.defaults = defaults;
	}
}
