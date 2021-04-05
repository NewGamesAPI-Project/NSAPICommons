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
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Spigot plugin for loading the Commons API on a spigot server.
 * It also may include spigot-specific common methods.
 */
public class SpigotCommons extends JavaPlugin implements Listener, ServerSchedulerBridge {

    public static SpigotCommons commons;

    protected CommonsAPI api;
    protected ArrayList<NSSyncScheduler> hookedSchedulers;

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
            new BukkitRunnable() {

                @Override
                public void run() {
                    if(isCommonsPluginLoaded()) {
                        for (NSSyncScheduler s : new ArrayList<>(getHookedSchedulers())) s.serverTick();
                    } else { this.cancel(); }
                }

            }.runTaskTimer(this, 1, 1);

        } catch (Exception err){
            commons = null;

            err.printStackTrace();
            HandlerList.unregisterAll((Listener) this);
            // Just making sure everything is properly nulled.
        }
    }

    @Override
    public void onDisable() {
        this.api = null;
        HandlerList.unregisterAll((Listener) this);

        // Unregister all schedulers.
        for(NSSyncScheduler s: new ArrayList<>(getHookedSchedulers())) {
            registerSchedulerHook(s);
        }
    }



    @Override
    public void registerSchedulerHook(NSSyncScheduler scheduler) {
        if(!hookedSchedulers.contains(scheduler)) hookedSchedulers.add(scheduler);
    }

    @Override
    public void removeSchedulerHook(NSSyncScheduler scheduler) {
        hookedSchedulers.remove(scheduler);
    }

    @Override
    public List<NSSyncScheduler> getHookedSchedulers() {
        return new ArrayList<>(hookedSchedulers);
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
