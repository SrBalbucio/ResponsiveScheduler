package balbucio.responsivescheduler.event.impl;

import balbucio.responsivescheduler.RSTask;
import balbucio.responsivescheduler.event.Event;
import balbucio.responsivescheduler.ResponsiveScheduler;

public class TaskFinishedEvent implements Event {

    private RSTask task;

    public TaskFinishedEvent(RSTask task) {
        this.task = task;
    }

    public RSTask getTask() {
        return task;
    }

    public void rerun(){
        ResponsiveScheduler.runTask(task);
    }
}
