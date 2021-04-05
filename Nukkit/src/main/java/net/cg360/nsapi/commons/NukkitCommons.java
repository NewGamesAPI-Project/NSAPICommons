package net.cg360.nsapi.commons;

import cn.nukkit.event.Event;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.Task;
import net.cg360.nsapi.commons.event.NSEventManager;
import net.cg360.nsapi.commons.event.VanillaEvent;
import net.cg360.nsapi.commons.scheduler.NSSyncScheduler;
import net.cg360.nsapi.commons.scheduler.ServerSchedulerBridge;

import java.util.ArrayList;
import java.util.List;

/**
 * Spigot plugin for loading the Commons API on a spigot server.
 * It also may include spigot-specific common methods.
 */
public class NukkitCommons extends PluginBase implements Listener, ServerSchedulerBridge {

    public static NukkitCommons commons;

    protected CommonsAPI api;
    protected ArrayList<NSSyncScheduler> hookedSchedulers;

    @Override
    public void onEnable() {
        try {
            commons = this;

            this.hookedSchedulers = new ArrayList<>();

            this.api = new CommonsAPI(
                    new NukkitLoggerInterface(),
                    this,
                    new NSSyncScheduler(this, 1),
                    new NSEventManager()
            );
            this.api.setAsPrimaryAPI();
            this.getServer().getPluginManager().registerEvents(this, this);
            this.getServer().getScheduler().scheduleDelayedRepeatingTask(new Task() {

                @Override
                public void onRun(int currentTick) {
                    if(isCommonsPluginLoaded()) {
                        for (NSSyncScheduler s : new ArrayList<>(getHookedSchedulers())) s.serverTick();

                    } else {
                        this.cancel();
                    }
                }

            }, 1, 1);

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



    @EventHandler // nukkit handler
    public <E extends Event> void onEvent(E event) {
        VanillaEvent<Event> vanillaEvent = new VanillaEvent<>(event);
        getApi().mainEventManager.call(vanillaEvent);
    }

    public static NukkitCommons get() { return commons; }
    public static boolean isCommonsPluginLoaded() { return commons != null; }

    /** @return the primary event manager. */
    public static CommonsAPI getApi() { return get().api; }
}
