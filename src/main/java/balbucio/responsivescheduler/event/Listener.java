package balbucio.responsivescheduler.event;

import balbucio.responsivescheduler.event.impl.AsyncTaskFinishedEvent;
import balbucio.responsivescheduler.event.impl.AsyncTaskStartedEvent;
import balbucio.responsivescheduler.event.impl.TaskFinishedEvent;
import balbucio.responsivescheduler.event.impl.TaskStartedEvent;

public interface Listener {

    void asyncTaskStarted(AsyncTaskStartedEvent evt);
    void asyncTaskFinished(AsyncTaskFinishedEvent evt);
    void taskStatedEvent(TaskStartedEvent evt);
    void taskFinishedEvent(TaskFinishedEvent evt);
}
