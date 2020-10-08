package net.anweisen.gommestats.manager.stats;

import net.codingarea.engine.utils.NumberFormatter;

import static net.codingarea.engine.utils.NumberFormatter.*;

/**
 * @author anweisen
 * GommeBot developed on 07-30-2020
 * https://github.com/anweisen
 */

public enum StatsAttribute {

	GAMES("<:games:751748294589284362>", "Games", MIDDLE_NUMBER),
	WINS("<:wins:751748294958514237>", "Wins", MIDDLE_NUMBER),
	WINRATE("<:winrate:751748301891567616>", "Winrate", PERCENTAGE),
	KILLS("<:kills:751748294811844699>", "Kills", MIDDLE_NUMBER),
	DEATHS("<:deaths:751748295088537600>", "Deaths", MIDDLE_NUMBER),
	KD("<:sword:751748300419367093>", "K/D", DEFAULT),
	POINTS("<:points:751748294824165386>", "Points", BIG_NUMBER),
	KARMA(POINTS.emoji, "Karma", BIG_NUMBER),
	BEDS("<:beds:751748293838635080>", "Beds", MIDDLE_NUMBER),
	COOKIES("<:cookies:751748296908734474>", "Cookies", MIDDLE_NUMBER);

	private final String emoji, name;
	private final NumberFormatter formatter;

	StatsAttribute(String emoji, String name, NumberFormatter formatter) {
		this.emoji = emoji;
		this.name = name;
		this.formatter = formatter;
	}

	public String getEmoji() {
		return emoji;
	}

	public String getName() {
		return name;
	}

	public String value(double value) {
		return formatter.format(value);
	}

}
