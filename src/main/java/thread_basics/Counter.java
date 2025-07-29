package thread_basics;

class Counter {

    static int count = 0;

    /**
     * Increments the count variable and prints the current count.
     * This method is not synchronized, which means it is not thread-safe.
     * and occur race conditions if accessed by multiple threads concurrently.
     */
    void increment() {
        count++;
        System.out.println("Count: " + count + " by " + Thread.currentThread().getName());
    }

    /**
     * Increments the count variable in a thread-safe manner.
     * This method is synchronized, which means it can be safely accessed by multiple threads concurrently.
     * and prevents race conditions.
     */
    synchronized void increment_two() {
        count++;
        System.out.println("Count: " + count + " by " + Thread.currentThread().getName());
    }

}
