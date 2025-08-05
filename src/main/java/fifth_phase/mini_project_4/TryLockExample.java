package fifth_phase.mini_project_4;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The TryLockExample class demonstrates the use of the ReentrantLock's tryLock method
 * to manage concurrent access to a shared resource.
 */
public class TryLockExample {

    // A ReentrantLock instance to control access to the shared resource
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * The main method creates and starts two threads that attempt to access
     * a shared resource using the tryLock mechanism.
     *
     * @param args Command-line arguments (not used in this example)
     */
    public static void main(String[] args) {
        TryLockExample example = new TryLockExample();

        // Define a task for the threads to execute
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            example.accessResource(threadName);
        };

        // Create and start two threads
        Thread thread1 = new Thread(task, "Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");

        thread1.start();
        thread2.start();

        // Wait for both threads to finish execution
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Both threads have finished execution.");
    }

    /**
     * Attempts to acquire the lock and access the shared resource.
     * If the lock is acquired, the thread performs some work and releases the lock.
     * If the lock cannot be acquired within the specified timeout, a message is logged.
     *
     * @param threadName The name of the thread attempting to access the resource
     */
    public void accessResource(String threadName) {
        try {
            // Attempt to acquire the lock with a timeout
            if (lock.tryLock(100, TimeUnit.MILLISECONDS)) {
                try {
                    System.out.println(threadName + " has acquired the lock.");
                    // Simulate some work with the resource
                    Thread.sleep(200);
                } finally {
                    // Release the lock
                    lock.unlock();
                    System.out.println(threadName + " has released the lock.");
                }
            } else {
                // Log a message if the lock could not be acquired
                System.out.println(threadName + " could not acquire the lock, it is busy.");
            }
        } catch (InterruptedException e) {
            // Handle thread interruption
            Thread.currentThread().interrupt();
        }
    }
}