package net.anweisen.gommestats.commands;

import net.anweisen.gommestats.GommeStats;
import net.anweisen.gommestats.utils.entities.Embeds;
import net.codingarea.engine.discord.commandmanager.Command;
import net.codingarea.engine.discord.commandmanager.CommandEvent;
import org.jetbrains.annotations.NotNull;

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
	public void onCommand(@NotNull final CommandEvent event) {
		event.queueReply(Embeds.builder()
				.setAuthor("» " + event.getJDA().getSelfUser().getName() + " • Information", GommeStats.BOT_INVITE, event.getJDA().getSelfUser().getEffectiveAvatarUrl())
				.addField("Information (4)", "» " + CommandEvent.syntax(event, "stats <gamemode> [player]", false) + " • Sehe Spielerstatistiken ein\n" +
														"» " + CommandEvent.syntax(event, "clan <clan>", false) + " • Sehe Claninfos ein\n" +
														"» " + CommandEvent.syntax(event, "clans", false) + " • Sehe ein wie viele Clans es gibt\n" +
														"» " + CommandEvent.syntax(event, "games", false) + " • Sehe ein welche Spielmodis es gibt", false)
				.addField("Administration (1)", "» " + CommandEvent.syntax(event, "setprefix <prefix>", false) + " • Setze den Prefix", false)
				.addField("Allgemeines (2)", "» Lade den Bot auf deinen Server [ein](" + GommeStats.BOT_INVITE + ")\n" +
													     "» Joine auf den [Supportserver](" + GommeStats.SERVER_INVITE + ")", false)
				.build());
	}
}
