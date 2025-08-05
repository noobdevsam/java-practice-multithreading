package fifth_phase.mini_project_3;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * The VirtualCompletableChain class demonstrates the use of virtual threads
 * and CompletableFuture to execute asynchronous tasks in a chain.
 */
public class VirtualCompletableChain {

    // Logger instance for logging messages
    private static final Logger logger = Logger.getLogger(VirtualCompletableChain.class.getName());

    /**
     * The main method initializes a virtual thread executor and executes
     * a chain of asynchronous tasks using CompletableFuture.
     *
     * @param args Command-line arguments (not used in this example)
     */
    public static void main(String[] args) {
        // Create a virtual thread executor
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {

            // Define a chain of asynchronous tasks
            CompletableFuture<Void> task = CompletableFuture
                    .supplyAsync(() -> {
                        log("Starting Task 1: Fetch data");
                        sleep(1000); // Simulate a delay
                        return "response-data"; // Simulated fetched data
                    }, executor)
                    .thenApplyAsync(data -> {
                        log("Step 2: Processing data");
                        sleep(500); // Simulate a delay
                        return data.toUpperCase(); // Processed data
                    }, executor)
                    .thenAcceptAsync(finalResult -> {
                        log("Step 3: Final output = " + finalResult);
                    }, executor)
                    .exceptionally(ex -> {
                        // Handle exceptions in the task chain
                        System.err.println("Something went wrong: " + ex.getMessage());
                        return null;
                    });

            // Wait for the task chain to complete
            task.join();
            // Shutdown the executor
            executor.shutdown();
        }
    }

    /**
     * Logs a message with the current thread information.
     *
     * @param message The message to log
     */
    private static void log(String message) {
        System.out.printf("[%s] %s%n", Thread.currentThread(), message);
    }

    /**
     * Pauses the current thread for the specified duration.
     *
     * @param ms The duration to sleep in milliseconds
     */
    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }
}