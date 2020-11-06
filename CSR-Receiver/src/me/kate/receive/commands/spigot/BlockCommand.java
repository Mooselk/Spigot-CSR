package me.kate.receive.commands.spigot;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;

import me.kate.receive.Main;
import me.kate.receive.commands.CommandHandles;
import me.kate.receive.npcs.api.NPC;
import me.kate.receive.npcs.api.state.NPCAnimation;
import me.kate.receive.utils.LocationUtils;
import me.kate.receive.utils.SoundUtils;

public class BlockCommand extends CommandHandles {

	private Main plugin;
	private UUID uuid;
	private String x, y, z, type;
	
	private NPC npc;
	private Material mat;
	private byte data;
	private Location loc;
	
	public BlockCommand(Main plugin, String[] command) {
		this.plugin = plugin;
		this.uuid = uc(command[0]);
		this.type= command[1];
		
		this.x = command[2];
		this.y = command[3];
		this.z = command[4];
		
		this.npc = getUser(uuid).getNPC();
		this.mat = Material.getMaterial(command[5]);
		this.data = Byte.valueOf(command[6]);
		this.loc = LocationUtils.toLoc("world", x, y, z);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void placeBlock() {
		synchronized(plugin) {
			loc.getBlock().setType(mat);
			loc.getBlock().setData(data); // deprecated in 1.13.x+
		}
		npc.playAnimation(NPCAnimation.SWING_MAINHAND);
	}
	
	@Override
	public void breakBlock() {
		synchronized(plugin) {
			loc.getBlock().setType(Material.AIR);
			SoundUtils.playBreakSound(loc.getBlock());
		}
		npc.playAnimation(NPCAnimation.SWING_MAINHAND);
	}
	
	@Override
	public void handle() {
		if (type.equals("place")) 
			this.move();
		else if (type.equals("break")) 
			this.sneak();
	}
}
