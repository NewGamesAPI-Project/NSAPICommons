package net.cg360.nsapi.commons;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Spigot plugin for loading the Commons API on a spigot server.
 * It also may include spigot-specific common methods.
 */
public class SpigotCommons extends JavaPlugin {

    public static SpigotCommons plg;

    @Override
    public void onEnable() {
        plg = this;
        new SpigotLoggerInterface().setAsMain();
    }

    public static SpigotCommons get() { return plg; }
}
