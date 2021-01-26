package net.cg360.nsapi.commons;

import org.bukkit.plugin.java.JavaPlugin;

public class SpigotCommons extends JavaPlugin {

    public static SpigotCommons plg;

    @Override
    public void onEnable() {
        plg = this;
        new SpigotLoggerInterface().setAsMain();
    }

    public static SpigotCommons get() { return plg; }
}
