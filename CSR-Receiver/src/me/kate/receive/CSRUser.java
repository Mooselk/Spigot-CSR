package me.kate.receive;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.kate.receive.npcs.api.NPC;

public class CSRUser {
	
	private static final Map<UUID, CSRUser> USERS = new HashMap<>();
	
	private UUID uuid;
	private NPC npc;
	
	public CSRUser() {
	}
	
	public CSRUser(String uuid, NPC npc) {
		this.uuid = UUID.fromString(uuid);
		this.npc = npc;
		
		USERS.put(this.uuid, this);
	}
	
	public static Map<UUID, CSRUser> getUsers() {
		return USERS;
	}
	
	public NPC getNPC() {
		return npc;
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public String getName() {
		return npc.getName();
	}
	
	public void destroy() {
		// CSRUser.getUsers().remove(uuid)
		// called by CreationCommand#destroy()
		npc.destroy();
	}
}
