package me.kate.redis.listeners.spigot;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import me.kate.redis.Main;
import me.kate.redis.commands.interact.InteractCommand;

public class PlayerInteractListener implements Listener {

	private Main plugin;
	
	public PlayerInteractListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		new InteractCommand(plugin, player.getUniqueId(), event.getAction()).send();
	}
	
}
