package me.kate.receive.redis;

import me.kate.receive.Main;
import me.kate.receive.commands.CommandHandles;
import me.kate.receive.commands.spigot.BlockCommand;
import me.kate.receive.commands.spigot.CreationCommand;
import me.kate.receive.commands.spigot.InventoryCommand;
import me.kate.receive.commands.spigot.MovementCommand;
import redis.clients.jedis.JedisPubSub;

public class RedisListener extends JedisPubSub {

	private Main plugin;
	
	public RedisListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void onMessage(String channel, String message) {
		
		CommandHandles command = null;
		String[] splitCommand = message.split(":"); 
		String type = splitCommand[1];
		
		if (type.equals("spawn") || type.equals("destroy")) {
			command = new CreationCommand(plugin, splitCommand);
		} 
		else if (type.equals("place") || type.equals("break")) {
			command = new BlockCommand(plugin, splitCommand);
		}
		else if (type.equals("move") || type.equals("sneak")) {
			command = new MovementCommand(plugin, splitCommand);
		}
		else if (type.equals("hotbar") || type.equals("interact")) {
			command = new InventoryCommand(plugin, splitCommand);
		}
		
		if (command != null) {
			command.handle();
		}
	}
	
}
