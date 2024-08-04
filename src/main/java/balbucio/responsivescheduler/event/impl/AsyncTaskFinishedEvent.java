package balbucio.responsivescheduler.event.impl;

import balbucio.responsivescheduler.*;
import balbucio.responsivescheduler.event.Event;

public class AsyncTaskFinishedEvent implements Event {

    private RSTask task;
    private boolean interrupted = false;

    public AsyncTaskFinishedEvent(RSTask task, boolean interrupted) {
        this.task = task;
        this.interrupted = interrupted;
    }

    public RSTask getTask() {
        return task;
    }

    public boolean isInterrupted() {
        return interrupted;
    }

    public void rerun(){
        ResponsiveScheduler.getInstance().runAsyncTask(task);
    }
}
