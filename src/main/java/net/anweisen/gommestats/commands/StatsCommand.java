package net.anweisen.gommestats.commands;

import net.anweisen.gommestats.commandmanager.CommandEvent;
import net.anweisen.gommestats.commandmanager.commands.Command;
import net.anweisen.gommestats.manager.stats.GameMode;
import net.anweisen.gommestats.manager.stats.PlayerStats;
import net.anweisen.gommestats.manager.stats.StatsAttribute;
import net.anweisen.gommestats.manager.stats.StatsWrapper;
import net.anweisen.gommestats.utils.entities.Embeds;
import net.anweisen.gommestats.utils.Utils;
import net.anweisen.gommestats.utils.entities.StatsHidedException;
import net.dv8tion.jda.api.EmbedBuilder;
import org.json.simple.JSONObject;

import java.text.DecimalFormat;

import static net.anweisen.gommestats.utils.Utils.syntax;

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
	public void onCommand(CommandEvent event) {

		if (event.getArgs().length != 1 && event.getArgs().length != 2) {
			event.queueReply("Benutze " + syntax(event, "<gamemode> [player]"));
			return;
		}

		GameMode gamemode = GameMode.byName(event.getArg(0));
		if (gamemode == null) {
			event.queueReply("Den Spielmodus `" + event.getArg(0) + "` gibt es nicht. Mit " + syntax(event, "gamemodes") + " siehst du alle.");
			return;
		}

		String player = event.getMemberName();
		if (event.getArgs().length == 2) {
			player = event.getArg(1);
		}

		event.getChannel().sendTyping().queue();

		try {

			PlayerStats stats = StatsWrapper.getsStatsByNameWithException(player, gamemode);
			EmbedBuilder embed = Embeds.builder();
			StringBuilder builder = new StringBuilder();
			String headURL = null;

			try {

				JSONObject jsonObject = Utils.fetchWithException(player);
				player = (String) jsonObject.get("name");
				String uuid = (String) jsonObject.get("id");

				headURL = Utils.getHeadURLByUUID(uuid);

			} catch (Exception ignored) { }

			for (StatsAttribute currentAttribute : stats.getDeclaredAttribute()) {
				builder.append(currentAttribute.getEmoji() + " | **" + currentAttribute.getName() + "** » " + stats.getString(currentAttribute) + "\n");
			}

			embed.setAuthor((headURL != null ? "» " : "") + gamemode.getName() + " Stats von " + player, PlayerStats.getURL(player, gamemode), headURL);
			embed.setDescription(builder.toString());

			event.queueReply(embed.build());

		} catch (StatsHidedException ignored) {
			event.queueReply("Der Spieler hat seine Stats versteckt");
		} catch (Exception ex) {
			event.queueReply("Etwas ist schief gelaufen: `" + ex.getMessage() + "`");
		}

	}
}
