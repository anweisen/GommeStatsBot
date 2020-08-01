package net.anweisen.gommestats.listener;

import net.anweisen.gommestats.commandmanager.CommandHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

/**
 * @author anweisen
 * GommeBot developed on 07-30-2020
 * https://github.com/anweisen
 */

public class MessageListener extends ListenerAdapter {

	private final CommandHandler handler;

	public MessageListener(CommandHandler handler) {
		this.handler = handler;
	}

	@Override
	public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
		handler.handleCommand("gs ", event);
	}

}
