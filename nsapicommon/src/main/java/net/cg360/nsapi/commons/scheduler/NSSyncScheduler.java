package net.cg360.nsapi.commons.scheduler;

import net.cg360.nsapi.commons.scheduler.task.NSTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * A synchronous scheduler that can be ran independently from the
 * server implementation a plugin is targeted for.
 */
public class NSSyncScheduler {

    private UUID schedulerID;

    protected long syncedTick;    // The server's tick whilst this has been hooked.
    protected long schedulerTick; // The scheduler's actual tick. This depends on the tickrate

    protected int tickDelay; // The amount of server ticks between each scheduler tick.

    protected ServerSchedulerBridge hook;
    protected ArrayList<NSTaskEntry> schedulerTasks;
    protected boolean isRunning;

    public NSSyncScheduler(ServerSchedulerBridge schedulerHook, int tickDelay) {
        this.schedulerID = UUID.randomUUID();

        this.syncedTick = 0;
        this.schedulerTick = 0;
        this.tickDelay = Math.max(1, tickDelay);

        this.hook = schedulerHook;
        this.schedulerTasks = new ArrayList<>();
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
        if(hook != null) hook.removeSchedulerHook(this);
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


            // To avoid stopping the scheduler from inside a task making it scream, use ArrayList wrapping
            for(NSTaskEntry task: new ArrayList<>(schedulerTasks)) {


                long taskTick = task.getNextTick();
                if(taskTick == schedulerTick) {
                    task.getTask().run();

                    // Not cancelled + it's a repeat task.
                    if(task.isRepeating() && (!task.getTask().isCancelled())) {
                        long targetTick = taskTick + task.getRepeatInterval();

                        NSTaskEntry newTask = new NSTaskEntry(task.getTask(), task.getRepeatInterval(), targetTick);
                        queueNSTaskEntry(newTask);
                    }
                } else if(taskTick > schedulerTick) {
                    // Upcoming task, do not remove from queue! :)
                    break;
                }

                schedulerTasks.remove(0); // Operate like a queue.
                // Remove from the start as long as it isn't an upcoming task.
                // If a task is somehow scheduled *before* the current tick, it should
                // be removed anyway.
            }
            schedulerTick++; // Tick after so tasks can be ran without a delay.
        }
    }


    // -- Task Control --

    protected void queueNSTaskEntry(NSTaskEntry entry){
        if(entry.getNextTick() >= schedulerTick) throw new IllegalStateException("Task cannot be scheduled before the current tick.");

        int size = schedulerTasks.size();
        for(int i = 0; i < size; i++) {
            // Entry belongs before task? Insert into it's position
            if(schedulerTasks.get(i).getNextTick() < entry.getNextTick()) {
                this.schedulerTasks.add(i, entry);
                return;
            }
        }

        // Not added in loop. Append to the end.
        this.schedulerTasks.add(entry);
    }



    // -- Getters --

    /** The unique ID of this scheduler. */
    public UUID getSchedulerID() { return schedulerID; }

    /** @return the amount of server ticks this scheduler has been running for. */
    public long getSyncedTick() { return syncedTick; }
    /** @return the amount of ticks this scheduler has executed. */
    public long getSchedulerTick() { return schedulerTick; }
    /** @return the amount of server ticks between each scheduler tick. */
    public int getTickDelay() { return tickDelay; }



    // -- Setters --

    /** Sets the amount of server ticks that should pass between each scheduler tick. */
    public void setTickDelay(int tickDelay) {
        this.tickDelay = tickDelay;
    }
}
