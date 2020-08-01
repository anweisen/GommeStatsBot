package net.anweisen.gommestats.manager.clans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 * @author anweisen
 * GommeBot developed on 07-31-2020
 * https://github.com/anweisen
 */

public class Clan {

	public static String getURL(String clanName) {
		return "https://www.gommehd.net/clan-profile/members/" + clanName;
	}

	private final TreeMap<ClanRank, List<String>> members = new TreeMap<>();
	private final String name, tag, avatar;
	private final int size;

	public Clan(String name, String tag, String avatar, int size) {
		this.name = name;
		this.tag = tag;
		this.avatar = avatar;
		this.size = size;
	}

	void addMember(ClanRank rank, String player) {
		List<String> players = members.getOrDefault(rank, new ArrayList<>());
		players.add(player);
		Collections.sort(players);
		members.put(rank, players);
	}

	public List<String> getMember(ClanRank rank) {
		return members.getOrDefault(rank, new ArrayList<>());
	}

	public int size() {
		int size = 0;
		for (ClanRank currentRank : ClanRank.values()) {
			size += getMember(currentRank).size();
		}
		return size;
	}

	public int getSize() {
		return size;
	}

	public String getName() {
		return name;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getTag() {
		return tag;
	}

	@Override
	public String toString() {
		return "Clan{" +
				"Name=" + name + ", " +
				"Tag=" + tag + ", " +
				"Avatar=" + avatar + ", " +
				"Leader=" + getMember(ClanRank.LEADER) + ", " +
				"Moderator=" + getMember(ClanRank.MODERATOR) + ", " +
				"Member=" + getMember(ClanRank.MEMBER) +
				"}";
	}
}
