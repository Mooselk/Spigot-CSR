package me.kate.receive.commands;

import java.util.UUID;

import me.kate.receive.CSRUser;
import me.kate.receive.CSRUserManager;

public abstract class CommandHandles {
	
	public CSRUser getUser(UUID uuid) {
		return CSRUserManager.getUser(uuid);
	}
	
	// looks cleaner than calling
	// UUID#fromString each time
	public UUID uc(String u) {
		return UUID.fromString(u);
	}
	
	public abstract void handle();
	
	public void interact() { }
		
	public void spawn() { }
		
	public void destory() { }

	public void createUser() { }
		
	public void destroyUser() { }
		
	public void move() { }
	
	public void sneak() { }
		
	public void placeBlock() { }
		
	public void breakBlock() { }
		
	public void hotbarMove() { }
		
	public void armorEquip() { }
	
}
