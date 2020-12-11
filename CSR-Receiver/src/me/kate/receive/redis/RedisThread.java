package me.kate.receive.redis;

import me.kate.receive.Main;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisThread {
	
	private Main plugin;
	
	public RedisThread(Main plugin) {
		this.plugin = plugin;
	}
	
	public void start() {
		
    	final JedisPoolConfig poolConfig = new JedisPoolConfig();
		final JedisPool jedisPool = new JedisPool(poolConfig, "localhost", 6379, 0);
		final Jedis subscriberJedis = jedisPool.getResource();
		final RedisListener subscriber = new RedisListener(plugin);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                	System.out.println("Subscribing to " + plugin.channel + ". This thread will be blocked.");
                    subscriberJedis.subscribe(subscriber, plugin.channel);
                    System.out.println("Subscription ended.");
                } catch (Exception e) {
                	System.out.println("Subscribing failed. " + e);
                	e.printStackTrace();
                }
            }
        }).start();
        jedisPool.close();
	}
	
}
