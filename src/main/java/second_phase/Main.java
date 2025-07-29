package second_phase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws InterruptedException {
//        simulate_race_condition_fix_with_reentrant_lock();

//        simulate_waiting_with_countdown_latch();

//        simulate_limit_access_with_semaphore();
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

    /**
     * Simulates waiting for multiple threads to complete using a CountDownLatch.
     * This method creates three threads, each performing a simulated task.
     * The CountDownLatch ensures the main thread waits until all tasks are completed.
     * Each thread decrements the latch count upon completion of its task.
     * Finally, a log message is displayed indicating all tasks are completed.
     *
     * @throws InterruptedException if the thread execution is interrupted
     */
    private static void simulate_waiting_with_countdown_latch() throws InterruptedException {
        // Initialize a CountDownLatch with a count of 3
        var latch = new CountDownLatch(3);

        // Define a task to be executed by each thread
        Runnable task = () -> {
            logger.info("Task started by " + Thread.currentThread().getName());

            try {
                // Simulate some work with sleep
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Handle interruption and reset thread's interrupt status
                Thread.currentThread().interrupt();
            } finally {
                // Decrement the latch count
                latch.countDown();
            }
        };

        // Create and start three threads to execute the task
        for (int i = 0; i < 3; i++) {
            new Thread(task, "Thread-" + (i + 1)).start();
        }

        // Wait for all tasks to complete
        latch.await();

        // Log a message indicating all tasks are completed
        logger.info("All tasks completed.");
    }

    /**
     * Simulates limiting access to a resource using a Semaphore.
     * This method creates a Semaphore with 2 permits and spawns 5 threads.
     * Each thread attempts to acquire a permit, performs a simulated task, and then releases the permit.
     * The Semaphore ensures that at most 2 threads can access the resource concurrently.
     * Logs are generated to indicate when a permit is acquired and released.
     */
    private static void simulate_limit_access_with_semaphore() {
        // Create a Semaphore with 2 permits
        var semaphore = new Semaphore(2);

        // Define a task to be executed by each thread
        Runnable task = () -> {
            try {
                // Acquire a permit from the semaphore
                semaphore.acquire();

                logger.info("Acquired permit: " + Thread.currentThread().getName());

                // Simulate some work with sleep
                Thread.sleep(1000);

                logger.info("Releasing permit: " + Thread.currentThread().getName());

            } catch (InterruptedException e) {
                // Handle exception and reset thread's interrupt status
                Thread.currentThread().interrupt();
                logger.severe("Thread interrupted: " + e.getMessage());
            } finally {
                // Release the permit back to the semaphore
                semaphore.release();
            }
        };

        // Create and start 5 threads to execute the task
        for (int i = 0; i < 5; i++) {
            new Thread(task).start();
        }
    }
}
