package me.kate.receive.npcs;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.kate.receive.npcs.NPCLibOptions.MovementHandling;
import me.kate.receive.npcs.api.NPC;
import me.kate.receive.npcs.api.util.Logger;
import me.kate.receive.npcs.listeners.ChunkListener;
import me.kate.receive.npcs.listeners.PacketListener;
import me.kate.receive.npcs.listeners.PeriodicMoveListener;
import me.kate.receive.npcs.listeners.PlayerListener;
import me.kate.receive.npcs.listeners.PlayerMoveEventListener;


public final class NPCLib {

    private final JavaPlugin plugin;
    private final Logger logger;
    private final Class<?> npcClass;

    private double autoHideDistance = 50.0;

    private NPCLib(JavaPlugin plugin, MovementHandling moveHandling) {
        this.plugin = plugin;
        this.logger = new Logger("NPC");

        String versionName = plugin.getServer().getClass().getPackage().getName().split("\\.")[3];

        Class<?> npcClass = null;

        try {
            npcClass = Class.forName("me.kate.receive.npcs.nms." + versionName + ".NPC_" + versionName);
        } catch (ClassNotFoundException exception) {
            // Version not supported, error below.
        }

        this.npcClass = npcClass;

        if (npcClass == null) {
            logger.severe("Failed to initiate. Your server's version (" + versionName + ") is not supported");
            return;
        }

        PluginManager pluginManager = plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerListener(this), plugin);
        pluginManager.registerEvents(new ChunkListener(this), plugin);

        if (moveHandling.usePme) {
            pluginManager.registerEvents(new PlayerMoveEventListener(), plugin);
        } else {
            pluginManager.registerEvents(new PeriodicMoveListener(this, moveHandling.updateInterval), plugin);
        }

        // Boot the according packet listener.
        new PacketListener().start(this);

        logger.info("Enabled for Minecraft " + versionName);
    }

    public NPCLib(JavaPlugin plugin) {
        this(plugin, MovementHandling.playerMoveEvent());
    }

    public NPCLib(JavaPlugin plugin, NPCLibOptions options) {
        this(plugin, options.moveHandling);
    }

    /**
     * @return The JavaPlugin instance.
     */
    public JavaPlugin getPlugin() {
        return plugin;
    }

    /**
     * Set a new value for the auto-hide distance.
     * A recommended value (and default) is 50 blocks.
     *
     * @param autoHideDistance The new value.
     */
    public void setAutoHideDistance(double autoHideDistance) {
        this.autoHideDistance = autoHideDistance;
    }

    /**
     * @return The auto-hide distance.
     */
    public double getAutoHideDistance() {
        return autoHideDistance;
    }

    /**
     * @return The logger NPCLib uses.
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Create a new non-player character (NPC).
     *
     * @param text The text you want to sendShowPackets above the NPC (null = no text).
     * @return The NPC object you may use to sendShowPackets it to players.
     */
    public NPC createNPC(String name) {
        try {
            return (NPC) npcClass.getConstructors()[0].newInstance(this, name);
        } catch (Exception exception) {
            logger.warning("Failed to create NPC. Please report the following stacktrace message", exception);
        }

        return null;
    }

    /**
     * Create a new non-player character (NPC).
     *
     * @return The NPC object you may use to sendShowPackets it to players.
     */
    public NPC createNPC() {
        return createNPC(null);
    }
}
