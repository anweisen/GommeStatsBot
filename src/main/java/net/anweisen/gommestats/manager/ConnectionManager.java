package net.anweisen.gommestats.manager;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author anweisen
 * GommeBot developed on 07-31-2020
 * https://github.com/anweisen
 */

public class ConnectionManager {

	public static Document openConnection(String url) throws IOException {

		Connection connection = Jsoup.connect(url);
		connection.timeout(7500);
		connection.followRedirects(false);

		return connection.get();

	}

}
