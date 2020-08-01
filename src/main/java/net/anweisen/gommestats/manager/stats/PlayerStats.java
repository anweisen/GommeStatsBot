package net.anweisen.gommestats.manager.stats;

import java.util.Set;
import java.util.TreeMap;

/**
 * @author anweisen
 * GommeBot developed on 07-30-2020
 * https://github.com/anweisen
 */
public class PlayerStats {

	public static String getURL(String playerName, GameMode mode) {
		return "https://www.gommehd.net/player/index?playerName=" + playerName + (mode != null ? "#" + mode.getHTMLName() : "");
	}

	private final GameMode mode;
	private final TreeMap<StatsAttribute, Double> stats = new TreeMap<>();

	PlayerStats(GameMode mode) {
		this.mode = mode;
	}

	public void setInt(StatsAttribute attribute, int value) {
		setDouble(attribute, value);
	}

	public void setDouble(StatsAttribute attribute, double value) {
		stats.put(attribute, value);
	}

	public double getDouble(StatsAttribute attribute) {
		return stats.getOrDefault(attribute, 0D);
	}

	public int getInt(StatsAttribute attribute) {
		return (int) getDouble(attribute);
	}

	public GameMode getGameMode() {
		return mode;
	}

	public Set<StatsAttribute> getDeclaredAttribute() {
		return stats.keySet();
	}

	public String getString(StatsAttribute attribute) {
		return attribute.value(getDouble(attribute));
	}
}
