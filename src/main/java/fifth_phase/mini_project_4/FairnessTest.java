package fifth_phase.mini_project_4;

import java.util.concurrent.locks.ReentrantLock;

/**
 * The FairnessTest class demonstrates the use of a fair ReentrantLock
 * to ensure threads acquire the lock in a fair order.
 */
public record FairnessTest(ReentrantLock lock) {

    /**
     * The main method initializes a FairnessTest instance with a fair ReentrantLock
     * and starts multiple threads to test lock fairness.
     *
     * @param args Command-line arguments (not used in this example)
     */
    public static void main(String[] args) {
        // Create a FairnessTest instance with a fair ReentrantLock
        var test = new FairnessTest(
                new ReentrantLock(true) // Fair lock ensures threads acquire the lock in order
        );

        // Start multiple threads to test lock fairness
        for (int i = 0; i <= 3; i++) {
            test.access("test" + i);
        }
    }

    /**
     * Creates and starts a thread that attempts to acquire the lock multiple times.
     * Each thread logs messages when it acquires and releases the lock.
     *
     * @param name The name of the thread
     */
    public void access(String name) {
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                lock.lock(); // Acquire the lock
                try {
                    System.out.println(name + " acquired the lock"); // Log lock acquisition
                    Thread.sleep(50); // Simulate work while holding the lock
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Handle thread interruption
                } finally {
                    lock.unlock(); // Release the lock
                    System.out.println(name + " released the lock"); // Log lock release
                }
            }
        }).start();
    }
}