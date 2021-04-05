package net.cg360.nsapi.commons;

import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import net.cg360.nsapi.commons.event.NSEventManager;
import net.cg360.nsapi.commons.event.VanillaEvent;

/**
 * Spigot plugin for loading the Commons API on a spigot server.
 * It also may include spigot-specific common methods.
 */
public class NukkitCommons extends PluginBase implements Listener {

    public static NukkitCommons commons;

    protected NSEventManager eventManager;



    @Override
    public void onEnable() {
        try {
            commons = this;

            new NukkitLoggerInterface().setAsMain();
            this.eventManager = new NSEventManager();
            this.eventManager.setAsPrimaryManager();

            this.getServer().getPluginManager().registerEvents(this, this);

        } catch (Exception err){
            commons = null;

            err.printStackTrace();
            HandlerList.unregisterAll((Listener) this);
            // Just making sure everything is properly nulled.
        }
    }



    @cn.nukkit.event.EventHandler // bukkit handler
    public <E extends Event> void onEvent(E event) {
        VanillaEvent<Event> vanillaEvent = new VanillaEvent<>(event);
        eventManager.call(vanillaEvent);
    }

    public static NukkitCommons get() { return commons; }
    public static boolean isCommonsPluginLoaded() { return commons != null; }

    /** @return the primary event manager. */
    public static NSEventManager getEventManager() { return get().eventManager; }
}
