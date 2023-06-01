import balbucio.responsivescheduler.event.Listener;
import balbucio.responsivescheduler.event.impl.AsyncTaskFinishedEvent;
import balbucio.responsivescheduler.event.impl.AsyncTaskStartedEvent;
import balbucio.responsivescheduler.event.impl.TaskFinishedEvent;
import balbucio.responsivescheduler.event.impl.TaskStartedEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import balbucio.responsivescheduler.ResponsiveScheduler;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SchedulerTest implements Listener {

    private ResponsiveScheduler scheduler;
    private TaskTest test = new TaskTest();
    @BeforeAll
    public void init(){
        scheduler = new ResponsiveScheduler();
        scheduler.getEventManager().registerListener(this);
    }

    @Test
    public void runTask(){
        scheduler.runTask(test);
    }

    @Test
    public void runRepeat(){
        scheduler.repeatTask(test, 0, 2000);
    }

    @Override
    public void asyncTaskStarted(AsyncTaskStartedEvent evt) {
    }

    @Override
    public void asyncTaskFinished(AsyncTaskFinishedEvent evt) {

    }

    @Override
    public void taskStatedEvent(TaskStartedEvent evt) {

    }

    @Override
    public void taskFinishedEvent(TaskFinishedEvent evt) {

    }
}
