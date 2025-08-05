package fifth_phase.mini_project_3;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class VirtualCompletableChain {

    private static final Logger logger = Logger.getLogger(VirtualCompletableChain.class.getName());

    public static void main(String[] args) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {

            CompletableFuture<Void> task = CompletableFuture
                    .supplyAsync(() -> {
                        log("Starting Task 1: Fetch data");
                        sleep(1000);
                        return "response-data";
                    }, executor)
                    .thenApplyAsync(data -> {
                        log("Step 2: Processing data");
                        sleep(500);
                        return data.toUpperCase();
                    }, executor)
                    .thenAcceptAsync(finalResult -> {
                        log("Step 3: Final output = " + finalResult);
                    }, executor)
                    .exceptionally(ex -> {
                        System.err.println("Something went wrong: " + ex.getMessage());
                        return null;
                    });

            task.join(); // Wait for the task to complete
            executor.shutdown();
        }
    }

    private static void log(String message) {
        System.out.printf("[%s] %s%n", Thread.currentThread(), message);
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }

}
