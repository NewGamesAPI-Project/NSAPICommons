package net.cg360.nsapi.commons;

import net.cg360.nsapi.commons.event.NSEventManager;
import net.cg360.nsapi.commons.event.VanillaEvent;
import net.cg360.nsapi.commons.scheduler.NSSyncScheduler;
import net.cg360.nsapi.commons.scheduler.ServerSchedulerBridge;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * Spigot plugin for loading the Commons API on a spigot server.
 * It also may include spigot-specific common methods.
 */
public class SpigotCommons extends JavaPlugin implements Listener, ServerSchedulerBridge {

    public static SpigotCommons commons;

    protected CommonsAPI api;

    @Override
    public void onEnable() {
        try {
            commons = this;

            this.api = new CommonsAPI(
                    new SpigotLoggerInterface(),
                    this,
                    new NSSyncScheduler(this, 1),
                    new NSEventManager()
            );
            this.api.setAsPrimaryAPI();
            this.getServer().getPluginManager().registerEvents(this, this);

        } catch (Exception err){
            commons = null;

            err.printStackTrace();
            HandlerList.unregisterAll((Listener) this);
            // Just making sure everything is properly nulled.
        }
    }



    @Override
    public void registerSchedulerHook(NSSyncScheduler scheduler) {

    }

    @Override
    public void removeSchedulerHook(NSSyncScheduler scheduler) {

    }

    @Override
    public List<NSSyncScheduler> getHookedSchedulers() {
        return null;
    }



    @EventHandler // bukkit handler
    public <E extends Event> void onEvent(E event) {
        VanillaEvent<Event> vanillaEvent = new VanillaEvent<>(event);
        getApi().mainEventManager.call(vanillaEvent);
    }


    public static SpigotCommons get() { return commons; }
    public static boolean isCommonsPluginLoaded() { return commons != null; }

    /** @return the primary event manager. */
    public static CommonsAPI getApi() { return get().api; }
}
