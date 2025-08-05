package fifth_phase.mini_project_4;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockExample {

    private final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        TryLockExample example = new TryLockExample();

        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            example.accessResource(threadName);
        };

        Thread thread1 = new Thread(task, "Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Both threads have finished execution.");
    }

    public void accessResource(String threadName) {
        try {
            if (lock.tryLock(100, TimeUnit.MILLISECONDS)) {
                try {
                    System.out.println(threadName + " has acquired the lock.");
                    // Simulate some work with the resource
                    Thread.sleep(200);
                } finally {
                    lock.unlock();
                    System.out.println(threadName + " has released the lock.");
                }
            } else {
                System.out.println(threadName + " could not acquire the lock, it is busy.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
