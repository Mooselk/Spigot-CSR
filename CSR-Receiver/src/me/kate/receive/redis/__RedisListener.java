package me.kate.receive.redis;

import java.util.concurrent.ExecutionException;

import org.bukkit.Location;
import org.bukkit.Material;

import me.kate.receive.Main;
import me.kate.receive.npcs.api.NPC;
import me.kate.receive.npcs.api.skin.Skin;
import me.kate.receive.npcs.api.state.NPCAnimation;
import me.kate.receive.npcs.api.state.NPCSlot;
import me.kate.receive.npcs.api.state.NPCState;
import me.kate.receive.npcs.internal.NPCManager;
import me.kate.receive.utils.ItemBuilder;
import me.kate.receive.utils.LocationUtils;
import redis.clients.jedis.JedisPubSub;

public class __RedisListener extends JedisPubSub {

	private Main plugin;
	
	public __RedisListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onMessage(String channel, String message) {
		
		System.out.print("Message received. Channel: {" + channel + "}, Msg: {" + message + "}");
		String[] msg = message.split(":");

		if (msg[1].equalsIgnoreCase("spawn")) {
			String uuid = msg[0];
			String name = msg[2];
			Skin skin = new Skin(name);
			
			Location loc = LocationUtils.toLoc("world", msg[3], msg[4], msg[5]);
			
			NPC npc = plugin.getLib().createNPC(name);
			
			plugin.getServer().getOnlinePlayers().forEach(player -> { 
				player.sendMessage("[CSR] Adding new player " + name + " (" + uuid + ")");
				player.sendMessage("[CSR] Creating new NPC puppet...");
			});
			
			npc.setLocation(loc);
			try {
				npc.setSkin(skin.future().get());
			} catch (IllegalStateException | InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			
			plugin.getServer().getOnlinePlayers().forEach(player -> player.sendMessage("[CSR] Applying skin..."));
			npc.create();
			
			plugin.getServer().getOnlinePlayers().forEach(player -> npc.show(player));
			
			plugin.getServer().getOnlinePlayers().forEach(player -> {
				player.sendMessage("[CSR] Syncing...");
				player.sendMessage("[CSR] Done!");
			});
		}
		
		NPCManager.getAllNPCs().forEach(npc -> {
			
			switch (msg[1].toLowerCase()) {
			
				case "sneak" : {
					String sneak = msg[2];
					
					// this works but, just 1 if else statement doesn't
					if (sneak.equals("0")) {
						npc.toggleState(NPCState.CROUCHED);
					} else {
						npc.toggleState(NPCState.STANDING);
					}
					
					if (sneak.equals("1")) {
						npc.toggleState(NPCState.CROUCHED);
					} else {
						npc.toggleState(NPCState.STANDING);
					}
					break;
				}
			
				case "place" : {
					npc.playAnimation(NPCAnimation.SWING_MAINHAND);
				
					Location loc = LocationUtils.toLoc("world", msg[2], msg[3], msg[4]);
				
					plugin.getServer().getScheduler().runTask(plugin, () -> {
						loc.getBlock().setType(Material.valueOf(msg[5]));
						loc.getBlock().setData(Byte.valueOf(msg[6]));
					});
					break;
				}
			
				case "break" : {
					Location loc = LocationUtils.toLoc("world", msg[2], msg[3], msg[4]);
					
					synchronized(plugin) {
						loc.getBlock().setType(Material.AIR);
					}
					
					plugin.getServer().getScheduler().runTask(plugin, () -> {
						loc.getBlock().setType(Material.AIR);
					});
					break;
				}
			
				case "move" : {
					Location loc = LocationUtils.toLoc("world", msg[2], msg[3], msg[4], msg[5], msg[6]);
					Location old = npc.getLocation();
					
					npc.moveTo(old, loc);
					npc.setLocation(loc);
					break;
				}
			
				case "hotbar" : {
					npc.setItem(NPCSlot.MAINHAND, new ItemBuilder(msg[2]).setEnchanted(msg[3]).create());
					break;
				}
				
				case "interact" : {
					npc.playAnimation(NPCAnimation.SWING_MAINHAND);
					break;
				}
			
			}
		});
	}
}
