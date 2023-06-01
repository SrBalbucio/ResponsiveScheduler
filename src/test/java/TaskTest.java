import balbucio.responsivescheduler.RSTask;

public class TaskTest extends RSTask {
    @Override
    public void run() {
        System.out.println("Estou rodando!");
    }
}
