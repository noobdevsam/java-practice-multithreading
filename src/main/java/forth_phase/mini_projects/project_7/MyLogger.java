package forth_phase.mini_projects.project_7;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A simple logger class that writes log messages to a file asynchronously.
 * The logger uses a `BlockingQueue` to store log messages and a separate thread
 * to write them to the file. It supports graceful shutdown to ensure all messages
 * are written before the application terminates.
 */
class MyLogger {

    /**
     * A thread-safe queue to store log messages before they are written to the file.
     */
    private final BlockingQueue<String> log_queue = new LinkedBlockingQueue<>();

    /**
     * The thread responsible for writing log messages to the file.
     */
    private final Thread writer_thread;

    /**
     * A flag to control the lifecycle of the logger. When set to `false`, the logger
     * stops accepting new messages and shuts down gracefully.
     */
    // The volatile keyword ensures that changes to the running variable are immediately visible to all threads
    private volatile boolean running = true;

    /**
     * Constructs a `MyLogger` instance and initializes the writer thread.
     *
     * @param filename The name of the file where log messages will be written.
     * @throws IOException If an I/O error occurs while opening the file.
     */
    public MyLogger(String filename) throws IOException {
        var writer = new BufferedWriter(
                new FileWriter(filename, true) // Open the file in append mode
        );

        // Initialize the writer thread
        writer_thread = new Thread(() -> {
            try {
                // Continuously process log messages while running or until the queue is empty
                while (running || !log_queue.isEmpty()) {

                    // Retrieve and remove the head of the queue, or return null if the queue is empty
                    // If a message is available, write it to the file
                    var message = log_queue.poll();

                    if (message != null) {
                        writer.write(message); // Write the message to the file
                        writer.newLine(); // Add a new line after each message
                    } else {
                        // Avoid busy-waiting by sleeping for a short duration
                        Thread.sleep(100);
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.getMessage(); // Handle exceptions during logging
            } finally {
                try {
                    // Ensure all messages are flushed and the writer is closed
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.getMessage();
                }
            }
        }, "MyLogger-Writer-Thread");

        // Start the writer thread
        writer_thread.start();
    }

    /**
     * Adds a log message to the queue for asynchronous writing.
     *
     * @param message The log message to be written to the file.
     */
    public void log(String message) {
        if (running) {
            log_queue.offer(message); // Add the message to the queue if the logger is running
        }
    }

    /**
     * Shuts down the logger gracefully. Ensures all queued messages are written
     * to the file before terminating the writer thread.
     *
     * @throws InterruptedException If the current thread is interrupted while waiting
     *                              for the writer thread to terminate.
     */
    public void shutdown() throws InterruptedException {
        running = false; // Signal the writer thread to stop
        writer_thread.join(); // Wait for the writer thread to finish
    }
}