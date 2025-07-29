package second_phase;

import java.util.concurrent.locks.ReentrantLock;

/**
 * The Counter class provides a thread-safe mechanism to increment and retrieve a count value.
 * It uses a ReentrantLock to ensure that the increment operation is performed atomically.
 */
class Counter {

    // A ReentrantLock to ensure thread-safe access to the count variable.
    private final ReentrantLock lock = new ReentrantLock();
    // The current count value.
    private int count = 0;

    /**
     * Increments the count value by 1 in a thread-safe manner.
     * The method acquires a lock before performing the increment operation
     * and releases the lock afterward.
     */
    public void increment() {
        lock.lock();

        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Retrieves the current count value.
     *
     * @return the current value of the count variable.
     */
    public int getCount() {
        return count;
    }
}
