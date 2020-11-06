package me.kate.redis.commands.create;

import java.util.UUID;

import me.kate.redis.Main;
import me.kate.redis.commands.Command;
import me.kate.redis.commands.CommandType;

public class UserDestroyCommand extends Command {

	private UUID uuid;
	private CommandType type;
	
	public UserDestroyCommand(Main plugin, UUID uuid) {
		super(plugin);
		this.uuid = uuid;
		this.type = CommandType.USER_DESTROY;
	}

	@Override
	public CommandType getType() {
		return type;
	}

	@Override
	public String format() {
		return uuid.toString() + ":" + type.toString();
	}

}
