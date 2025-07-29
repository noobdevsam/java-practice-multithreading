package second_phase;

import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws InterruptedException {
//        simulate_race_condition_fix_with_reentrant_lock();
    }

    /**
     * Simulates fixing a race condition using a ReentrantLock mechanism.
     * This method creates two threads that increment a shared counter concurrently.
     * The threads are joined to ensure the main thread waits for their completion.
     * Finally, the final value of the counter is logged.
     *
     * @throws InterruptedException if the thread execution is interrupted
     */
    private static void simulate_race_condition_fix_with_reentrant_lock() throws InterruptedException {
        // Create a shared Counter instance
        var counter = new Counter();

        // Define a task that increments the counter 10 times
        Runnable task = () -> {
            for (int i = 0; i < 10; i++) {
                counter.increment();
            }
        };

        // Create two threads to execute the task
        var thread1 = new Thread(task);
        var thread2 = new Thread(task);

        // Start both threads
        thread1.start();
        thread2.start();

        // Wait for both threads to complete
        thread1.join();
        thread2.join();

        // Log the final value of the counter
        logger.info("Final counter value: " + counter.getCount());
    }
}
