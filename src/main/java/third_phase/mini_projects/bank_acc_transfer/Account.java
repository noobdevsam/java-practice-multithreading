package third_phase.mini_projects.bank_acc_transfer;

/**
 * Represents a bank account with a balance and a name.
 * Provides synchronized methods for depositing, withdrawing, and checking the balance.
 */
class Account {

    // The name of the account holder
    private final String name;
    // The current balance of the account
    private int balance;

    /**
     * Constructs an Account instance with the specified name and initial balance.
     *
     * @param name    The name of the account holder.
     * @param balance The initial balance of the account.
     */
    public Account(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }

    /**
     * Deposits the specified amount into the account.
     * This method is synchronized to ensure thread safety.
     *
     * @param amount The amount to deposit.
     */
    public synchronized void deposit(int amount) {
        balance += amount;
    }

    /**
     * Withdraws the specified amount from the account.
     * This method is synchronized to ensure thread safety.
     *
     * @param amount The amount to withdraw.
     */
    public synchronized void withdraw(int amount) {
        balance -= amount;
    }

    /**
     * Retrieves the current balance of the account.
     * This method is synchronized to ensure thread safety.
     *
     * @return The current balance of the account.
     */
    public synchronized int getBalance() {
        return balance;
    }

    /**
     * Retrieves the name of the account holder.
     *
     * @return The name of the account holder.
     */
    public String getName() {
        return name;
    }
}