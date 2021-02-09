package net.cg360.nsapi.commons;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Spigot plugin for loading the Commons API on a spigot server.
 * It also may include spigot-specific common methods.
 */
public class SpigotCommons extends JavaPlugin {

    public static SpigotCommons commons;

    @Override
    public void onEnable() {
        try {
            commons = this;
            new SpigotLoggerInterface().setAsMain();

        } catch (Exception err){
            commons = null;
            err.printStackTrace();
            // Just making sure everything is properly nulled.
        }
    }

    public static SpigotCommons get() { return commons; }
    public static boolean isCommonsLoaded() { return commons != null; }

}
