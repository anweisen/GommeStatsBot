package net.anweisen.gommestats.utils;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

/**
 * @author anweisen
 * GommeBot developed on 07-30-2020
 * https://github.com/anweisen
 */

public class Utils {

	public static JSONObject fetchWithException(String playerName) throws IOException, ParseException {
		String url = "https://api.mojang.com/users/profiles/minecraft/" + playerName;
		String UUIDJson = IOUtils.toString(openConnection(url).getInputStream());
		if (UUIDJson.isEmpty()) return null;
		return (JSONObject) JSONValue.parseWithException(UUIDJson);
	}

	public static Properties readProperties(URL url) throws IOException {
		Properties properties = new Properties();
		InputStream input = url.openConnection().getInputStream();
		properties.load(new InputStreamReader(input, StandardCharsets.UTF_8));
		return properties;
	}

	public static void saveProperties(Properties properties, File file) throws IOException {
		file.createNewFile();
		Writer writer = new PrintWriter(new FileOutputStream(file));
		properties.store(writer, null);
		writer.flush();
		writer.close();
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

}
