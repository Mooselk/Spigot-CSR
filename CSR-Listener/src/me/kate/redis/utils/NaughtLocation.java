package me.kate.redis.utils;

import org.bukkit.Location;

public class NaughtLocation {

	private double x, y, z;
	private float pitch, yaw;
	private Location location = null;
		
	public NaughtLocation(Location location) {
		this.location = location;
	}
	
	public NaughtLocation(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public NaughtLocation(double x, double y, double z, float pitch, float yaw) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public NaughtLocation convert() {
		final NaughtLocation n = new NaughtLocation(
				location.getX(), 
				location.getY(), 
				location.getZ());
		location = null;
		return n;
	}
	
	@Override
	public String toString() {
		return x 
				+ ", " + 
				y 
				+ ", " + 
				z 
				+ ", " + 
				pitch 
				+ ", " + 
				yaw;
	}
}
