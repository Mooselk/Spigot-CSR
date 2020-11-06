package me.kate.receive.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftSound;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock;

public class SoundUtils {

	public static void playBreakSound(Block block) {
        block.breakNaturally();
        for(Sound sound : Sound.values()) {
			try {
				Field field = CraftSound.class.getDeclaredField("sounds");
				field.setAccessible(true);
	            
	            String[] sounds = (String[]) field.get(null);
	            Method getBlock = CraftBlock.class.getDeclaredMethod("getNMSBlock");
	            getBlock.setAccessible(true);
	            Object nmsBlock = getBlock.invoke(block);
	            net.minecraft.server.v1_8_R3.Block nblock = (net.minecraft.server.v1_8_R3.Block) nmsBlock;
	 
	            if (nblock.stepSound.getBreakSound().equals(sounds[sound.ordinal()])) {
	                block.getWorld().playSound(block.getLocation(), sound,10, 10);
	            }
			} catch (NoSuchFieldException | SecurityException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}
