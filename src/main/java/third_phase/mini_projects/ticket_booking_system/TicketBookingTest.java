package third_phase.mini_projects.ticket_booking_system;

public class TicketBookingTest {

    public static void main(String[] args) {
        var ticketBookingSystem = new TicketBooking();

        Runnable task = () -> ticketBookingSystem.bookTicket(
                Thread.currentThread().getName(), 4
        );

        var thread1 = new Thread(task, "User-1");
        var thread2 = new Thread(task, "User-2");
        var thread3 = new Thread(task, "User-3");

        thread1.start();
        thread2.start();
        thread3.start();
    }

}
