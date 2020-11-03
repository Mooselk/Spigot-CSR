package me.kate.redis.listeners.spigot;

import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.kate.redis.Main;
import me.kate.redis.commands.interact.BlockBreakCommand;
import me.kate.redis.utils.NaughtLocation;

public class BlockBreakListener implements Listener {

	private Main plugin;
	
	public BlockBreakListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		UUID uuid = event.getPlayer().getUniqueId();
		Block block = event.getBlock();
		NaughtLocation location = new NaughtLocation(block.getLocation()).convert();
		new BlockBreakCommand(plugin, uuid, location).send();
	}

}
