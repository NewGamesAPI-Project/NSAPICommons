package net.cg360.nsapi.commons.scheduler;

import java.util.List;

public interface ServerSchedulerBridge {

    /** Adds the provided scheduler to the server scheduler tick list, allowing it to function. */
    void registerSchedulerHook(NSSyncScheduler scheduler);

    /** Removes the provided scheduler from the server tick list, effectively stopping it. */
    void removeSchedulerHook(NSSyncScheduler scheduler);

    /** A list of all the hooked schedulers. */
    List<NSSyncScheduler> getHookedSchedulers();

}
