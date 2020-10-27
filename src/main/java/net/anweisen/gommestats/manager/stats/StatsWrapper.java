package net.anweisen.gommestats.manager.stats;

import net.anweisen.gommestats.manager.ConnectionManager;
import net.anweisen.gommestats.utils.entities.StatsHiddenException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * @author anweisen
 * GommeBot developed on 07-30-2020
 * https://github.com/anweisen
 */

public class StatsWrapper {

	public static PlayerStats getsStatsByName(String playerName, GameMode gamemode) throws IOException, StatsHiddenException {

		Document document = getConnection(playerName, 0);

		Element gameModeElement = document.getElementById(gamemode.getHTMLName());
		if (gameModeElement == null)
			throw new StatsHiddenException();

		Elements scores = gameModeElement.getElementsByClass("score");
		if (scores == null || scores.isEmpty())
			throw new StatsHiddenException();

		return gamemode.instance(scores);

	}

	public static PlayerStats[] getsStatsByName(String playerName, GameMode... gamemodes) throws IOException, StatsHiddenException {

		Document document = getConnection(playerName, 0);

		PlayerStats[] stats = new PlayerStats[gamemodes.length];
		for (int i = 0; i < stats.length; i++) {

			GameMode gamemode = gamemodes[i];

			Element gameModeElement = document.getElementById(gamemode.getHTMLName());
			if (gameModeElement == null)
				continue;

			Elements scores = gameModeElement.getElementsByClass("score");
			if (scores == null || scores.isEmpty())
				continue;

			stats[i] = gamemode.instance(scores);

		}

		return stats;

	}

	public static Document getConnection(@Nonnull String playerName, int cycle) throws IOException {

		// Simple retry system
		// Retries on timeout up to 3 times
		try {
			String request = PlayerStats.getURL(playerName, null);
			return ConnectionManager.openConnection(request, 500);
		} catch (SocketTimeoutException ex) {
			if (cycle < 3) {
				return getConnection(playerName, ++cycle);
			} else {
				throw ex;
			}
		}

	}

}
