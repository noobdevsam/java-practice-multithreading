package third_phase.mini_projects.library_access_simulation;

import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

/**
 * The Library class simulates a library with limited seating capacity.
 * It uses a Semaphore to control access, ensuring that only a fixed number
 * of students can study in the library at the same time.
 */
class Library {

    // Semaphore to control access to the library
    private final Semaphore seats = new Semaphore(3); // Maximum 3 students can study at a time

    // Logger to log library access events
    private final Logger logger = Logger.getLogger(Library.class.getName());

    /**
     * Allows a student to enter the library if a seat is available.
     * If no seats are available, the student waits until one becomes free.
     *
     * @param student The name of the student attempting to enter the library.
     */
    public void enterLibrary(String student) {
        try {
            // Acquire a seat (block if no seats are available)
            seats.acquire();
            logger.info(student + " has entered the library.");

            // Simulate the student studying in the library
            Thread.sleep(2000);
            logger.info(student + " has left the library.");
        } catch (InterruptedException ex) {
            // Handle interruption during library access
            logger.warning(student + " was interrupted while trying to enter the library.");
        } finally {
            // Release the seat after the student leaves
            seats.release();
        }
    }
}