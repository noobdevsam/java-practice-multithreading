package forth_phase.mini_projects.project_7;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class MyLogger {

    private final BlockingQueue<String> log_queue = new LinkedBlockingQueue<>();
    private final Thread writer_thread;
    // The volatile keyword ensures that changes to the running variable are immediately visible to all threads
    private volatile boolean running = true;

    public MyLogger(Thread writer_thread) {
        this.writer_thread = writer_thread;
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
