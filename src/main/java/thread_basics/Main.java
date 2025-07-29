package thread_basics;

import java.util.logging.Logger;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws InterruptedException {
//        simulate_simple_thread();
//
//        simulate_thread_with_runnable();
//
//        simulate_multithread_execution();
//
//
//        simulate_and_solve_race_conditions();
    }

    private static void simulate_simple_thread() {
        // Create a new thread using the MyThread class
        var myThread = new MyThread();

        // Start the thread
        myThread.start();
    }

    private static void simulate_thread_with_runnable() {
        // Create a new thread using the MyRunnable class
        var runnableThread = new Thread(new MyRunnable());

        // Start the runnable thread
        runnableThread.start();

        // Print the current thread's name
        log.info("Main thread: " + Thread.currentThread().getName());
    }

    private static void simulate_multithread_execution() throws InterruptedException {
        var tr1 = new Thread(new Task("Task 1"));
        var tr2 = new Thread(new Task("Task 2"));

        tr1.start();
        tr2.start();

        tr1.join(); // Wait for Task-1 (tr1) to finish
        tr2.join(); // Wait for Task-2 (tr2) to finish

        log.info("All tasks completed.");
    }

    private static void simulate_and_solve_race_conditions() throws InterruptedException {
        // Create a Counter instance
        var counter = new Counter();

        // Create and start two threads that increment the counter
        var thread1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
//                counter.increment();
                counter.increment_two(); // Using the synchronized method for thread safety
            }
        });

        var thread2 = new Thread(() -> {
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
