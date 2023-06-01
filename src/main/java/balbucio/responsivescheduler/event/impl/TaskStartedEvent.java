package balbucio.responsivescheduler.event.impl;

import balbucio.responsivescheduler.RSTask;
import balbucio.responsivescheduler.event.Event;

public class TaskStartedEvent implements Event {

    private RSTask task;
    private Thread thread;
    private boolean canceled = false;

    public TaskStartedEvent(RSTask task) {
        this.task = task;
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
