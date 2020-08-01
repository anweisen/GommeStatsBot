package net.anweisen.gommestats.commands;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import net.anweisen.gommestats.commandmanager.CommandEvent;
import net.anweisen.gommestats.commandmanager.commands.Command;
import net.anweisen.gommestats.manager.clans.Clan;
import net.anweisen.gommestats.manager.clans.ClanRank;
import net.anweisen.gommestats.manager.clans.ClanWrapper;
import net.anweisen.gommestats.manager.stats.PlayerStats;
import net.anweisen.gommestats.utils.entities.Embeds;
import net.dv8tion.jda.api.EmbedBuilder;

import static net.anweisen.gommestats.utils.Utils.syntax;

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
	public void onCommand(CommandEvent event) {

		if (event.getArgs().length != 1) {
			event.queueReply("Benutze " + syntax(event, "<clan>"));
			return;
		}

		String clanName = event.getArg(0);
		event.getChannel().sendTyping().queue();

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

			event.queueReply(embed.build());

		} catch (FailingHttpStatusCodeException ignored) {
			event.queueReply("Den Clan `" + clanName + "` gibt es nicht. Achte auf Groß- und Kleinschreibung.");
		} catch (Exception ex) {
			event.queueReply("Etwas ist schief gelaufen: `" + ex.getMessage() + "`");
		}

	}

}
