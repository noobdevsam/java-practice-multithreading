package third_phase.mini_projects.ticket_booking_system;

public class TicketBookingTest {

    public static void main(String[] args) {
        simulate_ticket_booking();
    }

    private static void simulate_ticket_booking() {
        // Create an instance of the TicketBooking system
        var ticketBookingSystem = new TicketBooking();

        // Define a task that books a ticket for the current thread's user
        Runnable task = () -> ticketBookingSystem.bookTicket(
                Thread.currentThread().getName(), 4
        );

        // Create threads representing different users booking tickets
        var thread1 = new Thread(task, "User-1");
        var thread2 = new Thread(task, "User-2");
        var thread3 = new Thread(task, "User-3");

        // Start the threads to execute the ticket booking task concurrently
        thread1.start();
        thread2.start();
        thread3.start();
    }

}
