package me.kate.redis.commands.interact;

import java.util.UUID;

import me.kate.redis.Main;
import me.kate.redis.commands.Command;
import me.kate.redis.commands.CommandType;
import me.kate.redis.utils.NaughtLocation;

public class BlockBreakCommand extends Command {

	private UUID uuid;
	private NaughtLocation location;
	
	private CommandType type;
	
	public BlockBreakCommand(Main plugin, UUID uuid, NaughtLocation location) {
		super(plugin);
		this.uuid = uuid;
		this.location = location;
		this.type = CommandType.BREAK;
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
				location.getZ();
	}
	
}
