package net.anweisen.gommestats.commands;

import net.anweisen.gommestats.manager.stats.GameMode;
import net.codingarea.engine.discord.commandmanager.Command;
import net.codingarea.engine.discord.commandmanager.CommandEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author anweisen
 * GommeBot developed on 07-31-2020
 * https://github.com/anweisen
 */

public class GamemodesCommand extends Command {

	public GamemodesCommand() {
		super("gamemodes", false, "gm", "gms", "g");
	}

	@Override
	public void onCommand(@NotNull final CommandEvent event) {

		StringBuilder modes = new StringBuilder();
		for (GameMode currentGameMode : GameMode.values()) {
			if (!modes.toString().isEmpty()) {
				modes.append(", ");
			}
			modes.append(currentGameMode.getName());
		}

		event.queueReply("Es gibt die folgenden Spielmodis: \n`" + modes + "`");
	}
}
