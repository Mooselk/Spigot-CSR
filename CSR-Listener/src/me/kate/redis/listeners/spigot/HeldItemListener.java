package me.kate.redis.listeners.spigot;

import java.util.UUID;

import org.bukkit.Material;
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
		UUID uuid = event.getPlayer().getUniqueId();
		ItemStack item = event.getPlayer().getItemInHand();
		Material material = item.getType();
		boolean notEnchanted = item.getEnchantments().isEmpty();
		
		if (event.getPlayer().getItemInHand() == null) {
			item = new ItemStack(Material.AIR);
		}
		
		new HeldItemCommand(plugin, uuid, material, notEnchanted).send();
		
	}
	
}
