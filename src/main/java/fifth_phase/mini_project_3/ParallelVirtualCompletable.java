package fifth_phase.mini_project_3;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;


public class ParallelVirtualCompletable {

    public static void main(String[] args) {

        var inputs = List.of("apple", "banana", "cherry", "date", "elderberry");
        var executor = Executors.newVirtualThreadPerTaskExecutor();

        List<CompletableFuture<Void>> futures = inputs.stream().map(
                input -> CompletableFuture
                        .supplyAsync(() -> {
                            log("Fetching: " + input);
                            sleep(1000); // Simulate a delay
                            return input;
                        }, executor)
                        .thenApplyAsync(data -> {
                            log("Processing: " + data);
                            sleep(500); // Simulate processing time
                            return data.toUpperCase();
                        }, executor)
                        .thenAcceptAsync(result -> log("Final result: " + result), executor)
        ).toList();

        var allDone = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        );
        allDone.join(); // Wait for all tasks to complete

        log("All tasks completed.");

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