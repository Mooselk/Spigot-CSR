package me.kate.redis.commands.create;

import java.util.UUID;

import me.kate.redis.Main;
import me.kate.redis.commands.Command;
import me.kate.redis.commands.CommandType;
import me.kate.redis.utils.NaughtLocation;

public class SpawnCommand extends Command {
	
	private UUID uuid;
	private String playerName;
	private NaughtLocation location;
	private CommandType type;
	
	public SpawnCommand(Main plugin, UUID uuid, String playerName, NaughtLocation location) {
		super(plugin);
		this.uuid = uuid;
		this.playerName = playerName;
		this.location = location;
		this.type = CommandType.SPAWN;
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
				playerName
				+ ":" + 
				location.getX() 
				+ ":" + 
				location.getY()
				+ ":" + 
				location.getZ() 
				+ ":" + 
				location.getPitch() 
				+ ":" + 
				location.getYaw();
	}

}
