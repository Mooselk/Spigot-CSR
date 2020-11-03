package me.kate.redis.commands.interact;

import java.util.UUID;

import org.bukkit.Material;

import me.kate.redis.Main;
import me.kate.redis.commands.Command;
import me.kate.redis.commands.CommandType;

public class HeldItemCommand extends Command {

	private UUID uuid;
	private Material material;
	private Boolean enchanted;
	private CommandType type;
	
	public HeldItemCommand(Main plugin, UUID uuid, Material material, boolean enchanted) {
		super(plugin);
		this.uuid = uuid;
		this.material = material;
		this.enchanted = enchanted;
		this.type = CommandType.HOTBAR;
	}

	@Override
	public CommandType getType() {
		return type;
	}

	@Override
	public String format() {
		return uuid.toString() 
				+ ":" + 
				type.toString()
				+ ":" + 
				material.toString() 
				+ ":" + 
				enchanted.toString();
	}
	
}
