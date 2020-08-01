package net.anweisen.gommestats.utils;

import net.anweisen.gommestats.commandmanager.CommandEvent;
import net.anweisen.gommestats.utils.commons.Log;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

/**
 * @author anweisen
 * GommeBot developed on 07-30-2020
 * https://github.com/anweisen
 */

public class Utils {

	public static JSONObject fetch(String playerName) {
		try {
			return fetchWithException(playerName);
		} catch (IOException | ParseException ex) {
			Log.severe("Could not fetch player information :: " + ex.getMessage());
			return null;
		}
	}

	public static JSONObject fetchWithException(String playerName) throws IOException, ParseException {
		String url = "https://api.mojang.com/users/profiles/minecraft/" + playerName;
		String UUIDJson = IOUtils.toString(openConnection(url).getInputStream());
		if (UUIDJson.isEmpty()) return null;
		return (JSONObject) JSONValue.parseWithException(UUIDJson);
	}

	public static Properties readProperties(File file) throws IOException {
		return readProperties(file.toURI().toURL());
	}

	public static Properties readProperties(URL url) throws IOException {
		Properties properties = new Properties();
		InputStream input = url.openConnection().getInputStream();
		properties.load(new InputStreamReader(input, StandardCharsets.UTF_8));
		return properties;
	}

	public static void copyProperties(Properties source, Properties destination, File destinationFile) throws IOException {
		for (String currentKey : source.stringPropertyNames()) {
			String currentValue = source.getProperty(currentKey);
			destination.setProperty(currentKey, currentValue);
		}
		saveProperties(destination, destinationFile);
	}

	public static void saveProperties(Properties properties, File file) throws IOException {
		file.createNewFile();
		Writer writer = new PrintWriter(new FileOutputStream(file));
		properties.store(writer, null);
		writer.flush();
		writer.close();
	}

	public static void saveJSON(File file, JSONObject jsonObject) throws IOException {
		Writer writer = new PrintWriter(file);
		jsonObject.writeJSONString(writer);
		writer.flush();
		writer.close();
	}

	public static JSONObject getJSONObject(File file) throws IOException, ParseException {
		return (JSONObject) JSONValue.parseWithException(new FileReader(file));
	}

	public static JSONObject getJSONObject(String string) throws IOException, ParseException {
		return (JSONObject) JSONValue.parseWithException(new StringReader(string));
	}

	public static BufferedImage getImage(String request) {
		try {
			HttpURLConnection connection = openConnection(request);
			return ImageIO.read(connection.getInputStream());
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static HttpURLConnection openConnection(String request) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(request).openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		return connection;
	}

	public static BufferedImage getImage(File file) {
		try {
			URLConnection connection = file.toURI().toURL().openConnection();
			InputStream input = connection.getInputStream();
			return ImageIO.read(input);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static BufferedImage getLocalImage(String path) {
		try {
			InputStream input = ClassLoader.getSystemResourceAsStream(path);
			return ImageIO.read(input);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static String getHeadURLByUUID(String uuid) {
		if (uuid == null || uuid.equals("error")) uuid = "c06f89064c8a49119c29ea1dbd1aab82"; // uuid of MHF_Steve
		return "https://crafatar.com/avatars/" + uuid + "?size=128&default=MHF_Steve&overlay";
	}

	public static BufferedImage getHeadByUUID(String uuid) {
		return getImage(getHeadURLByUUID(uuid));
	}

	@SafeVarargs
	public static <T> T[] a(T... strings) {
		return strings;
	}

	@SafeVarargs
	public static <T> T[][] b(T[]... arrays) {
		return arrays;
	}

	@SafeVarargs
	public static <T> boolean listContains(List<T> list, T... container) {
		for (T current : container) {
			if (!list.contains(current)) return false;
		}
		return true;
	}

	public static String format(double value) {
		return "";
	}

	public static boolean containsMention(String text) {

		char[] goal = {'<','@','!','n','n','n','n','n','n','n','n','n','n','n','n','n','n','n','n','n','n','>'};
		int current = 0;
		for (char currentChar : text.toCharArray()) {

			boolean isInMention = false;
			char expected = goal[current];

			if (currentChar == expected) {
				isInMention = true;
			} else if (expected == 'n') {
				try {
					Integer.parseInt(String.valueOf(current));
					isInMention = true;
				} catch (NumberFormatException ignored) { }
			}

			if (isInMention) {
				current++;
			} else {
				current = 0;
			}

			if (current == goal.length) return true;

		}

		return false;

	}

	public static String syntax(CommandEvent event, String syntax) {
		return syntax(event, syntax, true);
	}

	public static String syntax(CommandEvent event, String syntax, boolean command) {
		String message = event.getPrefix() + (command ? event.getCommandName() + " " : "") + syntax;
		boolean mark = !containsMention(message);
		return (mark ? "`" : "*") + message + (mark ? "`" : "*");
	}

}
