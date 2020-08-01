package net.anweisen.gommestats.manager.stats.image;

import java.awt.image.BufferedImage;

import static net.anweisen.gommestats.utils.Utils.getLocalImage;

/**
 * @author anweisen
 * GommeBot developed on 07-30-2020
 * https://github.com/anweisen
 */

public abstract class Icon {

	private static final String folder = "/icons";

	public static final BufferedImage
			BEDS = getLocalImage(folder + "/beds.png"),
			COOKIES = getLocalImage(folder + "/cookies.png"),
			DEATHS = getLocalImage(folder + "/deaths.png"),
			GAMES = getLocalImage(folder + "/games.png"),
			KILLS = getLocalImage(folder + "/kills.png"),
			POINTS = getLocalImage(folder + "/points.png"),
			WINS = getLocalImage(folder + "/wins.png");

}
