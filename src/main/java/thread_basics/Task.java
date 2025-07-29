package thread_basics;

import java.util.logging.Logger;

// Use multiple threads to perform tasks concurrently
class Task implements Runnable {

    private static final Logger log = Logger.getLogger(Task.class.getName());
    private final String name;


    public Task(String name) {
        this.name = name;
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        log.info("Start: " + name + " - " + Thread.currentThread().getName());

        try {
            Thread.sleep(1000); // Simulate some work with a sleep
        } catch (InterruptedException ex) {
            log.warning("Thread interrupted: " + ex.getMessage());
        }
    }

}
