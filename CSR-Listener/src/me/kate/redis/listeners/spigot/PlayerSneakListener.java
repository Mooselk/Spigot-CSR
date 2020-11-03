package me.kate.redis.listeners.spigot;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import me.kate.redis.Main;
import me.kate.redis.commands.movement.SneakCommand;

public class PlayerSneakListener implements Listener {
	
	private Main plugin;
	
	public PlayerSneakListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();
		new SneakCommand(plugin, player.getUniqueId(), player.isSneaking()).send();
	}

}
