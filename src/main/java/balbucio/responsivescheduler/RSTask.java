package balbucio.responsivescheduler;

import balbucio.responsivescheduler.event.impl.TaskProblemEvent;

public abstract class RSTask implements Runnable{

    public static RSTask fromRunnable(Runnable r){
        return new RSTask() {
            @Override
            public void run() {
                try {
                    r.run();
                } catch (Exception e){
                    e.printStackTrace();
                    setProblem(true);
                    setProblemID(-1);
                }
            }
        };
    }

    private boolean problem = false;
    private int problemID = 0;

    public boolean hasProblem() {
        return problem;
    }

    public void setProblem(boolean problem) {
        this.problem = problem;
        if(problem){
            ResponsiveScheduler.getInstance().getEventManager().sendEvent(new TaskProblemEvent(this));
        }
    }

    public int getProblemID() {
        return problemID;
    }

    public void setProblemID(int problemID) {
        this.problemID = problemID;
    }
}
