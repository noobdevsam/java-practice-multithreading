package thread_basics;

public class Main {

    public static void main(String[] args) {
        // Create a new thread using the MyThread class
        MyThread myThread = new MyThread();

        // Start the thread
        myThread.start();

        // Print the current thread's name
        System.out.println("Main thread: " + Thread.currentThread().getName());
    }
}
