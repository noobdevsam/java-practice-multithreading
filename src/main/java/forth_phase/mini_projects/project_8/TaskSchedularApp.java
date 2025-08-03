package forth_phase.mini_projects.project_8;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * A demonstration of task scheduling using a ScheduledThreadPoolExecutor.
 * This application schedules periodic and delayed tasks, manages their execution,
 * and shuts down the scheduler gracefully.
 */
public class TaskSchedularApp {

    /**
     * The main method to execute the task scheduler application.
     * Schedules a periodic task and a delayed task, waits for their execution,
     * and then shuts down the scheduler.
     *
     * @param args Command-line arguments (not used in this example).
     * @throws InterruptedException If the current thread is interrupted while sleeping or waiting for termination.
     */
    public static void main(String[] args) throws InterruptedException {
        // Create a scheduler with a thread pool of size 2
        var scheduler = Executors.newScheduledThreadPool(2);

        // Define a periodic task that prints the current timestamp
        Runnable periodicTask = () -> System.out.println("Periodic task executed at = " + System.currentTimeMillis());

        // Define a delayed task that prints the current timestamp
        Runnable delayedTask = () -> System.out.println("Delayed task executed at = " + System.currentTimeMillis());

        // Schedule the periodic task to run every 2 seconds after an initial delay of 1 second
        ScheduledFuture<?> periodicFuture = scheduler
                .scheduleAtFixedRate(periodicTask, 1, 2, TimeUnit.SECONDS);

        // Schedule the delayed task to run once after a delay of 5 seconds
        ScheduledFuture<?> delayedFuture = scheduler
                .schedule(delayedTask, 5, TimeUnit.SECONDS);

        // Allow tasks to execute for 10 seconds
        Thread.sleep(10000);

        // Cancel the periodic task and shut down the scheduler
        periodicFuture.cancel(true);
        scheduler.shutdown();
        scheduler.awaitTermination(5, TimeUnit.SECONDS);

        // Print a confirmation message after the scheduler is shut down
        System.out.println("Scheduler shutdown completed.");
    }

}