import balbucio.responsivescheduler.RSTask;
import balbucio.responsivescheduler.ResponsiveScheduler;

public class TaskTest implements RSTask {

    @Override
    public void run(ResponsiveScheduler rs) throws Exception {
        System.out.println("Olá, estou vivo!");
    }
}
