package net.anweisen.gommestats.utils.entities;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

/**
 * @author anweisen
 * GommeBot developed on 07-30-2020
 * https://github.com/anweisen
 */

public class Embeds {

	public static final Color COLOR = Color.decode("#FECC01");

	public static EmbedBuilder builder() {
		return new EmbedBuilder().setColor(COLOR);
	}

}
