package fifth_phase.mini_project_2;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * VirtualSchedular demonstrates the use of virtual threads and scheduled tasks in Java.
 * It schedules periodic tasks to run at fixed intervals and uses virtual threads for task execution.
 */
public class VirtualSchedular {

    /**
     * The main method initializes the scheduler and executor, schedules tasks, and sets up a shutdown hook.
     *
     * @param args Command-line arguments (not used in this program).
     */
    public static void main(String[] args) {

        // Create a single-threaded scheduled executor for periodic tasks.
        var scheduler = Executors.newSingleThreadScheduledExecutor();

        // Create a virtual thread per task executor for concurrent task execution.
        var executor = Executors.newVirtualThreadPerTaskExecutor();

        // Define a periodic task to be executed.
        Runnable periodicTask = () -> {
            System.out.println("Running periodic task at: " + LocalDateTime.now() + " | Thread: " + Thread.currentThread());

            try {
                // Simulate task execution with a sleep.
                Thread.sleep(1000);
            } catch (Exception e) {
                System.err.println("Error executing task: " + e.getMessage());
            }
        };

        // Submit the periodic task to the executor and schedule it at fixed intervals.
        for (int i = 0; i < 10; i++) {
            executor.submit(periodicTask); // Submit task to virtual thread executor.
            scheduler.scheduleAtFixedRate(periodicTask, 0, 3, TimeUnit.SECONDS); // Schedule task at fixed intervals.
        }

        // Add a shutdown hook to clean up resources when the application terminates.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            scheduler.shutdown(); // Shut down the scheduler.
            executor.shutdown(); // Shut down the executor.
            System.out.println("Scheduler terminated and executor shutdown.");
        }));

        // Close the scheduler and executor (not necessary as shutdown is already called).
        scheduler.close();
        executor.close();

    }

}
