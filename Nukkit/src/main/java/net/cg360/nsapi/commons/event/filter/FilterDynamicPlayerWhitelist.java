package net.cg360.nsapi.commons.event.filter;

import cn.nukkit.Player;
import net.cg360.nsapi.commons.event.Event;

import java.util.function.Supplier;

public class FilterDynamicPlayerWhitelist implements EventFilter {

    protected Supplier<Player[]> supplier;
    protected boolean onlyPlayerEvents;


    public FilterDynamicPlayerWhitelist(Supplier<Player[]> supplier) { this(supplier, false); }
    public FilterDynamicPlayerWhitelist(Supplier<Player[]> supplier, boolean onlyPlayerEvents) {
        this.supplier = supplier;
        this.onlyPlayerEvents = onlyPlayerEvents;
    }



    @Override
    public boolean checkEvent(Event eventIn) {
        return new FilterStaticPlayerWhitelist(supplier.get()).checkEvent(eventIn);
    }
}
