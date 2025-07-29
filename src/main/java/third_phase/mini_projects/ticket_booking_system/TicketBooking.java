package third_phase.mini_projects.ticket_booking_system;

import java.util.logging.Logger;

/**
 * Represents a ticket booking system with a limited number of available tickets.
 * This class provides a method to book tickets in a thread-safe manner.
 */
class TicketBooking {

    // Logger instance for logging booking activities
    private final Logger logger = Logger.getLogger(TicketBooking.class.getName());
    // The number of tickets currently available for booking
    private int availableTickets = 10;

    /**
     * Attempts to book a specified number of tickets for a user.
     * This method is synchronized to ensure thread safety when accessing and modifying the available tickets.
     *
     * @param user  The name of the user attempting to book tickets.
     * @param count The number of tickets the user wants to book.
     * @return true if the booking is successful, false if there are not enough tickets available.
     */
    public synchronized boolean bookTicket(String user, int count) {

        if (count <= availableTickets) {
            // Log the successful booking
            logger.info(user + " booked " + count + " tickets.");

            // Deduct the booked tickets from the available tickets
            availableTickets -= count;
            return true;
        } else {
            // Log the failed booking attempt due to insufficient tickets
            logger.info(user + " tried to book " + count + " tickets but only " + availableTickets + " are available.");
            return false;
        }
    }

}