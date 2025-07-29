package thread_basics;

public class Main {

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

        // Print the current thread's name
        System.out.println("Main thread: " + Thread.currentThread().getName());

        var tr1 = new Thread(new Task("Task 1"));
        var tr2 = new Thread(new Task("Task 2"));

        tr1.start();
        tr2.start();

        tr1.join(); // Wait for Task-1 (tr1) to finish
        tr2.join(); // Wait for Task-2 (tr2) to finish

        System.out.println("All tasks completed.");
    }
}
