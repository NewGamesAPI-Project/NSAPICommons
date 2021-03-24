package net.cg360.nsapi.commons.event.filter;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import net.cg360.nsapi.commons.event.type.EntityEvent;
import net.cg360.nsapi.commons.event.type.PlayerEvent;
import net.cg360.nsapi.commons.event.Event;

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
    public boolean checkEvent(Event eventIn) {
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

        return pass;
    }
}
