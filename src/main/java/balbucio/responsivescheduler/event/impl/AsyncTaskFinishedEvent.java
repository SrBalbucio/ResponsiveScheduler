package balbucio.responsivescheduler.event.impl;

import balbucio.responsivescheduler.*;
import balbucio.responsivescheduler.event.Event;
import balbucio.responsivescheduler.ResponsiveScheduler;

public class AsyncTaskFinishedEvent implements Event {

    private RSTask task;
    private boolean interrupted = false;
    private boolean problemOnExecution = false;

    public AsyncTaskFinishedEvent(RSTask task, boolean interrupted, boolean problemOnExecution) {
        this.task = task;
        this.interrupted = interrupted;
        this.problemOnExecution = problemOnExecution;
    }

    public RSTask getTask() {
        return task;
    }

    public boolean isInterrupted() {
        return interrupted;
    }

    public boolean isProblemOnExecution() {
        return problemOnExecution;
    }

    public void rerun(){
        ResponsiveScheduler.runAsyncTask(task);
    }
}
