package me.kate.redis.commands.movement;

import java.util.UUID;

import me.kate.redis.Main;
import me.kate.redis.commands.Command;
import me.kate.redis.commands.CommandType;

public class SneakCommand extends Command {
	
	private boolean sneaking;
	private UUID uuid;
	private CommandType type;
	
	public SneakCommand(Main plugin, UUID uuid, boolean sneaking) {
		super(plugin);
		this.sneaking = sneaking;
		this.uuid = uuid;
		this.type = CommandType.SNEAK;
	}

	@Override
	public CommandType getType() {
		return type;
	}

	@Override
	public String format() {
		
		if (sneaking) {
			return uuid.toString() 
					+ ":" +
					type.toString()
					+ ":" + 
					"1";
		}
		
		return uuid.toString() 
				+ ":" +
				type.toString()
				+ ":" + 
				"0";
	}

}
