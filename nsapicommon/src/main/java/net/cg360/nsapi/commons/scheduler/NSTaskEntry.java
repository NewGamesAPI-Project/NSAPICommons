package net.cg360.nsapi.commons.scheduler;

import net.cg360.nsapi.commons.Check;
import net.cg360.nsapi.commons.scheduler.task.NSTask;

public final class NSTaskEntry {

    private final NSTask task;
    private final int repeatInterval;
    private final long nextTick;

    protected NSTaskEntry(NSTask task, int repeatInterval, long nextTick) {
        Check.nullParam(task, "task");

        this.task = task;
        this.repeatInterval = repeatInterval;
        this.nextTick = nextTick;
    }

    public NSTask getTask() { return task; }
    public boolean isRepeating() { return repeatInterval > 0; }

    public int getRepeatInterval() { return repeatInterval; }
    public long getNextTick() { return nextTick; }

}
