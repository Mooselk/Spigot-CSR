package me.kate.redis.listeners.spigot;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.kate.redis.Main;
import me.kate.redis.commands.movement.MoveCommand;
import me.kate.redis.utils.NaughtLocation;

public class MovementListener implements Listener {
	
	private Main plugin;
	
	public MovementListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		
		final Player player = event.getPlayer();
		Location location = event.getTo();
		
		MoveCommand command = new MoveCommand(
				plugin, player.getUniqueId(), 
				new NaughtLocation(
						location.getX(), 
						location.getY(), 
						location.getZ(),
						location.getPitch(),
						location.getYaw()));
		command.send();
		
	}
}