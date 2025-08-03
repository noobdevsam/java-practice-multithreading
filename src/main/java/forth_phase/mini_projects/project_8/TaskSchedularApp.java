package forth_phase.mini_projects.project_8;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TaskSchedularApp {

    public static void main(String[] args) throws InterruptedException {
        var scheduler = Executors.newScheduledThreadPool(2);

        Runnable periodicTask = () -> System.out.println("Periodic task executed at = " + System.currentTimeMillis());
        Runnable delayedTask = () -> System.out.println("Delayed task executed at = " + System.currentTimeMillis());

        ScheduledFuture<?> periodicFuture = scheduler
                .scheduleAtFixedRate(periodicTask, 1, 2, TimeUnit.SECONDS);
        ScheduledFuture<?> delayedFuture = scheduler
                .schedule(delayedTask, 5, TimeUnit.SECONDS);

        Thread.sleep(10000);

        periodicFuture.cancel(true);
        scheduler.shutdown();
        scheduler.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Scheduler shutdown completed.");
    }

}
