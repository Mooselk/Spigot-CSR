package me.kate.redis.commands.interact;

import java.util.UUID;

import org.bukkit.Material;

import me.kate.redis.Main;
import me.kate.redis.commands.Command;
import me.kate.redis.commands.CommandType;
import me.kate.redis.utils.NaughtLocation;

public class BlockPlaceCommand extends Command {
	
	private UUID uuid;
	private Material material;
	private NaughtLocation location;
	private byte data;
	
	private CommandType type;
	
	public BlockPlaceCommand(Main plugin, UUID uuid, Material material, NaughtLocation location, byte data) {
		super(plugin);
		this.uuid = uuid;
		this.material = material;
		this.location = location;
		this.data = data;
		this.type = CommandType.PLACE;
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
				location.getX() 
				+ ":" + 
				location.getY()
				+ ":" + 
				location.getZ()
				+ ":" +
				material.toString()
				+ ":" +
				data;
	}
	
}
