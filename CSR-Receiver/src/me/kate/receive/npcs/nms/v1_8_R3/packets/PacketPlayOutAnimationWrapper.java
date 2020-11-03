package me.kate.receive.npcs.nms.v1_8_R3.packets;

import com.comphenix.tinyprotocol.Reflection;

import me.kate.receive.npcs.api.state.NPCAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;

public class PacketPlayOutAnimationWrapper {

    public PacketPlayOutAnimation create(NPCAnimation npcAnimation, int entityId)  {
        PacketPlayOutAnimation packetPlayOutAnimation = new PacketPlayOutAnimation();

        Reflection.getField(packetPlayOutAnimation.getClass(), "a", int.class)
                .set(packetPlayOutAnimation, entityId);
        Reflection.getField(packetPlayOutAnimation.getClass(), "b", int.class)
                .set(packetPlayOutAnimation, npcAnimation.getId());

        return packetPlayOutAnimation;
    }
}
