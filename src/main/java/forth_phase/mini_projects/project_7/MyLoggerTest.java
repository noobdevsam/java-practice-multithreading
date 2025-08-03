package forth_phase.mini_projects.project_7;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyLoggerTest {

    public static void main(String[] args) throws Exception {
        var logger = new MyLogger("app.log");

        try (var executor = Executors.newFixedThreadPool(4)) {
            Runnable task = () -> {
                for (int i = 0; i < 100; i++) {
                    logger.log(Thread.currentThread().getName() + " - Log message " + i);

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException ignored) {
                    }
                }
            };

            for (int i = 0; i < 4; i++) {
                executor.submit(task);
            }

            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);

            logger.shutdown();

            System.out.println("All log messages have been written to the file.");
        }
    }

}
