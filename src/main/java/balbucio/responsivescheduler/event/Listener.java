package balbucio.responsivescheduler.event;

import balbucio.responsivescheduler.event.impl.*;

public interface Listener {

    void asyncTaskStarted(AsyncTaskStartedEvent evt);
    void asyncTaskFinished(AsyncTaskFinishedEvent evt);
    void taskStatedEvent(TaskStartedEvent evt);
    void taskFinishedEvent(TaskFinishedEvent evt);
    void scheduledTask(ScheduledTaskEvent evt);
    void shutdown(ShutdownEvent evt);
}
