package me.kate.receive.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.kate.receive.npcs.internal.NPCManager;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		NPCManager.getAllNPCs().forEach(npcd -> {
			npcd.show(event.getPlayer());
		});
	}
}
