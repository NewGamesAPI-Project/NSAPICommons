package net.cg360.nsapi.commons.scheduler.task;

import net.cg360.nsapi.commons.util.Check;

public final class NSRunnableTask extends NSTask {

    private final Runnable taskRunnable;

    public NSRunnableTask(Runnable taskRunnable) {
        Check.nullParam(taskRunnable, "taskRunnable");
        this.taskRunnable = taskRunnable;
    }

    @Override
    public void run() {
        taskRunnable.run();
    }
}
