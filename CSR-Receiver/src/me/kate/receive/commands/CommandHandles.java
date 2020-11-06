package me.kate.receive.commands;

import java.util.UUID;

import me.kate.receive.CSRUser;

public abstract class CommandHandles {
	
	// should go in the CSRUser class
	// but I don't want to call a static method
	// when I don't need to
	public CSRUser getUser(UUID uuid) {
		CSRUser user = CSRUser.getUsers().get(uuid);
		if (user != null) {
			return user;
		}
		return null;
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
