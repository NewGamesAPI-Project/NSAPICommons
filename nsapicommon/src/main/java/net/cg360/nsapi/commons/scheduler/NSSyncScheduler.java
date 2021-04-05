package net.cg360.nsapi.commons.scheduler;

import java.util.UUID;

/**
 * A synchronous scheduler that can be ran independently from the
 * server implementation a plugin is targeted for.
 */
public class NSSyncScheduler {

    private UUID schedulerID;

    protected int syncedTick;    // The server's tick whilst this has been hooked.
    protected int schedulerTick; // The scheduler's actual tick. This depends on the tickrate

    protected int tickDelay; // The amount of server ticks between each scheduler tick.

    protected ServerSchedulerBridge hook;
    protected boolean isRunning;

    public NSSyncScheduler(ServerSchedulerBridge schedulerHook, int tickDelay) {
        this.schedulerID = UUID.randomUUID();

        this.syncedTick = 0;
        this.schedulerTick = 0;
        this.tickDelay = Math.max(1, tickDelay);

        this.hook = schedulerHook;
        this.isRunning = false;
    }



    // -- Control --

    public boolean startScheduler() {
        if(!isRunning) {
            this.isRunning = true;
            if(hook != null) hook.registerSchedulerHook(this);
            return true;
        }
        return false;
    }

    /**
     * Removes scheduler's hook to the server tick whilst clearing the queue.
     * @return true if the scheduler was initially running and then stopped.
     */
    public boolean stopScheduler() {
        if(isRunning) {
            pauseScheduler();
            clearSchedulerTasks();
            return true;
        }
        return false;
    }

    /** Removes scheduler's hook to the server tick whilst clearing the queue */
    public void pauseScheduler() {
        this.isRunning = false;
        hook.removeSchedulerHook(this);
    }

    /** Cleares all the tasks queued in the scheduler. */
    public void clearSchedulerTasks() {
        // Cancel tasks and clear list.
    }


    // -- Ticking --

    /**
     * Ran to indicate a server tick has occurred, potentially triggering a server tick.
     * @return true is a scheduler tick is triggered as a result.
     */
    public boolean serverTick() {
        syncedTick++;

        // Check if synced is a multiple of the delay
        if((syncedTick % tickDelay) == 0) {
            schedulerTick();
            return true;
        }
        return false;
    }

    /** Executes a scheduler tick, running any tasks due to run on this tick. */
    public void schedulerTick() {
        if(isRunning) {
            schedulerTick++;

            //for(Task: new ArrayList(tasks)) {} -- To avoid stopping the scheduler from inside a task making it scream
        }
    }



    // -- Getters --

    /** The unique ID of this scheduler. */
    public UUID getSchedulerID() { return schedulerID; }

    /** @return the amount of server ticks this scheduler has been running for. */
    public int getSyncedTick() { return syncedTick; }
    /** @return the amount of ticks this scheduler has executed. */
    public int getSchedulerTick() { return schedulerTick; }
    /** @return the amount of server ticks between each scheduler tick. */
    public int getTickDelay() { return tickDelay; }



    // -- Setters --

    /** Sets the amount of server ticks that should pass between each scheduler tick. */
    public void setTickDelay(int tickDelay) {
        this.tickDelay = tickDelay;
    }
}
