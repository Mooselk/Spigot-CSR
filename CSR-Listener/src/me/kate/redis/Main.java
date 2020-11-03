package me.kate.redis;

import org.bukkit.plugin.java.JavaPlugin;

import me.kate.redis.listeners.spigot.BlockBreakListener;
import me.kate.redis.listeners.spigot.BlockPlaceListener;
import me.kate.redis.listeners.spigot.HeldItemListener;
import me.kate.redis.listeners.spigot.MovementListener;
import me.kate.redis.listeners.spigot.PlayerInteractListener;
import me.kate.redis.listeners.spigot.PlayerJoinListener;
import me.kate.redis.listeners.spigot.PlayerSneakListener;
import redis.clients.jedis.Jedis;

public class Main extends JavaPlugin {

	private Jedis jedis;
	
	private String host = "localhost";
	private int port = 6379;
	private int timeout = 5000;
	public String channel = "packets";
	
	private static Main instance;
	
	@Override
	public void onEnable() {
		instance = this;
		this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
		this.getServer().getPluginManager().registerEvents(new MovementListener(this), this);
		this.getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
		this.getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerSneakListener(this), this);
		this.getServer().getPluginManager().registerEvents(new HeldItemListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
		this.setupRedis();
		if (jedis.isConnected()) {
			this.getServer().getLogger().info("[CSR] Connected to Redis");
		} else {
			this.getServer().getLogger().info("[CSR] Failed to connect to Redis");
		}
	}

	private void setupRedis() {
		jedis = new Jedis(host, port, timeout);
		jedis.connect();
	}
	
	public Jedis getJedis() {
		return jedis;
	}
	
	public static Main instance() {
		return instance;
	}
	
}
