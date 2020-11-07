package net.anweisen.gommestats.manager.stats;

import net.codingarea.engine.utils.NumberConversions;
import org.json.simple.JSONObject;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.Map.Entry;
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

	public void set(StatsAttribute attribute, double value) {
		stats.put(attribute, value);
	}

	public double getDouble(StatsAttribute attribute) {
		return stats.getOrDefault(attribute, 0D);
	}

	public GameMode getGameMode() {
		return mode;
	}

	public Set<StatsAttribute> getDeclaredAttributes() {
		return stats.keySet();
	}

	public String getString(StatsAttribute attribute) {
		return attribute.value(getDouble(attribute));
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		for (Entry<StatsAttribute, Double> entry : stats.entrySet()) {
			json.put(entry.getKey().name(), entry.getValue());
		}
		return json;
	}

	public static PlayerStats ofJSON(@Nonnull GameMode mode, @Nonnull JSONObject json) {
		PlayerStats stats = new PlayerStats(mode);
		for (StatsAttribute attribute : mode.getValues()) {
			double value = NumberConversions.toDouble(json.get(attribute.name()));
			stats.set(attribute, value);
		}
		return stats;
	}

	@Override
	public String toString() {
		return "PlayerStats{" +
				"game=" + mode + ", " +
				"stats=" + toJSON() +
				'}';
	}
}
