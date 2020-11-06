package me.kate.receive.commands.spigot;

import java.util.UUID;

import org.bukkit.Material;

import me.kate.receive.Main;
import me.kate.receive.commands.CommandHandles;
import me.kate.receive.npcs.api.NPC;
import me.kate.receive.npcs.api.state.NPCAnimation;
import me.kate.receive.npcs.api.state.NPCSlot;
import me.kate.receive.utils.ItemBuilder;

public class InventoryCommand extends CommandHandles {

	// private Main plugin;
	private UUID uuid;
	private String type, enchanted;
	
	private Material mat;
	private NPC npc;
	
	public InventoryCommand(Main plugin, String[] command) {
		// this.plugin = plugin;
		this.uuid = uc(command[0]);
		this.type = command[1];
		
		if (command.length == 3) {
			this.mat = Material.getMaterial(command[2]);
			this.enchanted = command[3];
		}
		
		this.npc = getUser(uuid).getNPC();
	}
	
	@Override
	public void interact() {
		npc.playAnimation(NPCAnimation.SWING_MAINHAND);
	}
	
	@Override
	public void hotbarMove() {
		npc.setItem(NPCSlot.MAINHAND, new ItemBuilder(mat)
				.setEnchanted(enchanted)
				.create());
	}
	
	@Override
	public void armorEquip() {
		// to-do
	}
	
	@Override
	public void handle() {
		if (type.equals("interact")) {
			this.interact();
		} else if (type.equals("armorequip")) {
			this.armorEquip();
		} else if (type.equals("hotbar")) {
			this.hotbarMove();
		}
	}

}
