package third_phase.mini_projects.producer_consumer_with_blocking_queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Logger;

/**
 * A simple Producer-Consumer example using an ArrayBlockingQueue.
 * The producer generates integer values and places them in the queue,
 * while the consumer retrieves and processes these values.
 */
public class ProducerConsumer {

    // Logger instance for logging messages
    private static final Logger logger = Logger.getLogger(ProducerConsumer.class.getName());

    public static void main(String[] args) {

        // Create a blocking queue with a capacity of 5
        var queue = new ArrayBlockingQueue<Integer>(5);

        // Define the producer task
        Runnable producer = () -> {
            int value = 0;

            while (true) {
                try {
                    // Add a value to the queue
                    queue.put(value);

                    logger.info("Produced: " + value++);

                    Thread.sleep(500); // Simulate production delay

                    // Stop producing after 20 items
                    if (value >= 20) {
                        logger.info("Producer finished producing items.");
                        break;
                    }
                } catch (InterruptedException e) {
                    // Log interruption and exit the loop
                    logger.info(e.getMessage());
                    break;
                }
            }
        };

        // Define the consumer task
        Runnable consumer = () -> {
            while (true) {
                try {
                    // Retrieve a value from the queue
                    int val = queue.take();

                    logger.info("Consumed: " + val);

                    Thread.sleep(800); // Simulate consumption delay

                    // Stop consuming after 20 items
                    if (val >= 20) {
                        logger.info("Consumer finished consuming items.");
                        break;
                    }
                } catch (InterruptedException e) {
                    // Log interruption and exit the loop
                    logger.info(e.getMessage());
                    break;
                }
            }
        };

        // Start the producer and consumer threads
        new Thread(producer, "User-1").start();
        new Thread(consumer, "User-2").start();
    }
}