package net.anweisen.gommestats.utils.entities;

/**
 * @author anweisen
 * GommeBot developed on 07-30-2020
 * https://github.com/anweisen
 */

public class StatsHidedException extends Exception {

	public StatsHidedException() {
		this("Stats are hidden");
	}

	public StatsHidedException(String message) {
		super(message);
	}

}
