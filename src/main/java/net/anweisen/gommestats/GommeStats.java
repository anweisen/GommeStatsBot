package net.anweisen.gommestats;

import net.anweisen.gommestats.commands.*;
import net.anweisen.gommestats.manager.clans.ClanWrapper;
import net.anweisen.gommestats.manager.stats.GameMode;
import net.anweisen.gommestats.manager.stats.PlayerStats;
import net.anweisen.gommestats.manager.stats.StatsWrapper;
import net.codingarea.engine.discord.commandmanager.CommandHandler;
import net.codingarea.engine.discord.commandmanager.ICommandHandler;
import net.codingarea.engine.discord.defaults.*;
import net.codingarea.engine.discord.defaults.DefaultStatusChanger.Generators;
import net.codingarea.engine.lang.ConstantLanguageManager;
import net.codingarea.engine.lang.Language;
import net.codingarea.engine.sql.MySQL;
import net.codingarea.engine.sql.SQL;
import net.codingarea.engine.utils.LogHelper;
import net.codingarea.engine.utils.config.ConfigLoader;
import net.dv8tion.jda.api.entities.Activity.ActivityType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

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

		ConfigLoader config = new ConfigLoader("config", "token");
		SQL sql = MySQL.defaultOfConfig(config);
		DefaultPrefixCache prefixCache = new DefaultPrefixCache(sql, "gs ");
		ICommandHandler handler = new CommandHandler(prefixCache);
		new ConstantLanguageManager(Language.loadResource("lang/german.lang"));
		ShardManager shardManager = DefaultBuilder.createShardManager(config.getString("token"),
																	  GatewayIntent.DIRECT_MESSAGES, GatewayIntent.DIRECT_MESSAGE_TYPING,
																	  GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_TYPING)
												  .disableCache(CacheFlag.CLIENT_STATUS, CacheFlag.ACTIVITY, CacheFlag.EMOTE, CacheFlag.VOICE_STATE)
												  .addEventListeners(new DefaultCommandListener(handler))
												  .build();

		handler.registerCommands(
				new StatsCommand(), new GamemodesCommand(), new ClansCommand(),
				new ClanInfoCommand(), new InviteCommand(), new HelpCommand(),
				new DefaultSetPrefixCommand(prefixCache)
		);


		DefaultStatusChanger.forShardManager(shardManager, Generators.of(ActivityType.DEFAULT, "",
				"gs stats • Statistiken", "gs clan • Claninfos")).sync(15);

	}
}
