package net.cg360.nsapi.commons.scheduler.task;

import java.util.UUID;

public abstract class NSTask {

    private UUID taskID;
    private boolean isCancelled;

    public NSTask() {
        this.taskID = UUID.randomUUID();
        this.isCancelled = false;
    }


    /** Executes the task. */
    public abstract void run();


    /** Sets the task as cancelled. */
    public final void cancel() { this.isCancelled = true; }

    /** @return the unique id of the task. */
    public UUID getTaskID() { return taskID; }
    /** @return true if the task has been cancelled. */
    public final boolean isCancelled() { return isCancelled; }
}
