package net.anweisen.gommestats.commands;

import net.anweisen.gommestats.manager.stats.GameMode;
import net.anweisen.gommestats.manager.stats.PlayerStats;
import net.anweisen.gommestats.manager.stats.StatsAttribute;
import net.anweisen.gommestats.manager.stats.StatsWrapper;
import net.anweisen.gommestats.utils.entities.Embeds;
import net.anweisen.gommestats.utils.Utils;
import net.anweisen.gommestats.utils.entities.StatsHiddenException;
import net.codingarea.engine.discord.commandmanager.Command;
import net.codingarea.engine.discord.commandmanager.event.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import org.json.simple.JSONObject;

import javax.annotation.Nonnull;


/**
 * @author anweisen
 * GommeBot developed on 07-30-2020
 * https://github.com/anweisen
 */

public class StatsCommand extends Command {

	public StatsCommand() {
		super("stats", true, true, "s");
	}

	@Override
	public void onCommand(@Nonnull CommandEvent event) throws Exception {

		if (event.getArgs().length != 1 && event.getArgs().length != 2) {
			event.reply("Benutze " + syntax(event, "<gamemode> [player]"));
			return;
		}

		GameMode gamemode = GameMode.byName(event.getArg(0));
		if (gamemode == null) {
			event.reply("Den Spielmodus `" + removeMarkdown(event.getArg(0), true) + "` gibt es nicht. Mit " + CommandEvent.syntax(event, "gamemodes", false) + " siehst du alle.");
			return;
		}

		String player = event.getEffectiveUserName();
		if (event.getArgs().length == 2) {
			player = event.getArg(1);
		}

		event.getChannel().sendTyping().queue();

		try {

			PlayerStats stats = StatsWrapper.getsStatsByName(player, gamemode);
			EmbedBuilder embed = Embeds.builder();
			StringBuilder builder = new StringBuilder();
			String headURL = null;

			try {

				JSONObject jsonObject = Utils.fetchWithException(player);
				player = (String) jsonObject.get("name");
				String uuid = (String) jsonObject.get("id");

				headURL = Utils.getHeadURLByUUID(uuid);

			} catch (Exception ignored) { }

			for (StatsAttribute currentAttribute : stats.getDeclaredAttributes()) {
				builder.append(currentAttribute.getEmoji() + " | **" + currentAttribute.getName() + "** » " + stats.getString(currentAttribute) + "\n");
			}

			embed.setAuthor((headURL != null ? "» " : "") + gamemode.getName() + " Stats von " + player, PlayerStats.getURL(player, gamemode), headURL);
			embed.setDescription(builder.toString());

			event.reply(embed.build());

		} catch (StatsHiddenException ignored) {
			event.reply("Der Spieler hat seine Stats versteckt");
		}

	}
}
