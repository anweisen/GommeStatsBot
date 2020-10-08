package net.anweisen.gommestats.manager.stats;

import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.List;

import static net.anweisen.gommestats.manager.stats.StatsAttribute.*;
import static net.anweisen.gommestats.utils.Utils.*;

/**
 * @author anweisen
 * GommeBot developed on 07-30-2020
 * https://github.com/anweisen
 */

public enum GameMode {

	TTT("TTT", a(), WINS, KILLS, KARMA, DEATHS),
	SURVIVALGAMES("SurvivalGames", a("sg"), WINS, KILLS, DEATHS, POINTS),
	ENDERGAMES("EnderGames", a("eg"), WINS, KILLS, DEATHS),
	BEDWARS("BedWars", a("bw"), WINS, KILLS, GAMES, BEDS, DEATHS),
	SKYWARS("SkyWars", a("sw"), WINS, KILLS, DEATHS),
	QUICKSURVIVALGAMES("QuickSurvivalGames", a("qsg"), WINS, KILLS, DEATHS, POINTS),
	CORES("Cores", a(), WINS, KILLS, DEATHS),
	GUNGAME("GunGame", a("gg"), KILLS),
	SPEEDUHC("SpeedUHC", a("uhc", "suhc", "speed"), WINS, KILLS, DEATHS, POINTS),
	MASTERBUILDERS("MasterBuilders", a("mb", "builders", "buildbattle"), WINS, GAMES, POINTS),
	COOKIES("Cookies", a(), WINS, StatsAttribute.COOKIES),
	HARDCORE("Hardcore", a("ffa"), KILLS, DEATHS),
	;

	public static GameMode byName(String name) {
		for (GameMode currentGameMode : GameMode.values()) {
			if (currentGameMode.name().equalsIgnoreCase(name)) return currentGameMode;
			if (Arrays.asList(currentGameMode.alias).contains(name.toLowerCase())) return currentGameMode;
		}
		return null;
	}

	private final StatsAttribute[] values;
	private final String[] alias;
	private final String name;

	GameMode(String name, String[] alias, StatsAttribute... values) {
		this.name = name;
		this.values = values;
		this.alias = alias;
	}

	public List<StatsAttribute> getValues() {
		return Arrays.asList(values);
	}

	public String getName() {
		return name;
	}

	public PlayerStats instance(Elements elements) {

		PlayerStats stats = new PlayerStats(this);

		for (int i = 0; i < values.length; i++) {
			try {

				int value = Integer.parseInt(elements.get(i).text());
				StatsAttribute attribute = values[i];

				stats.setInt(attribute, value);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		calculateKD(stats);
		calculateWinRate(stats);

		return stats;

	}

	private boolean showKD() {
		return listContains(getValues(), KILLS, DEATHS);
	}


	public String getHTMLName() {
		return this.name().toLowerCase();
	}

	private static void calculateKD(PlayerStats stats) {
		if (stats.getGameMode().showKD()) {

			double kills = stats.getInt(KILLS);
			double deaths = stats.getInt(DEATHS);

			if (deaths <= 0) {
				deaths = 1;
			}

			double kd = kills / deaths;
			stats.setDouble(KD, kd);

		}
	}

	private static void calculateWinRate(PlayerStats stats) {
		List<StatsAttribute> values = stats.getGameMode().getValues();
		if (listContains(values, GAMES, WINS)) {

			double gamesPlayed = stats.getInt(GAMES);
			double gamesWon = stats.getInt(WINS);

			double winRate = (gamesPlayed <= 0 ? 0 : (gamesWon / gamesPlayed) * 100);

			stats.setDouble(WINRATE, winRate);

		}
	}

}
