package balbucio.responsivescheduler.event.impl;

import balbucio.responsivescheduler.RSTask;
import balbucio.responsivescheduler.event.Event;

public class TaskProblemEvent implements Event {

    private RSTask task;
    private int problemID;

    public TaskProblemEvent(RSTask task) {
        this.task = task;
        this.problemID = task.getProblemID();
    }

    public RSTask getTask() {
        return task;
    }

    public void setTask(RSTask task) {
        this.task = task;
    }

    public int getProblemID() {
        return problemID;
    }

    public void setProblemID(int problemID) {
        this.problemID = problemID;
    }
}
