package balbucio.responsivescheduler;

import balbucio.responsivescheduler.event.RSEventManager;
import balbucio.responsivescheduler.event.impl.*;

import java.util.*;
import java.util.concurrent.*;

public class ResponsiveScheduler {
    private static ResponsiveScheduler instance;
    public static ResponsiveScheduler getInstance() {
        return instance;
    }

    public static void run(Runnable runnable){
        if(instance == null){
            instance = new ResponsiveScheduler();
        }
        instance.runTask(RSTask.fromRunnable(runnable));
    }

    public static void run(RSTask t){
        if(instance == null){
            instance = new ResponsiveScheduler();
        }
        instance.runTask(t);
    }

    public static void runAsync(RSTask t){
        if(instance == null){
            instance = new ResponsiveScheduler();
        }
        instance.runAsyncTask(t);
    }

    private ScheduledExecutorService executor;
    private Map<RSTask, Thread> async = new HashMap<>();
    private Map<RSTask, Future<?>> tasks = new HashMap<>();
    private RSEventManager eventManager;

    public ResponsiveScheduler(){
        this(10);
    }

    public ResponsiveScheduler(int poolSize){
        if(instance != null){
            return;
        }
        instance = this;
        executor = Executors.newScheduledThreadPool(poolSize);
        this.eventManager = new RSEventManager();
        executor.scheduleAtFixedRate(() -> {
            for(RSTask t : async.keySet()){
                Thread thread = async.get(t);
                if(!thread.isAlive() || thread.isInterrupted()){
                    async.remove(t);
                    AsyncTaskFinishedEvent event = new AsyncTaskFinishedEvent(t, thread.isInterrupted(), t.hasProblem());
                    eventManager.sendEvent(event);
                }
            }
            for(RSTask t : tasks.keySet()){
                Future<?> f = tasks.get(t);
                if(f.isDone() || f.isCancelled()){
                    tasks.remove(t);
                    TaskFinishedEvent event = new TaskFinishedEvent(t);
                    eventManager.sendEvent(event);
                }
            }
        }, 0, 2000, TimeUnit.MILLISECONDS);
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
        thread.setDaemon(true);
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

    public void repeatDailyTask(int hour, int minute, int second, RSTask task) {
        ScheduledTaskEvent event = new ScheduledTaskEvent(task, hour, minute, second);
        eventManager.sendEvent(event);
        if (event.isCanceled()) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        if (calendar.getTime().before(now)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        long delay = calendar.getTimeInMillis() - now.getTime();
        Future<?> future = executor.scheduleAtFixedRate(task, delay, 24 * 60 * 60 * 1000, TimeUnit.MILLISECONDS);
        tasks.put(task, future);

    }

    public void cancelTask(RSTask task){
        async.forEach((t, th) -> {
            if(t == task){
                if(th.isAlive()) {
                    th.interrupt();
                    AsyncTaskFinishedEvent event = new AsyncTaskFinishedEvent(t, th.isInterrupted(), t.hasProblem());
                    eventManager.sendEvent(event);
                    async.remove(t);
                }
            }
        });
        tasks.forEach((t, f) -> {
            if(t == task){
                if(!f.isDone() || !f.isCancelled()){
                    f.cancel(true);
                    TaskFinishedEvent event = new TaskFinishedEvent(t);
                    eventManager.sendEvent(event);
                    tasks.remove(t);
                }
            }
        });
    }

    public void cancelAllTasks(){
        async.forEach((t, th) -> {
            if(th.isAlive()) {
                th.interrupt();
                AsyncTaskFinishedEvent event = new AsyncTaskFinishedEvent(t, th.isInterrupted(), t.hasProblem());
                eventManager.sendEvent(event);

            }
        });
        tasks.forEach((t, f) -> {
            if(!f.isDone() || !f.isCancelled()){
                f.cancel(true);
                TaskFinishedEvent event = new TaskFinishedEvent(t);
                eventManager.sendEvent(event);
            }
        });
        async.clear();
        tasks.clear();
    }

    public boolean isActive(){
        return executor.isShutdown();
    }

    public void shutdown(){
        cancelAllTasks();
        executor.shutdownNow();
        eventManager.sendEvent(new ShutdownEvent());
    }

}
