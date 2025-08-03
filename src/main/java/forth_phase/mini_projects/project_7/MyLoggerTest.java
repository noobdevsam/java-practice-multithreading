package forth_phase.mini_projects.project_7;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * A test class for the `MyLogger` class.
 * Demonstrates the usage of the logger in a multithreaded environment.
 */
public class MyLoggerTest {

    /**
     * The main method to execute the logger test.
     * Creates multiple threads to log messages concurrently and ensures
     * all messages are written to the file before shutting down.
     *
     * @param args Command-line arguments (not used in this example).
     * @throws Exception If an error occurs during logger initialization or shutdown.
     */
    public static void main(String[] args) throws Exception {
        // Create a logger instance to write log messages to "app.log"
        var logger = new MyLogger("app.log");

        // Use a thread pool with 4 threads to simulate concurrent logging
        try (var executor = Executors.newFixedThreadPool(4)) {
            // Define a logging task for each thread
            Runnable task = () -> {
                for (int i = 0; i < 100; i++) {
                    // Log a message with the thread name and message index
                    logger.log(Thread.currentThread().getName() + " - Log message " + i);
                    try {
                        // Sleep for a short duration to simulate work
                        Thread.sleep(20);
                    } catch (InterruptedException ignored) {
                    }
                }
            };

            // Submit the logging task to the executor for each thread
            for (int i = 0; i < 4; i++) {
                executor.submit(task);
            }

            // Shut down the executor and wait for all tasks to complete
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);

            // Shut down the logger gracefully
            logger.shutdown();

            // Print a confirmation message to the terminal
            System.out.println("All log messages have been written to the file.");
        }
    }
}