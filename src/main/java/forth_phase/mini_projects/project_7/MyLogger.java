package forth_phase.mini_projects.project_7;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class MyLogger {

    private final BlockingQueue<String> log_queue = new LinkedBlockingQueue<>();
    private final Thread writer_thread;
    // The volatile keyword ensures that changes to the running variable are immediately visible to all threads
    private volatile boolean running = true;

    public MyLogger(String filename) throws IOException {
        var writer = new BufferedWriter(
                new FileWriter(filename, true)
        );

        writer_thread = new Thread(() -> {
            try {

                while (running || !log_queue.isEmpty()) {
                    var message = log_queue.poll();

                    if (message != null) {
                        writer.write(message);
                        writer.newLine();
                    } else {
                        // Avoid busy-waiting
                        Thread.sleep(100);
                    }
                }

            } catch (IOException | InterruptedException e) {
                e.getMessage();
            } finally {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.getMessage();
                }
            }
        }, "MyLogger-Writer-Thread");

        writer_thread.start();

    }

    public void log(String message) {
        if (running) {
            log_queue.offer(message);
        }
    }

    public void shutdown() throws InterruptedException {
        running = false;
        writer_thread.join();
    }
}
