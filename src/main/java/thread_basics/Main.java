package thread_basics;

public class Main {

    public static void main(String[] args) {
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
    }
}
