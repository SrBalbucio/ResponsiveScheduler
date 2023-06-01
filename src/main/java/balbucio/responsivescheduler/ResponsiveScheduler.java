package balbucio.responsivescheduler;

import balbucio.responsivescheduler.event.RSEventManager;
import balbucio.responsivescheduler.event.impl.AsyncTaskFinishedEvent;
import balbucio.responsivescheduler.event.impl.AsyncTaskStartedEvent;
import balbucio.responsivescheduler.event.impl.TaskFinishedEvent;
import balbucio.responsivescheduler.event.impl.TaskStartedEvent;

import java.util.*;
import java.util.concurrent.*;

public class ResponsiveScheduler {

    private static ResponsiveScheduler instance;
    public static ResponsiveScheduler getInstance() {
        return instance;
    }

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
    private Map<RSTask, Thread> async = new HashMap<>();
    private Map<RSTask, Future<?>> tasks = new HashMap<>();
    private Timer task = new Timer();
    private RSEventManager eventManager;

    public ResponsiveScheduler(){
        if(instance != null){
            return;
        }
        instance = this;
        this.eventManager = new RSEventManager();
        this.task.schedule(new TimerTask() {
            @Override
            public void run() {
                for(RSTask t : async.keySet()){
                    Thread thread = async.get(t);
                    if(!thread.isAlive() || thread.isInterrupted()){
                        tasks.remove(t);
                        AsyncTaskFinishedEvent event = new AsyncTaskFinishedEvent(t, thread.isInterrupted(), t.hasProblem());
                        eventManager.sendEvent(event);
                    }
                }
                for(RSTask t : tasks.keySet()){
                    Future<?> f = tasks.get(t);
                    if(f.isDone() || f.isCancelled()){
                        TaskFinishedEvent event = new TaskFinishedEvent(t);
                        eventManager.sendEvent(event);
                    }
                }
            }
        }, 0, 2000);
    }

    public RSEventManager getEventManager() {
        return eventManager;
    }

    public void runTask(RSTask task){
        TaskStartedEvent event = new TaskStartedEvent(task);
        eventManager.sendEvent(event);
        if(event.isCanceled()) {
            return;
        }
        Future<?> future = executor.submit(task);
        tasks.put(task, future);
    }

    public void runAsyncTask(RSTask task){
        Thread thread = new Thread(task);
        AsyncTaskStartedEvent event = new AsyncTaskStartedEvent(task, thread);
        eventManager.sendEvent(event);
        if(event.isCanceled()){
            return;
        }
        thread.start();
        async.put(task, thread);
    }

    public void runTaskAfter(RSTask task, long period){
        TaskStartedEvent event = new TaskStartedEvent(task);
        eventManager.sendEvent(event);
        if(event.isCanceled()) {
            return;
        }
        Future<?> future = executor.schedule(task, period, TimeUnit.MILLISECONDS);
        tasks.put(task, future);
    }

    public void repeatTask(RSTask task, long delay, long period){
        TaskStartedEvent event = new TaskStartedEvent(task);
        eventManager.sendEvent(event);
        if(event.isCanceled()) {
            return;
        }
        Future<?> future = executor.scheduleAtFixedRate(task, delay, period, TimeUnit.MILLISECONDS);
        tasks.put(task, future);
    }

    public void cancelTask(RSTask task){
        async.forEach((t, th) -> {
            if(t == task){
                if(th.isAlive()) {
                    th.interrupt();
                }
            }
        });
        tasks.forEach((t, f) -> {
            if(t == task){
                if(!f.isDone() || !f.isCancelled()){
                    f.cancel(true);
                }
            }
        });
    }

}
