package balbucio.responsivescheduler.event.impl;

import balbucio.responsivescheduler.RSTask;
import balbucio.responsivescheduler.event.Event;

public class AsyncTaskStartedEvent implements Event {

    private RSTask task;
    private Thread thread;
    private boolean canceled = false;

    public AsyncTaskStartedEvent(RSTask task, Thread thread) {
        this.task = task;
        this.thread = thread;
    }

    public RSTask getTask() {
        return task;
    }

    public Thread getThread() {
        return thread;
    }
    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
