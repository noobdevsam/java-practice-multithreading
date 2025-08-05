package fifth_phase.mini_project_3;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

/**
 * The ParallelVirtualCompletable class demonstrates the use of virtual threads
 * and CompletableFuture to execute multiple asynchronous tasks in parallel.
 */
public class ParallelVirtualCompletable {

    /**
     * The main method initializes a virtual thread executor and executes
     * a list of asynchronous tasks in parallel using CompletableFuture.
     *
     * @param args Command-line arguments (not used in this example)
     */
    public static void main(String[] args) {

        // List of input strings to process
        var inputs = List.of("apple", "banana", "cherry", "date", "elderberry");
        // Create a virtual thread executor
        var executor = Executors.newVirtualThreadPerTaskExecutor();

        // Map each input to a CompletableFuture chain and collect them into a list
        List<CompletableFuture<Void>> futures = inputs.stream().map(
                input -> CompletableFuture
                        .supplyAsync(() -> {
                            log("Fetching: " + input); // Log fetching step
                            sleep(1000); // Simulate a delay
                            return input; // Return the input
                        }, executor)
                        .thenApplyAsync(data -> {
                            log("Processing: " + data); // Log processing step
                            sleep(500); // Simulate processing time
                            return data.toUpperCase(); // Convert data to uppercase
                        }, executor)
                        .thenAcceptAsync(result -> log("Final result: " + result), executor) // Log final result
        ).toList();

        // Combine all CompletableFuture tasks into one
        var allDone = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        );
        allDone.join(); // Wait for all tasks to complete

        log("All tasks completed."); // Log completion message

        // Shutdown and close the executor
        executor.shutdown();
        executor.close();
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