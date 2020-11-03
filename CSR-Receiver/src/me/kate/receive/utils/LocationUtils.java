package me.kate.receive.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtils {

	public static Location toLoc(String world, String x, String y, String z) {
		return new Location(
				Bukkit.getWorld("world"), 
				Double.valueOf(x),
				Double.valueOf(y), 
				Double.valueOf(z));
	}
	
	public static Location toLoc(String world, String x, String y, String z, String pitch, String yaw) {
		Location loc = new Location(
				Bukkit.getWorld("world"), 
				Double.valueOf(x),
				Double.valueOf(y), 
				Double.valueOf(z));
		
		loc.setPitch(Float.valueOf(pitch));
		loc.setYaw(Float.valueOf(yaw));
		
		return loc;
	}
	
}
