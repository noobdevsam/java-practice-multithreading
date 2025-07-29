package thread_basics;

class MyRunnable implements Runnable {

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        System.out.println("Running in thread: " + Thread.currentThread().getName());
    }
}
