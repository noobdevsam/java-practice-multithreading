package second_phase;

import java.util.logging.Logger;

/**
 * Demonstrates a potential deadlock scenario using synchronized blocks.
 * This class contains two methods (`method1` and `method2`) that acquire locks
 * on two objects (`lock1` and `lock2`) in different orders, which can lead to a deadlock
 * if two threads execute these methods concurrently.
 */
class DeadLockDemo {

    private static final Logger logger = Logger.getLogger(DeadLockDemo.class.getName());
    // Lock objects used for synchronization
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    /**
     * Acquires `lock1` first and then attempts to acquire `lock2`.
     * Logs the progress of the thread as it acquires and waits for locks.
     */
    public void method1() {
        synchronized (lock1) {
            logger.info("Thread 1: Holding lock 1...");
            try {
                // Simulate some work
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // Reset the thread's interrupt status
                Thread.currentThread().interrupt();
            }

            logger.info("Thread 1: Waiting for lock 2...");
            synchronized (lock2) {
                logger.info("Thread 1: Acquired lock 2!");
            }
        }
    }

    /**
     * Acquires `lock2` first and then attempts to acquire `lock1`.
     * Logs the progress of the thread as it acquires and waits for locks.
     */
    public void method2() {
        synchronized (lock2) {
            logger.info("Thread 2: Holding lock 2...");
            try {
                // Simulate some work
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // Reset the thread's interrupt status
                Thread.currentThread().interrupt();
            }

            logger.info("Thread 2: Waiting for lock 1...");
            synchronized (lock1) {
                logger.info("Thread 2: Acquired lock 1!");
            }
        }
    }

}

// Fix the deadlock by ensuring that both methods acquire locks in the same order.