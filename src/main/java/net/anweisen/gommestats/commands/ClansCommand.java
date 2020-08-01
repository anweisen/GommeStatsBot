package net.anweisen.gommestats.commands;

import net.anweisen.gommestats.commandmanager.CommandEvent;
import net.anweisen.gommestats.commandmanager.commands.Command;
import net.anweisen.gommestats.manager.clans.ClanWrapper;
import net.anweisen.gommestats.manager.stats.StatsAttribute.AttributeValueFormatter;

/**
 * @author anweisen
 * GommeBot developed on 07-31-2020
 * https://github.com/anweisen
 */

public class ClansCommand extends Command {

	public ClansCommand() {
		super("clans");
	}

	@Override
	public void onCommand(CommandEvent event) {

		try {

			int clans = ClanWrapper.getActiveClansWithException();
			event.queueReply("Es sind derzeit **" + AttributeValueFormatter.MIDDLE_NUMBER.format(clans) + " Clans** aktiv");

		} catch (Exception ex) {
			event.queueReply("Etwas ist schief gelaufen: `" + ex.getMessage() + "`");
		}

	}

}
