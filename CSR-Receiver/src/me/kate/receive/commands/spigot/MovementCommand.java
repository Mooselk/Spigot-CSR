package me.kate.receive.commands.spigot;

import java.util.UUID;

import org.bukkit.Location;

import me.kate.receive.Main;
import me.kate.receive.commands.CommandHandles;
import me.kate.receive.npcs.api.NPC;
import me.kate.receive.npcs.api.state.NPCState;
import me.kate.receive.utils.LocationUtils;

public class MovementCommand extends CommandHandles {

	// private Main plugin;
	private UUID uuid;
	
	private String x, y, z, pit, yaw, type/*, sneaking*/;

	private NPC npc;
	private Location loc;
	
	public MovementCommand(Main plugin, String[] command) {
		// this.plugin = plugin;
		this.uuid = uc(command[0]);
		this.type = command[1];
		
		if (command.length >= 6) { // is it 6 or 7? does it include 0?
			this.x = command[2];
			this.y = command[3];
			this.z = command[4];
			
			this.pit = command[5];
			this.yaw = command[6];
		} else if (command.length <= 2){
			// this.sneaking = command[2];
		}
		
		this.npc = getUser(uuid).getNPC();
		this.loc = LocationUtils.toLoc("world", x, y, z, pit, yaw);
	}
	
	@Override
	public void move() {
		Location old = npc.getLocation();
		npc.moveTo(old, loc);
		npc.setLocation(loc);
	}
	
	@Override
	public void sneak() {
		npc.toggleState(NPCState.CROUCHED);
	}
	
	@Override
	public void handle() {
		if (type.equals("move")) 
			this.move();
		else if (type.equals("sneak")) 
			this.sneak();
	}

}
