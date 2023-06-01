package balbucio.responsivescheduler.event;

import balbucio.responsivescheduler.event.impl.AsyncTaskFinishedEvent;
import balbucio.responsivescheduler.event.impl.AsyncTaskStartedEvent;
import balbucio.responsivescheduler.event.impl.TaskFinishedEvent;
import balbucio.responsivescheduler.event.impl.TaskStartedEvent;

import java.util.ArrayList;
import java.util.List;

public class RSEventManager {

    private List<Listener> listeners = new ArrayList<>();

    public RSEventManager(){
    }

    public void registerListener(Listener listener){
        listeners.add(listener);
    }

    public void sendEvent(Event e){
        if(e instanceof AsyncTaskStartedEvent){
            listeners.forEach(l -> l.asyncTaskStarted((AsyncTaskStartedEvent) e));
        } else if(e instanceof AsyncTaskFinishedEvent){
            listeners.forEach(l -> l.asyncTaskFinished((AsyncTaskFinishedEvent) e));
        } else if(e instanceof TaskStartedEvent){
            listeners.forEach(l -> l.taskStatedEvent((TaskStartedEvent) e));
        } else if(e instanceof TaskFinishedEvent){
            listeners.forEach(l -> l.taskFinishedEvent((TaskFinishedEvent) e));
        }
    }
}
