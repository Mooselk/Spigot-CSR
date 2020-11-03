package me.kate.receive.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import me.kate.receive.npcs.internal.NPCManager;

public class PlayerCrouchListener implements Listener {

	
	@EventHandler
	public void crouch(PlayerToggleSneakEvent e) {
		if (e.getPlayer().isSneaking()) {
			NPCManager.getAllNPCs().forEach(npc -> {
				e.getPlayer().sendMessage("Location: " + npc.getLocation());
			});
		}
	}
	
}
