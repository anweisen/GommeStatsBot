package net.anweisen.gommestats.utils;

import net.anweisen.gommestats.utils.commons.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;

/**
 * @author anweisen
 * GommeBot developed on 07-30-2020
 * https://github.com/anweisen
 */

public class SecretsManager {

	private String token;

	public SecretsManager(File file) {

		try {

			if (!file.exists()) {
				file.createNewFile();
				throw new FileNotFoundException();
			}

			Properties properties = Utils.readProperties(file);
			token = properties.getProperty("TOKEN");
		} catch (Exception ex) {
			Log.severe("Could not load settings :: " + ex.getMessage());
			System.exit(4);
		}

	}

	public String getToken() {
		return token;
	}
}
