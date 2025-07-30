package third_phase.mini_projects.bank_acc_transfer;

import java.util.logging.Logger;

/**
 * Represents a task for transferring money between two bank accounts.
 * This class implements the Runnable interface to allow execution in a separate thread.
 */
class TransferTask implements Runnable {

    // The account from which money will be transferred
    private final Account fromAccount;

    // The account to which money will be transferred
    private final Account toAccount;

    // The amount of money to transfer
    private final int amount;

    // Logger instance for logging transfer details
    private final Logger logger = Logger.getLogger(TransferTask.class.getName());

    /**
     * Constructs a TransferTask instance with the specified accounts and transfer amount.
     *
     * @param fromAccount The account from which money will be transferred.
     * @param toAccount   The account to which money will be transferred.
     * @param amount      The amount of money to transfer.
     */
    public TransferTask(Account fromAccount, Account toAccount, int amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    /**
     * Executes the transfer operation.
     * Ensures thread safety by synchronizing on both accounts.
     * Logs the transfer details if the operation is successful.
     */
    @Override
    public void run() {

        // Synchronize on both accounts to prevent deadlock and ensure thread safety
        // The order of synchronization is important to avoid deadlocks

        synchronized (fromAccount) {
            synchronized (toAccount) {
                performTransfer();
            }
        }
    }

    private void performTransfer() {
        // Check if the source account has sufficient balance
        if (fromAccount.getBalance() >= amount) {
            // Withdraw the amount from the source account
            fromAccount.withdraw(amount);

            // Deposit the amount into the destination account
            toAccount.deposit(amount);

            // Log the transfer details
            logger.info(Thread.currentThread().getName() + " transferred " + amount + " from "
                    + fromAccount.getName() + " to " + toAccount.getName());
            logger.info(Thread.currentThread().getName() + " transfer completed");

            logger.info(fromAccount.getName() + " now has a balance of " + fromAccount.getBalance());
            logger.info(toAccount.getName() + " now has a balance of " + toAccount.getBalance());
        }
    }
}