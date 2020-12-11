package me.kate.receive.npcs.nms.v1_8_R3;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import me.kate.receive.npcs.NPCLib;
import me.kate.receive.npcs.api.skin.Skin;
import me.kate.receive.npcs.api.state.NPCAnimation;
import me.kate.receive.npcs.api.state.NPCSlot;
import me.kate.receive.npcs.internal.NPCBase;
import me.kate.receive.npcs.nms.v1_8_R3.packets.PacketPlayOutAnimationWrapper;
import me.kate.receive.npcs.nms.v1_8_R3.packets.PacketPlayOutEntityHeadRotationWrapper;
import me.kate.receive.npcs.nms.v1_8_R3.packets.PacketPlayOutEntityMetadataWrapper;
import me.kate.receive.npcs.nms.v1_8_R3.packets.PacketPlayOutNamedEntitySpawnWrapper;
import me.kate.receive.npcs.nms.v1_8_R3.packets.PacketPlayOutPlayerInfoWrapper;
import me.kate.receive.npcs.nms.v1_8_R3.packets.PacketPlayOutScoreboardTeamWrapper;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class NPC_v1_8_R3 extends NPCBase {

    private PacketPlayOutNamedEntitySpawn packetPlayOutNamedEntitySpawn;
    private PacketPlayOutScoreboardTeam packetPlayOutScoreboardTeamRegister;
    private PacketPlayOutPlayerInfo packetPlayOutPlayerInfoAdd, packetPlayOutPlayerInfoRemove;
    private PacketPlayOutEntityHeadRotation packetPlayOutEntityHeadRotation;
    private PacketPlayOutEntityDestroy packetPlayOutEntityDestroy;

    public NPC_v1_8_R3(NPCLib instance, String name) {
        super(instance, name);
    }

    @Override
    public void createPackets() {
        Bukkit.getOnlinePlayers().forEach(this::createPackets);
    }

    @Override
    public void createPackets(Player player) {

        PacketPlayOutPlayerInfoWrapper packetPlayOutPlayerInfoWrapper = new PacketPlayOutPlayerInfoWrapper();

        // Packets for spawning the NPC:
        this.packetPlayOutScoreboardTeamRegister = new PacketPlayOutScoreboardTeamWrapper()
                .createRegisterTeam(name); // First packet to send.

        this.packetPlayOutPlayerInfoAdd = packetPlayOutPlayerInfoWrapper
                .create(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, gameProfile, name); // Second packet to send.

        this.packetPlayOutNamedEntitySpawn = new PacketPlayOutNamedEntitySpawnWrapper()
                .create(uuid, location, entityId); // Third packet to send.

        this.packetPlayOutEntityHeadRotation = new PacketPlayOutEntityHeadRotationWrapper()
                .create(location, entityId); // Fourth packet to send.

        this.packetPlayOutPlayerInfoRemove = packetPlayOutPlayerInfoWrapper.create(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, gameProfile, name); // Fifth packet to send (delayed).

        // Packet for destroying the NPC:
        this.packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(entityId); // First packet to send.
    }

    @Override
    public void sendShowPackets(Player player) {
        PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;

        if (hasTeamRegistered.add(player.getUniqueId()))
            playerConnection.sendPacket(packetPlayOutScoreboardTeamRegister);
        playerConnection.sendPacket(packetPlayOutPlayerInfoAdd);
        playerConnection.sendPacket(packetPlayOutNamedEntitySpawn);
        playerConnection.sendPacket(packetPlayOutEntityHeadRotation);

        // Removing the player info after 10 seconds.
        Bukkit.getScheduler().runTaskLater(instance.getPlugin(), () ->
                playerConnection.sendPacket(packetPlayOutPlayerInfoRemove), 200);
    }

    @Override
    public void sendHidePackets(Player player) {
        PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
        playerConnection.sendPacket(packetPlayOutEntityDestroy);
        playerConnection.sendPacket(packetPlayOutPlayerInfoRemove);
    }

    @Override
    public void sendMetadataPacket(Player player) {
        PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
        PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadataWrapper().create(activeStates, entityId);

        playerConnection.sendPacket(packet);
    }

    @Override
    public void sendEquipmentPacket(Player player, NPCSlot slot, boolean auto) {
        PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;

        if (slot == NPCSlot.OFFHAND) {
            if (!auto) {
                throw new UnsupportedOperationException("Offhand is not supported on servers below 1.9");
            }
            return;
        }

        ItemStack item = getItem(slot);

        PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(entityId, slot.getSlot(), CraftItemStack.asNMSCopy(item));
        playerConnection.sendPacket(packet);
    }

    @Override
    public void sendAnimationPacket(Player player, NPCAnimation animation) {
        if(animation == NPCAnimation.SWING_OFFHAND) {
            throw new IllegalArgumentException("Offhand Swing Animations are only available on 1.9 and up.");
        }

        PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;

        PacketPlayOutAnimation packet = new PacketPlayOutAnimationWrapper().create(animation, entityId);
        playerConnection.sendPacket(packet);
    }

    @Override
    public void updateSkin(Skin skin) {
        GameProfile newProfile = new GameProfile(uuid, name);
        newProfile.getProperties().get("textures").clear();
        newProfile.getProperties().put("textures", new Property("textures", skin.getValue(), skin.getSignature()));
        this.packetPlayOutPlayerInfoAdd = new PacketPlayOutPlayerInfoWrapper().create(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, newProfile, name);
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
            playerConnection.sendPacket(packetPlayOutPlayerInfoRemove);
            playerConnection.sendPacket(packetPlayOutEntityDestroy);
            playerConnection.sendPacket(packetPlayOutPlayerInfoAdd);
            playerConnection.sendPacket(packetPlayOutNamedEntitySpawn);
        }
    }
    
    @Override
    public void sendHeadRotationPackets(Location location) {
    	for (Player player : Bukkit.getOnlinePlayers()) {    		
    		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
    		
    		Location npcLocation = getLocation();
    		Vector dirBetweenLocations = location.toVector().subtract(npcLocation.toVector());
    		
            npcLocation.setDirection(dirBetweenLocations);
            
            //float yaw = location.getYaw();
            //float pitch = location.getPitch();
            
            connection.sendPacket(new PacketPlayOutEntity.PacketPlayOutEntityLook(entityId, (byte) ((int) location.getYaw() * 256.0F / 360.0F), (byte) ((int) location.getPitch() * 256.0F / 360.0F), false));
            connection.sendPacket(new PacketPlayOutEntityHeadRotationWrapper().create(npcLocation, entityId));
    	}
    }
    
    @Override
    public void sendMovePackets(Location from, Location to) {
    	for (Player player : Bukkit.getOnlinePlayers()) {
    		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
    		double x = from.getX();
        	double y = from.getY();
        	double z = from.getZ();

        	if (to != null) {
            	x = (to.getX() * 32 - from.getX() * 32);
            	y = (to.getY() * 32 - from.getY() * 32);
            	z = (to.getZ() * 32 - from.getZ() * 32);
        	}
        	
        	String[] splitter = String.valueOf(y).split("\\.");
        	
        	if (splitter[1].startsWith("0")) {
        		y = Math.round(y);
        	}

        	connection.sendPacket(new PacketPlayOutEntity.PacketPlayOutRelEntityMove(entityId, (byte) Math.round(x), (byte) Math.round(y), (byte) Math.round(z), false));
        	connection.sendPacket(new PacketPlayOutEntity.PacketPlayOutEntityLook(entityId, (byte) ((int) location.getYaw() * 256.0F / 360.0F), (byte) ((int) location.getPitch() * 256.0F / 360.0F), false));
            connection.sendPacket(new PacketPlayOutEntityHeadRotationWrapper().create(from, entityId));
    	}
    }
}
