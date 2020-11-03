package me.kate.receive.npcs.nms.v1_8_R3.packets;

import org.bukkit.Location;

import com.comphenix.tinyprotocol.Reflection;

import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;

public class PacketPlayOutEntityHeadRotationWrapper {
	
	public PacketPlayOutEntityHeadRotation create(Location location, int entityId) {
        PacketPlayOutEntityHeadRotation packetPlayOutEntityHeadRotation = new PacketPlayOutEntityHeadRotation();

        Reflection.getField(packetPlayOutEntityHeadRotation.getClass(), "a", int.class).
                set(packetPlayOutEntityHeadRotation, entityId);
        Reflection.getField(packetPlayOutEntityHeadRotation.getClass(), "b", byte.class)
                .set(packetPlayOutEntityHeadRotation, (byte) ((int) location.getYaw() * 256.0F / 360.0F));

        return packetPlayOutEntityHeadRotation;
    }
	
}
