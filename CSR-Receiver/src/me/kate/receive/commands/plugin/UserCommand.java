package me.kate.receive.commands.plugin;

import java.util.UUID;

import me.kate.receive.CSRUserManager;
import me.kate.receive.Main;
import me.kate.receive.commands.CommandHandles;

public class UserCommand extends CommandHandles {

	// private Main plugin;
	private UUID uuid;
	private String type;
	
	public UserCommand(Main plugin, String[] command) {
		// this.plugin = plugin;
		this.uuid = uc(command[0]);
		this.type = command[1];
	}
	
	@Override
	public void createUser() {
		// CSRUser user = new CSRUser(uuid, null);
	}
	
	@Override
	public void destroyUser() {
		CSRUserManager.removeUser(uuid);
	}
	
	@Override
	public void handle() {
		if (type.equals("USER_CREATE")) 
			this.spawn();
		else if (type.equals("USER_DESTROY")) 
			this.destory();
	}

}
