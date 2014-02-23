package org.powerbot.bot.event.debug;

import java.awt.Graphics;

import org.powerbot.event.TextPaintListener;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.wrappers.Player;
import org.powerbot.script.wrappers.Tile;

import static org.powerbot.bot.event.debug.DebugHelper.drawLine;

public class TLocation implements TextPaintListener {
	private final MethodContext ctx;

	public TLocation(final MethodContext ctx) {
		this.ctx = ctx;
	}

	public int draw(int idx, final Graphics render) {
		final Player player = ctx.players.local();
		if (player != null) {
			final Tile tile = player.getLocation();
			drawLine(render, idx++, "Position: " + (tile != null ? tile.toString() : ""));
		}
		return idx;
	}
}
