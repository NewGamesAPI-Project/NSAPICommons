package net.cg360.nsapi.commons.event.filter;

import net.cg360.nsapi.commons.event.BaseEvent;
import net.cg360.nsapi.commons.event.VanillaEvent;
import net.cg360.nsapi.commons.event.type.EntityEvent;
import net.cg360.nsapi.commons.event.type.PlayerEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Taking in a whitelist, the filter checks if the event passed in
 * has any of the players on the whitelist mentioned within it. If it does,
 * it returns a true.
 */
public class FilterStaticPlayerWhitelist implements EventFilter {

    protected Player[] playerWhitelist;
    protected boolean onlyPlayerEvents;


    public FilterStaticPlayerWhitelist(Player[] playerWhitelist) { this(playerWhitelist, false); }
    public FilterStaticPlayerWhitelist(Player[] playerWhitelist, boolean onlyPlayerEvents) {
        this.playerWhitelist = playerWhitelist;
        this.onlyPlayerEvents = onlyPlayerEvents;
    }



    @Override
    public boolean checkEvent(BaseEvent eventIn) {
        boolean pass = true;
        // Should be true if event is not the right type.
        // Should be false if it's the right type but failed all checks.

        if(eventIn instanceof PlayerEvent) {
            pass = false; // A check has been done, make false unless found.
            Player check = ((PlayerEvent) eventIn).getPlayer();

            for(Player p: playerWhitelist) {

                if(p == check) {
                    return true; // Passed thus it's guaranteed true
                }
            }
        }

        if((!onlyPlayerEvents) && (eventIn instanceof EntityEvent)) {
            pass = false; // A check has been done, make false unless found.
            Entity check = ((EntityEvent) eventIn).getEntity();

            if(check instanceof Player) {

                for (Player p : playerWhitelist) {

                    if (p == check) {
                        pass = true;
                        break;
                    }
                }
            }
        }

        // Basically the same stuff but from the VanillaEvent wrapper
        if(eventIn instanceof VanillaEvent<?>) {
            org.bukkit.event.Event wrapped = ((VanillaEvent<?>) eventIn).getWrappedEvent();

            if(wrapped instanceof org.bukkit.event.player.PlayerEvent) {
                pass = false; // A check has been done, make false unless found.
                Player check = ((org.bukkit.event.player.PlayerEvent) wrapped).getPlayer();

                for(Player p: playerWhitelist) {

                    if(p == check) {
                        return true; // Passed thus it's guaranteed true
                    }
                }
            }

            if((!onlyPlayerEvents) && (wrapped instanceof org.bukkit.event.entity.EntityEvent)) {
                pass = false; // A check has been done, make false unless found.
                Entity check = ((org.bukkit.event.entity.EntityEvent) wrapped).getEntity();

                if(check instanceof Player) {

                    for (Player p : playerWhitelist) {

                        if (p == check) {
                            pass = true;
                            break;
                        }
                    }
                }
            }



        }
        return pass;
    }
}
