package net.anweisen.gommestats.commands;

import net.anweisen.gommestats.GommeStats;
import net.anweisen.gommestats.commandmanager.CommandEvent;
import net.anweisen.gommestats.commandmanager.commands.Command;
import net.anweisen.gommestats.utils.entities.Embeds;

import static net.anweisen.gommestats.utils.Utils.syntax;

/**
 * @author anweisen
 * GommeBot developed on 07-31-2020
 * https://github.com/anweisen
 */

public class HelpCommand extends Command {

	public HelpCommand() {
		super("help", "h");
	}

	@Override
	public void onCommand(CommandEvent event) {
		event.queueReply(Embeds.builder()
				.setAuthor("» " + event.getJDA().getSelfUser().getName() + " • Information", GommeStats.BOT_INVITE, event.getJDA().getSelfUser().getEffectiveAvatarUrl())
				.addField("Information (4)", "» " + syntax(event, "stats <gamemode> [player]", false) + " • Sehe Spielerstatistiken ein\n" +
														"» " + syntax(event, "clan <clan>", false) + " • Sehe Claninfos ein\n" +
														"» " + syntax(event, "clans", false) + " • Sehe ein wie viele Clans es gibt\n" +
														"» " + syntax(event, "games", false) + " • Sehe ein welche Spielmodis es gibt", false)
				.addField("Allgemeines (2)", "» Lade den Bot auf deinen Server ein [ein](" + GommeStats.BOT_INVITE + ")\n" +
													     "» Joine auf den [Supportserver](" + GommeStats.SERVER_INVITE + ")", false)
				.build());
	}
}
