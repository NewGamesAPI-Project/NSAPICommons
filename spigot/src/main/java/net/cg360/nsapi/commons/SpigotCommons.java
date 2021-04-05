package net.cg360.nsapi.commons;

import net.cg360.nsapi.commons.event.NSEventManager;
import net.cg360.nsapi.commons.event.VanillaEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Spigot plugin for loading the Commons API on a spigot server.
 * It also may include spigot-specific common methods.
 */
public class SpigotCommons extends JavaPlugin implements Listener {

    public static SpigotCommons commons;

    protected NSEventManager eventManager;



    @Override
    public void onEnable() {
        try {
            commons = this;

            new SpigotLoggerInterface().setAsMain();
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



    @org.bukkit.event.EventHandler // bukkit handler
    public <E extends Event> void onEvent(E event) {
        VanillaEvent<Event> vanillaEvent = new VanillaEvent<>(event);
        eventManager.call(vanillaEvent);
    }


    public static SpigotCommons get() { return commons; }
    public static boolean isCommonsPluginLoaded() { return commons != null; }

    /** @return the primary event manager. */
    public static NSEventManager getEventManager() { return get().eventManager; }
}
