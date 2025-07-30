package third_phase.mini_projects.bank_acc_transfer;

public class BankTest {

    public static void main(String[] args) {

        // Create an account for Alice with an initial balance of 1000
        var account1 = new Account("Alice", 1000);

        // Create an account for Bob with an initial balance of 500
        var account2 = new Account("Bob", 500);

        // Start a thread to transfer 200 units from Alice's account to Bob's account
        new Thread(
                new TransferTask(account1, account2, 200), // Transfer task specifying source, destination, and amount
                "Thread-1" // Name of the thread
        )
                .start(); // Start the thread

        // Start a thread to transfer 300 units from Bob's account to Alice's account
        new Thread(
                new TransferTask(account2, account1, 300), // Transfer task specifying source, destination, and amount
                "Thread-2" // Name of the thread
        )
                .start(); // Start the thread

    }

}
