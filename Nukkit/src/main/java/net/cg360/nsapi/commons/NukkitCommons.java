package net.cg360.nsapi.commons;

import cn.nukkit.plugin.PluginBase;

/**
 * Spigot plugin for loading the Commons API on a spigot server.
 * It also may include spigot-specific common methods.
 */
public class NukkitCommons extends PluginBase {

    public static NukkitCommons commons;

    @Override
    public void onEnable() {
        try {
            commons = this;
            new NukkitLoggerInterface().setAsMain();

        } catch (Exception err){
            commons = null;
            err.printStackTrace();
            // Just making sure everything is properly nulled.
        }
    }

    public static NukkitCommons get() { return commons; }
    public static boolean isCommonsLoaded() { return commons != null; }

}
