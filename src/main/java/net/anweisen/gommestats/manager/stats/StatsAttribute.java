package net.anweisen.gommestats.manager.stats;

import java.text.DecimalFormat;

import static net.anweisen.gommestats.manager.stats.StatsAttribute.AttributeValueFormatter.*;

/**
 * @author anweisen
 * GommeBot developed on 07-30-2020
 * https://github.com/anweisen
 */

public enum StatsAttribute {

	GAMES("<:games:738457235201458208>", "Games", MIDDLE_NUMBER),
	WINS("<:wins:738457236057358419>", "Wins", MIDDLE_NUMBER),
	WINRATE("<:winrate:738777160625684501>", "Winrate", PERCENTAGE),
	KILLS("<:kills:738457235939786832>", "Kills", MIDDLE_NUMBER),
	DEATHS("<:deaths:738457235084017675>", "Deaths", MIDDLE_NUMBER),
	KD("<:sword:738638161676337232>", "K/D", DEFAULT),
	POINTS("<:points:738457236132593764>", "Points", BIG_NUMBER),
	KARMA(POINTS.emoji, "Karma", BIG_NUMBER),
	BEDS("<:beds:738457233687576677>", "Beds", MIDDLE_NUMBER),
	COOKIES("<:cookies:738457234782158961>", "Cookies", MIDDLE_NUMBER);

	private final String emoji, name;
	private final AttributeValueFormatter formatter;

	StatsAttribute(String emoji, String name, AttributeValueFormatter formatter) {
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

	public interface AttributeValueFormatter {

		String format(double value);

		AttributeValueFormatter DEFAULT = fromPattern("0.##", "");
		AttributeValueFormatter	PERCENTAGE = fromPattern("0.##", "%");
		AttributeValueFormatter MIDDLE_NUMBER = fromPattern("###,###,###,###,###,###,###,###,###,###,###,##0.##", "");
		AttributeValueFormatter BIG_NUMBER = value -> {

			DecimalFormat format = new DecimalFormat("0.###");
			double divide;
			String ending = "";

			if (value < 1000) {
				divide = 1;
			} else if (value < 1000000) {
				divide = 1000;
				ending = "k";
			} else {
				divide = 1000000;
				ending = "m";
			}

			value /= divide;
			return format.format(value) + ending;

		};

		static AttributeValueFormatter fromPattern(String pattern, String ending) {
			return value -> new DecimalFormat(pattern).format(value) + ending;
		}

	}

}
