package me.kate.receive.npcs.internal;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.kate.receive.npcs.api.state.NPCAnimation;
import me.kate.receive.npcs.api.state.NPCSlot;

interface NPCPacketHandler {

    void createPackets();

    void createPackets(Player player);

    void sendShowPackets(Player player);

    void sendHidePackets(Player player);

    void sendMetadataPacket(Player player);

    void sendEquipmentPacket(Player player, NPCSlot slot, boolean auto);

    void sendAnimationPacket(Player player, NPCAnimation animation);

    void sendHeadRotationPackets(Location location);
    
    default void sendEquipmentPackets(Player player) {
        for (NPCSlot slot : NPCSlot.values())
            sendEquipmentPacket(player, slot, true);
    }

	void sendMovePackets(Location from, Location to);
}
