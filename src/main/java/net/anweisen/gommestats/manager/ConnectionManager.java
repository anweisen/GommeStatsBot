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

	public static Document openConnection(String url, int timeout) throws IOException {

		Connection connection = Jsoup.connect(url);
		connection.timeout(timeout);
		connection.followRedirects(false);

		return connection.execute().parse();

	}

}
