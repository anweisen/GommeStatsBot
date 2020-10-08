package net.anweisen.gommestats.utils.entities;

/**
 * @author anweisen
 * GommeBot developed on 07-30-2020
 * https://github.com/anweisen
 */

public class StatsHiddenException extends Exception {

	public StatsHiddenException() {
		this("Stats are hidden");
	}

	public StatsHiddenException(String message) {
		super(message);
	}

}
