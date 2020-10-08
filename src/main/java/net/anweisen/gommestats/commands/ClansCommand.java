package net.anweisen.gommestats.commands;

import net.anweisen.gommestats.manager.clans.ClanWrapper;
import net.codingarea.engine.discord.commandmanager.Command;
import net.codingarea.engine.discord.commandmanager.CommandEvent;
import net.codingarea.engine.utils.NumberFormatter;
import org.jetbrains.annotations.NotNull;

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
	public void onCommand(@NotNull final CommandEvent event) throws Exception {
		int clans = ClanWrapper.getActiveClansWithException();
		event.queueReply("Es sind derzeit **" + NumberFormatter.MIDDLE_NUMBER.format(clans) + " Clans** aktiv");
	}

}
