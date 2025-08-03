package fifth_phase;

import java.time.Duration;
import java.time.Instant;

/**
 * A demonstration of virtual threads in Java.
 * This program creates and executes a large number of tasks using virtual threads,
 * measures the time taken for execution, and prints the results to the terminal.
 */
public class VirtualThreadDemo {

    /**
     * The main method to execute the virtual thread demo.
     * Creates 10,000 virtual threads to perform a simple task, measures the execution time,
     * and waits for all threads to complete before exiting.
     *
     * @param args Command-line arguments (not used in this example).
     */
    public static void main(String[] args) {
        // Number of tasks to be executed
        var numberOfTasks = 10_000;

        // Record the start time of execution
        var start = Instant.now();

        // Create and start virtual threads to execute tasks
        for (int i = 0; i < numberOfTasks; i++) {
            Thread.startVirtualThread(() -> {
                try {
                    // Simulate task execution by sleeping for 100 milliseconds
                    Thread.sleep(100);
                    // Print the thread executing the task
                    System.out.println("Executed by = " + Thread.currentThread());
                } catch (InterruptedException e) {
                    e.getMessage(); // Handle interruption
                }
            });
        }

        // Record the end time of execution
        var end = Instant.now();

        // Calculate the duration of execution
        var duration = Duration.between(start, end);

        // Print the time taken to execute all tasks
        System.out.println("Time taken to execute " + numberOfTasks + " tasks = " + duration.toMillis() + " ms");

        try {
            // Wait for all virtual threads to complete
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}