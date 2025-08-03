package fifth_phase;

import java.time.Duration;
import java.time.Instant;

public class VirtualThreadDemo {

    public static void main(String[] args) {
        var numberOfTasks = 10_000;
        var start = Instant.now();

        for (int i = 0; i < numberOfTasks; i++) {
            Thread.startVirtualThread(() -> {
                try {
                    Thread.sleep(100);
                    System.out.println("Executed by = " + Thread.currentThread());
                } catch (InterruptedException e) {
                    e.getMessage();
                }
            });
        }

        var end = Instant.now();
        var duration = Duration.between(start, end);

        System.out.println("Time taken to execute " + numberOfTasks + " tasks = " + duration.toMillis() + " ms");

        try {
            Thread.sleep(1000); // Wait for all virtual threads to complete
        } catch (InterruptedException ignored) {
        }
    }

}
