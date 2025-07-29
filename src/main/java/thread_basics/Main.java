package thread_basics;

import java.util.logging.Logger;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws InterruptedException {
//        // Create a new thread using the MyThread class
//        MyThread myThread = new MyThread();
//
//        // Start the thread
//        myThread.start();
//
//        // Create a new thread using the MyRunnable class
//        Thread runnableThread = new Thread(new MyRunnable());
//
//        // Start the runnable thread
//        runnableThread.start();

//        // Print the current thread's name
//        log.info("Main thread: " + Thread.currentThread().getName());
//
//        var tr1 = new Thread(new Task("Task 1"));
//        var tr2 = new Thread(new Task("Task 2"));
//
//        tr1.start();
//        tr2.start();
//
//        tr1.join(); // Wait for Task-2 (tr2) to finish
//        tr2.join(); // Wait for Task-1 (tr1) to finish
//
//        log.info("All tasks completed.");


        // Create a Counter instance
        Counter counter = new Counter();

        // Create and start two threads that increment the counter
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
//                counter.increment();
                counter.increment_two(); // Using the synchronized method for thread safety
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
//                counter.increment();
                counter.increment_two(); // Using the synchronized method for thread safety
            }
        });

        thread1.start();
        thread2.start();

        // Wait for both threads to finish
        thread1.join();
        thread2.join();

        // Print the final count
        log.info("Final count: " + Counter.count);
    }
}
