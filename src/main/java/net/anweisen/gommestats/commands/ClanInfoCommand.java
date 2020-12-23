package net.anweisen.gommestats.commands;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import net.anweisen.gommestats.manager.clans.Clan;
import net.anweisen.gommestats.manager.clans.ClanRank;
import net.anweisen.gommestats.manager.clans.ClanWrapper;
import net.anweisen.gommestats.manager.stats.PlayerStats;
import net.anweisen.gommestats.utils.entities.Embeds;
import net.codingarea.engine.discord.commandmanager.Command;
import net.codingarea.engine.discord.commandmanager.event.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * @author anweisen
 * GommeBot developed on 07-31-2020
 * https://github.com/anweisen
 */
public class ClanInfoCommand extends Command {

	public ClanInfoCommand() {
		super("clan", true, "claninfo", "clanlist", "cl", "list");
	}

	@Override
	public void onCommand(@NotNull final CommandEvent event) throws Exception {

		if (event.getArgs().length != 1) {
			event.reply("Benutze " + syntax(event, "<clan>"));
			return;
		}

		String clanName = event.getArg(0);
		event.sendTyping();

		try {

			Clan clan = ClanWrapper.getClanWithException(clanName);

			EmbedBuilder embed = Embeds.builder();
			embed.setAuthor((clan.getAvatar() != null ? "» " : "") + "Claninfo von " + clanName + " " + clan.getTag() + " • " + clan.getSize() + " Member", Clan.getURL(clanName), clan.getAvatar());

			String iterator = "\n» ";
			for (ClanRank currentRank : ClanRank.values()) {

				StringBuilder builder = new StringBuilder();
				int members = 0;

				for (String currentMember : clan.getMember(currentRank)) {

					builder.append(iterator + "[" + currentMember + "](" + PlayerStats.getURL(currentMember, null) + ")");

					members++;
					if (members >= 10) {
						builder.append(iterator + "...");
						break;
					}

				}

				if (clan.getMember(currentRank).size() > 0) {
					embed.addField(currentRank.getName() + " (" + clan.getMember(currentRank).size() + ")", builder.toString(), false);
				}

			}

			event.reply(embed.build());

		} catch (FailingHttpStatusCodeException ignored) {
			event.reply("Den Clan `" + clanName + "` gibt es nicht. Achte auf Groß- und Kleinschreibung.");
		}

	}

}
