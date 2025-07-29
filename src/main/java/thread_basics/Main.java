package thread_basics;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
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
//
//        simulate_with_executor_service();
//
//        simulate_with_future_and_callable();
//
//        simulate_parallel_processing_with_callable();
//
        /*
         * Both approaches run tasks in parallel using multiple threads.
         * Use ForkJoinPool for custom recursive tasks,
         * and parallelStream() for simple collection processing.
         * */
//        simulate_parallel_processing_with_fork_join();
//        simulate_parallel_processing_with_parallel_stream();
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

    private static void simulate_with_executor_service() {
        try (var executor = Executors.newFixedThreadPool(5)) {

            for (int i = 0; i < 10; i++) {
                executor.submit(() -> {
                    log.info("Current thread: " + Thread.currentThread().getName());
                });
            }

            executor.shutdown();

        } catch (Exception e) {
            log.severe("An error occurred: " + e.getMessage());
        }

    }

    // Return a result from a Callable task using Future
    // This simulates a task that returns a result after some processing
    private static void simulate_with_future_and_callable() {
        Callable<String> task = () -> {
            Thread.sleep(1000); // Simulate some work
            return "Task completed";
        };

        try (var executor = Executors.newSingleThreadExecutor()) {
            var future = executor.submit(task);

            // This will block until the task is complete
            String result = future.get();

            log.info("Result from Callable: " + result);

            executor.shutdown();
        } catch (Exception e) {
            log.severe("An error occurred: " + e.getMessage());
        }

    }

    // Simulate parallel processing with multiple Callable tasks
    // This demonstrates how to run multiple tasks in parallel and collect their results
    private static void simulate_parallel_processing_with_callable() {

        var tasks = List.of(
                () -> {
                    Thread.sleep(50);
                    return "Result from Task 1";
                },
                () -> {
                    Thread.sleep(70);
                    return "Result from Task 2";
                },
                () -> {
                    Thread.sleep(30);
                    return "Result from Task 3";
                },
                (Callable<String>) () -> {
                    Thread.sleep(40);
                    return "Result from Task 4";
                }
        );

        try (var executor = Executors.newFixedThreadPool(4)) {

            var futures = executor.invokeAll(tasks);

            for (var future : futures) {
                log.info(future.get());
            }

            executor.shutdown();

        } catch (Exception e) {
            log.severe("An error occurred: " + e.getMessage());
        }

    }

    // Simulate parallel processing using ForkJoinPool
    private static void simulate_parallel_processing_with_fork_join() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        try (var pool = new ForkJoinPool()) {
            var result = pool.invoke(
                    new SumTask(arr, 0, arr.length)
            );

            log.info("Sum of array elements: " + result);
        } catch (Exception e) {
            log.severe("An error occurred: " + e.getMessage());
        }
    }

    private static void simulate_parallel_processing_with_parallel_stream() {
        var numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Using parallel stream to sum the elements of the array
        var sum = numbers
                .parallelStream()
                .mapToInt(Integer::intValue)
                .sum();

        log.info("Sum using parallel stream: " + sum);
    }
}
