package net.anweisen.gommestats;

import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import net.anweisen.gommestats.commandmanager.CommandHandler;
import net.anweisen.gommestats.commands.*;
import net.anweisen.gommestats.listener.MessageListener;
import net.anweisen.gommestats.utils.SecretsManager;
import net.anweisen.gommestats.utils.Utils;
import net.anweisen.gommestats.utils.commons.Log;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import sun.net.www.http.HttpClient;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author anweisen
 * GommeBot developed on 07-30-2020
 * https://github.com/anweisen
 */

public class GommeStats {

	public static final String BOT_INVITE = "https://discord.com/api/oauth2/authorize?client_id=738853688491114577&permissions=378880&scope=bot",
							   SERVER_INVITE = "https://discord.gg/JubAmHS";

	public static void main(String[] args) {
		new GommeStats();
	}

	private ShardManager shardManager;

	public GommeStats() {
		try {


			SecretsManager secrets = new SecretsManager(new File("config.properties"));
			DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(secrets.getToken());
			shardManager = builder.build();

			CommandHandler handler = new CommandHandler(shardManager.getShardById(0).getSelfUser().getId());
			handler.registerCommands(
					new StatsCommand(), new GamemodesCommand(), new ClansCommand(),
					new ClanInfoCommand(), new InviteCommand(), new HelpCommand()
			);

			Object[] listeners = {
					new MessageListener(handler)
			};
			for (JDA currentShard : shardManager.getShards()) {
				currentShard.addEventListener(listeners);
			}

		} catch (Exception ex) {
			Log.severe("Could not start bot :: " + ex.getMessage());
			System.exit(4);
		}

		startActivityTimer();

	}

	private void startActivityTimer() {

		new Timer().schedule(new TimerTask() {

			private int i = 0;

			@Override
			public void run() {

				String[] status = {
						"gs stats • Statistiken",
						"gs clan • Claninfos"
				};

				shardManager.setActivity(Activity.playing(status[i]));

				i++;
				if (i >= status.length) i = 0;

			}
		}, 1000, 15*1000);

	}

	public ShardManager getShardManager() {
		return shardManager;
	}
}
