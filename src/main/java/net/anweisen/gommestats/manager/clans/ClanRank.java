package net.anweisen.gommestats.manager.clans;

import org.jetbrains.annotations.NotNull;

/**
 * @author anweisen
 * GommeBot developed on 08-01-2020
 * https://github.com/anweisen
 */

public enum ClanRank {

	LEADER("Leader"),
	MODERATOR("Moderator"),
	MEMBER("Member");

	private final String name;

	ClanRank(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getName();
	}

	@NotNull
	public static ClanRank byOrdinal(int ordinal) {
		for (ClanRank current : values()) {
			if (current.ordinal() == ordinal) return current;
		}
		throw new IndexOutOfBoundsException();
	}

}
