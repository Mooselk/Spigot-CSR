package me.kate.redis.commands.create;

import me.kate.redis.Main;
import me.kate.redis.commands.Command;
import me.kate.redis.commands.CommandType;

public class UserCreateCommand extends Command {

	private CommandType type;
	
	public UserCreateCommand(Main plugin) {
		super(plugin);
		this.type = CommandType.USER_CREATE;
	}

	@Override
	public CommandType getType() {
		return type;
	}

	@Override
	public String format() {
		return null;
	}

}
