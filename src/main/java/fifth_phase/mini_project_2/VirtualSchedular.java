package fifth_phase.mini_project_2;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VirtualSchedular {

    public static void main(String[] args) {

        var scheduler = Executors.newSingleThreadScheduledExecutor();
        var executor = Executors.newVirtualThreadPerTaskExecutor();

        Runnable periodicTask = () -> {

            System.out.println("Running periodic task at: " + LocalDateTime.now() + " | Thread: " + Thread.currentThread());

            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                System.err.println("Error executing task: " + e.getMessage());
            }
        };

        for (int i = 0; i < 10; i++) {
            executor.submit(periodicTask);
            scheduler.scheduleAtFixedRate(periodicTask, 0, 3, TimeUnit.SECONDS);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            scheduler.shutdown();
            executor.shutdown();
            System.out.println("Scheduler terminated and executor shutdown.");
        }));

        scheduler.close();
        executor.close();

    }

}
