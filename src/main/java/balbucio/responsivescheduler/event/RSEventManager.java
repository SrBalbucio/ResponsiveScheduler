package balbucio.responsivescheduler.event;

import balbucio.responsivescheduler.event.impl.*;

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
        } else if(e instanceof TaskProblemEvent){
            listeners.forEach(l -> l.taskProblemEvent((TaskProblemEvent) e));
        }
    }
}
