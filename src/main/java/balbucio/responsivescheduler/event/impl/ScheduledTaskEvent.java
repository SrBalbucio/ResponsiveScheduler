package balbucio.responsivescheduler.event.impl;

import balbucio.responsivescheduler.RSTask;
import balbucio.responsivescheduler.event.Event;

public class ScheduledTaskEvent implements Event {

    private RSTask task;
    private int hour, minute, second;
    private boolean canceled = false;

    public ScheduledTaskEvent(RSTask task, int hour, int minute, int second) {
        this.task = task;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public RSTask getTask() {
        return task;
    }

    public void setTask(RSTask task) {
        this.task = task;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
