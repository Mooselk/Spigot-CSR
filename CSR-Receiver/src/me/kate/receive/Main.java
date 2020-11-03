package me.kate.receive;

import org.bukkit.plugin.java.JavaPlugin;

import me.kate.receive.listeners.PlayerCrouchListener;
import me.kate.receive.listeners.PlayerJoinListener;
import me.kate.receive.npcs.NPCLib;
import me.kate.receive.redis.RedisThread;
import redis.clients.jedis.Jedis;

public class Main extends JavaPlugin {

private Jedis jedis;
	
	private String host = "localhost";
	private int port = 6379;
	private int timeout = 5000;
	public String channel = "packets";
	
	private static NPCLib npcl;
	private static Main instance;
	
	@Override
	public void onEnable() {
		instance = this;
		
		npcl = new NPCLib(this);
		this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerCrouchListener(), this);
		this.setupRedis();
	}

	private void setupRedis() {
		jedis = new Jedis(host, port, timeout);
		jedis.connect();
		
		new RedisThread(this).start();
		
		if (jedis.isConnected()) {
			this.getServer().getLogger().info("[CSR] Connected to Redis");
		} else {
			this.getServer().getLogger().info("[CSR] Failed to connect to Redis");
		}
	}
	
	public Jedis getJedis() {
		return jedis;
	}
	
	public NPCLib getLib() {
		return npcl;
	}
	
	public static Main instance() {
		return instance;
	}
	
}