package me.kate.redis.commands.interact;

import java.util.UUID;

import org.bukkit.event.block.Action;

import me.kate.redis.Main;
import me.kate.redis.commands.Command;
import me.kate.redis.commands.CommandType;

public class InteractCommand extends Command {

	private UUID uuid;
	private Action action;
	private CommandType type;
	
	public InteractCommand(Main plugin, UUID uuid, Action action) {
		super(plugin);
		this.uuid = uuid;
		this.action = action;
		this.type = CommandType.INTERACT;
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
				action.toString();
	}
	
}
