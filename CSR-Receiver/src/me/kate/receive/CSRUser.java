package me.kate.receive;

import java.util.UUID;

import me.kate.receive.npcs.api.NPC;

public class CSRUser {
	
	private UUID uuid;
	private NPC npc;
	
	public CSRUser(UUID uuid, NPC npc) {
		this.uuid = uuid;
		this.npc = npc;
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
	
}
