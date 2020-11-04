package me.kate.redis.listeners.spigot;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import me.kate.redis.Main;
import me.kate.redis.commands.interact.HeldItemCommand;

public class HeldItemListener implements Listener {

	private Main plugin;
	
	public HeldItemListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onHeldItemChange(PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();
		int hotbarSlot = event.getNewSlot();
		ItemStack item = null;
		
		if (player.getInventory().getItem(hotbarSlot) != null) {
			item = player.getInventory().getItem(hotbarSlot);
		} else {
			item = new ItemStack(Material.AIR);
		}
		
		Material material = item.getType();
		boolean notEnchanted = item.getEnchantments().isEmpty();
		
		new HeldItemCommand(plugin, uuid, material, notEnchanted).send();	
	}
	
}
