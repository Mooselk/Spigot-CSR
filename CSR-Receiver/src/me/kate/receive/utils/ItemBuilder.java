package me.kate.receive.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

	private ItemStack item;
	
	public ItemBuilder(Material material) {
		this.item = new ItemStack(material);
	}
	
	public ItemBuilder(String material) {
		this.item = new ItemStack(Material.getMaterial(material));
	}
	
	public ItemStack create() {
		return item;
	}
	
	public ItemBuilder setEnchanted(String enchant) {
		ItemMeta im = item.getItemMeta();
		boolean ench = Boolean.valueOf(enchant);
		if (!ench) {
			im.addEnchant(Enchantment.DURABILITY, 1, true);
			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			item.setItemMeta(im);
		}
		return this;
	}
	
}
