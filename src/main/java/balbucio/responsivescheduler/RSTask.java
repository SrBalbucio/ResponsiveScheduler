package balbucio.responsivescheduler;

public abstract class RSTask implements Runnable{

    private boolean problem = false;

    public boolean hasProblem() {
        return problem;
    }

    public void setProblem(boolean problem) {
        this.problem = problem;
    }
}
