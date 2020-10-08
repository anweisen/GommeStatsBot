package net.anweisen.gommestats.commands;

import net.anweisen.gommestats.GommeStats;
import net.codingarea.engine.discord.commandmanager.Command;
import net.codingarea.engine.discord.commandmanager.CommandEvent;

/**
 * @author anweisen
 * GommeBot developed on 07-31-2020
 * https://github.com/anweisen
 */
public class InviteCommand extends Command {

	public InviteCommand() {
		super("invite");
	}

	@Override
	public void onCommand(CommandEvent event) {
		event.queueReply("Du kannst mich über diesen Link einladen: \n" + GommeStats.BOT_INVITE, message -> message.suppressEmbeds(true).queue());
	}
}
