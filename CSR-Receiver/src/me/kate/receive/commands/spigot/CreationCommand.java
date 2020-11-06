package me.kate.receive.commands.spigot;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.bukkit.Location;

import me.kate.receive.CSRUser;
import me.kate.receive.Main;
import me.kate.receive.commands.CommandHandles;
import me.kate.receive.npcs.api.NPC;
import me.kate.receive.npcs.api.skin.Skin;
import me.kate.receive.utils.LocationUtils;

public class CreationCommand extends CommandHandles {

	private Main plugin;
	private UUID uuid;
	
	private String name, type;
	private String x, y, z;
	
	private Skin skin;
	private Location loc;
	
	public CreationCommand(Main plugin, String[] command) {
		this.plugin = plugin;
		this.uuid = uc(command[0]);
		this.type = command[1];
		
		if (command.length >= 5) {	
			this.name = command[2];
			
			this.x = command[3];
			this.y = command[4];
			this.z = command[5];
		
			this.skin = new Skin(name);
			this.loc = LocationUtils.toLoc("world", x, y, z);
		}
	}
	
	@Override
	public void spawn() {
		NPC npc = null;		
		try {
			npc = plugin.getLib().createNPC(name); this.sendDebug(1);
			npc.setLocation(loc);
			npc.setSkin(skin.future().get());	   this.sendDebug(2);
			npc.showAll();						   this.sendDebug(3);
		} catch (IllegalStateException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destory() {
		CSRUser user = CSRUser.getUsers().remove(uuid);
		if (user != null) {
			user.destroy();
		}
	}
	
	@Override
	public void handle() {
		if (type.equals("spawn")) 
			this.spawn();
		else if (type.equals("destory")) 
			this.destory();
	}

	private void sendDebug(int part) {
		if (part == 1) {
			plugin.getServer().getOnlinePlayers().forEach(player -> { 
				player.sendMessage("[CSR] Adding new player " + name + " (" + uuid + ")");
				player.sendMessage("[CSR] Creating new NPC puppet...");
			});
		} else if (part == 2) {
			plugin.getServer().getOnlinePlayers().forEach(player -> player.sendMessage("[CSR] Applying skin..."));
		} else if (part == 3) {
			plugin.getServer().getOnlinePlayers().forEach(player -> {
				player.sendMessage("[CSR] Syncing...");
				player.sendMessage("[CSR] Done!");
			});
		}
	}
}
