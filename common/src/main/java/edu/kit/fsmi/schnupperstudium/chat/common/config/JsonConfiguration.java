package edu.kit.fsmi.schnupperstudium.chat.common.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

/**
 * Provides a persistent configuration.
 *
 * @author Daniel Wieland
 * @since Apr 29, 2015
 * @version 1.0.0
 *
 */
public final class JsonConfiguration extends MemoryConfiguration {
	/** Logger. */
	private static final Logger LOG = LogManager.getLogger();

	private static File DEFAULT_DIR;

	/** file to save to. */
	private File file;

	static {
		if (System.getProperty("os.name").toLowerCase().contains("win")) {
			DEFAULT_DIR = new File(System.getenv("APPDATA") + "/kamaro/config");
		} else {
			DEFAULT_DIR = new File(System.getProperty("user.home") + "/.kamaro/config");
		}
		if (!DEFAULT_DIR.exists() && !DEFAULT_DIR.mkdirs()) {
			LOG.error("Cannot create folder: " + DEFAULT_DIR);
		}
	}

	/**
	 * Creates a new configuration and sets the save destination.
	 *
	 * @param file save destination
	 */
	public JsonConfiguration(final File file) {
		this.file = file;
	}

	/**
	 * Creates a new configuration and sets the save destination and defaults.
	 *
	 * @param file save destination
	 * @param defaults default configuration
	 */
	public JsonConfiguration(final File file, final Configuration defaults) {
		super(defaults);

		this.file = file;
	}

	/**
	 * Returns a configured configuration using the given name. The default path will be used and it
	 * will attempt to load a default configuration from <code>src/main/resources/</code>
	 *
	 * @param name configuration name.
	 * @return instance of the given configuration.
	 */
	public static JsonConfiguration getConfiguration(final String name) {
		String fileName;
		if (name.lastIndexOf(".") <= 0) {
			fileName = name + ".conf";
		} else {
			fileName = name;
		}

		File file = new File(DEFAULT_DIR, fileName);
		InputStream input =
			JsonConfiguration.class.getResourceAsStream("/configs/" + fileName);

		JsonConfiguration configuration = loadFromFile(file);
		JsonConfiguration defaults = loadFromStream(input);

		configuration.setDefaults(defaults);

		return configuration;
	}

	/**
	 * Loads a configuration from a file. If the file does not exist an empty configuration is
	 * returned.
	 *
	 * @param file file to load from
	 * @return loaded configuration
	 */
	public static JsonConfiguration loadFromFile(final File file) {
		try {
			return loadFromStream(file, new FileInputStream(file));
		} catch (FileNotFoundException e) {
			return new JsonConfiguration(file);
		}
	}

	/**
	 * Loads a configuration from a file. If the file does not exist an empty configuration is
	 * returned.
	 *
	 * @param input input stream
	 * @return loaded configuration
	 */
	public static JsonConfiguration loadFromStream(final InputStream input) {
		return loadFromStream(null, input);
	}

	private static JsonConfiguration loadFromStream(final File file, final InputStream input) {
		if (input == null) {
			return new JsonConfiguration(file);
		}

		InputStreamReader reader = new InputStreamReader(input);
		JsonParser parser = new JsonParser();
		JsonElement json = parser.parse(reader);
		JsonConfiguration config = new JsonConfiguration(file);
		if (!json.isJsonObject()) {
			return config;
		}

		Gson gson = new Gson();
		json.getAsJsonObject().entrySet().forEach(entry -> {
			String key = entry.getKey();
			JsonElement value = entry.getValue();
			if (value == null) {
				return;
			} else if (value.isJsonPrimitive()) {
				JsonPrimitive primitive = value.getAsJsonPrimitive();
				if (primitive.isString()) {
					config.set(key, primitive.getAsString());
				} else if (primitive.isBoolean()) {
					config.set(key, primitive.getAsBoolean());
				} else if (primitive.isNumber()) {
					if (primitive.getAsDouble() - primitive.getAsInt() < 1E-6) {
						config.set(key, primitive.getAsInt());
					} else {
						config.set(key, primitive.getAsDouble());
					}
				} else {
					LOG.warn("Unkown prmitive with " + key);
				}
			} else {
				String className = value.getAsJsonObject().get("__class").getAsString();
				if (className == null || className.isEmpty()) {
					LOG.warn("Missing class entry for key: " + key);
					return;
				}

				try {
					Class<?> clazz = Class.forName(className);
					Object o = gson.fromJson(value, clazz);
					config.set(key, o);
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}
			}
		});

		try {
			reader.close();
		} catch (IOException ignore) {
			// don't care
		}

		return config;
	}

	/**
	 * Stores the configuration.
	 *
	 * @throws IOException if IO errors occur.
	 */
	public void saveConfiguration() throws IOException {
		if (file == null) {
			return;
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonObject jsonObject = new JsonObject();
		map.entrySet().forEach(
			entry -> {
				JsonElement element = gson.toJsonTree(entry.getValue());
				if (element.isJsonObject()) {
					((JsonObject) element).addProperty("__class", entry.getValue().getClass()
						.getName());
				}

				jsonObject.add(entry.getKey(), element);
			});

		String json = gson.toJson(jsonObject);

		FileWriter writer = new FileWriter(file, false);
		writer.write(json);
		writer.flush();
		writer.close();
	}

	/**
	 * @return file this configuration is saved to.
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Sets the file this configuration is saved to.
	 *
	 * @param file file to save to
	 */
	public void setFile(final File file) {
		this.file = file;
	}
}
