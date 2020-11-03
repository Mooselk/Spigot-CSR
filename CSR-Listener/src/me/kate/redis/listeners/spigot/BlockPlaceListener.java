package me.kate.redis.listeners.spigot;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import me.kate.redis.Main;
import me.kate.redis.commands.interact.BlockPlaceCommand;
import me.kate.redis.utils.NaughtLocation;

public class BlockPlaceListener implements Listener {
	
	private Main plugin;
	
	public BlockPlaceListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		UUID uuid = event.getPlayer().getUniqueId();
		Block block = event.getBlockPlaced();
		Byte blockDirection = event.getBlock().getData(); // removed in later version, prob 1.13.x
		Material material = block.getType();
		NaughtLocation location = new NaughtLocation(block.getLocation()).convert();
		
		new BlockPlaceCommand(plugin, uuid, material, location, blockDirection).send();
	}

}
