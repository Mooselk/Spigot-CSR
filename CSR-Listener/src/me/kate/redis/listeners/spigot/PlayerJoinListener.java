package me.kate.redis.listeners.spigot;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.kate.redis.Main;
import me.kate.redis.commands.create.SpawnCommand;
import me.kate.redis.utils.NaughtLocation;

public class PlayerJoinListener implements Listener {
	
	private Main plugin;
	
	public PlayerJoinListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		String name = player.getName();
		UUID uuid = player.getUniqueId();
		NaughtLocation location = new NaughtLocation(player.getLocation()).convert();
		
		new SpawnCommand(plugin, uuid, name, location).send();
	}

}
