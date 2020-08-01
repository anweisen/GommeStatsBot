package net.anweisen.gommestats.manager.stats;

import net.anweisen.gommestats.manager.ConnectionManager;
import net.anweisen.gommestats.manager.stats.PlayerStats;
import net.anweisen.gommestats.utils.entities.StatsHidedException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author anweisen
 * GommeBot developed on 07-30-2020
 * https://github.com/anweisen
 */

public class StatsWrapper {

	public static PlayerStats getsStatsByNameWithException(String playerName, GameMode gamemode) throws IOException, StatsHidedException {

		String request = PlayerStats.getURL(playerName, gamemode);
		Document document = ConnectionManager.openConnection(request);

		Element element = document.getElementById(gamemode.getHTMLName());
		if (element == null) throw new StatsHidedException();

		Elements elements = element.getElementsByClass("score");
		if (elements == null || elements.isEmpty()) throw new StatsHidedException();

		return gamemode.instance(elements);

	}

}
