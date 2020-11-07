package net.anweisen.gommestats;

import net.anweisen.gommestats.manager.clans.ClanWrapper;
import net.anweisen.gommestats.manager.stats.GameMode;
import net.anweisen.gommestats.manager.stats.PlayerStats;
import net.anweisen.gommestats.manager.stats.StatsWrapper;
import net.codingarea.engine.utils.LogHelper;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import static net.codingarea.engine.utils.LogHelper.info;

/**
 * @author anweisen
 * GommeBot developed on 07-30-2020
 * https://github.com/anweisen
 */

public class GommeStats {

	public static final String BOT_INVITE = "https://discord.com/api/oauth2/authorize?client_id=738853688491114577&permissions=378880&scope=bot",
							   SERVER_INVITE = "https://discord.gg/JubAmHS";

	public static void main(String[] args) throws Throwable {
		new GommeStats();
	}

	public GommeStats() throws Throwable {

		String[] names = new String[] {
				"anweisen", "KxmischesDomi", "7alex", "Angelo", "GommeHD", "Dner", "rewinside", "Sturmwaffel", "Dominik",
				"unge", "ungespielt", "xMarie_x", "FABIOMTK05", "ImAwesomeCereal", "blobheart", "zzapi9z", "vfries", "ChrisandGarrett"
		};

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				System.err.println();
				for (String name : names) {

					new Thread(() -> {

						try {

							long start = System.currentTimeMillis();
							PlayerStats[] stats = StatsWrapper.getsStatsByName(name, GameMode.values());
							info("Finished " + name + " in " + (System.currentTimeMillis()-start) + "ms");

						} catch (Exception ex) {
							System.err.println(ex.getClass().getSimpleName() + " for user " + name);
						}

					}).start();

				}

			}
		}, 1, 15*1000);


/*
		ConfigLoader config = new ConfigLoader("config", "token");
		CommandHandler handler = new CommandHandler();
		SQL sql = MySQL.defaultOfConfig(config);
		DefaultPrefixCache prefixCache = new DefaultPrefixCache(sql, "gs ");
		new ConstantLanguageManager(Language.loadResource("lang/german.lang"));
		ShardManager shardManager = DefaultBuilder.createShardManager(config.getString("token"),
																	  GatewayIntent.DIRECT_MESSAGES, GatewayIntent.DIRECT_MESSAGE_TYPING,
																	  GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_TYPING)
												  .disableCache(CacheFlag.CLIENT_STATUS, CacheFlag.ACTIVITY, CacheFlag.EMOTE, CacheFlag.VOICE_STATE)
												  .addEventListeners(new DefaultCommandListener(handler, prefixCache))
												  .build();

		handler.registerCommands(
				new StatsCommand(), new GamemodesCommand(), new ClansCommand(),
				new ClanInfoCommand(), new InviteCommand(), new HelpCommand(),
				new DefaultSetPrefixCommand(prefixCache)
		);


		new DefaultStatusChanger(shardManager, "", "gs stats • Statistiken", "gs clan • Claninfos").sync(15);
		*/

	}
}
