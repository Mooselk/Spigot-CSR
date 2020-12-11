package me.kate.receive;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CSRUserManager {

	private static final Map<UUID, CSRUser> USERS = new HashMap<>();
	
	public static Map<UUID, CSRUser> getUsers() {
		return USERS;
	}
	
	public static void addUser(CSRUser user) {
		USERS.put(user.getUUID(), user);
	}
	
	public static void removeUser(CSRUser user) {
		if (user != null) {
			USERS.remove(user.getUUID());
		}
	}
	
	public static void removeUser(UUID user) {
		if (user != null) {
			USERS.remove(user);
		}
	}
	
	public static CSRUser getUser(UUID uuid) {
		CSRUser user = USERS.get(uuid);
		if (user != null)
			return user;
		return null;
	}
	
	private CSRUserManager() {
        throw new SecurityException("You cannot initialize this class.");
    }
}
