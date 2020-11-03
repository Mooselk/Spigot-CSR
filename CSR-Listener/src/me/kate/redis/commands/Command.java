package me.kate.redis.commands;

import me.kate.redis.Main;

public abstract class Command {

	private Main plugin;
	
	public Command(Main plugin) {
		this.plugin = plugin;
	}
	
	public void send() {
		try {
			plugin.getJedis().publish(plugin.channel, this.format());
		} catch (Exception e) {
			plugin.getServer().getLogger().severe(e.getMessage());
		}
	}
	
	public abstract CommandType getType();
	
	public abstract String format();
	
}
