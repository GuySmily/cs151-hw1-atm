public class Account {
    private Bank hostBank;
    private long accountNumber;
    private Customer owner;
    private String password;
    private double balance;
    private String cardNumber;  //Limit one per acct

    /**
     * Creates an Account holds financial and security information for a customer at a Bank.
     * @param hostBank the bank this account is held at
     * @param accountNumber the identifying number of this account
     * @param cust owner of this account
     * @param password required to access this account
     * @param initialBalance the balance to post to this account when it is opened
     */
    public Account(Bank hostBank, long accountNumber, Customer cust, String password, double initialBalance){
        this.hostBank = hostBank;
        this.accountNumber = accountNumber;
        this.owner = cust;
        this.password = password;
        this.balance = initialBalance;
        this.cardNumber = null;
    }

    /**
     * Assigns a debit card number to this account.  Separate from constructor because Card requires acct to exist first
     * @param newCardNumber card number that identifies this account
     */
    public void assignCardNumber(String newCardNumber){
        cardNumber = newCardNumber;
    }

    /**
     * @return card number in string format
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Show a list of cards associated with an account at a bank with their expiration dates and passwords
     * @return string representation of account for printing
     */
    public String toString() {
        return "Acct: " + accountNumber +
               ", Bal: $" + balance +
               ", Card: " + cardNumber +
               ", PW: " + password;
    }

    /**
     * Checks if given password matches this account
      * @param password password to check
     * @return whether password was correct or not
     */
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    /**
     * Gets balance of account
     * @return account balance
     */
    public double getBalance() {
        return balance;
    }

    // Could return balance here, but usually ATMs do not display balance - you have to explicitly request balances.
    /*public boolean withdraw(double amount) {
        boolean success = false;
        if (balance >= amount) {
            balance -= amount;
            success = true;
        }
        return success;
    }*/

    /**
     * Reduces account balance by amount and returns remaining balance
     * @param amount how much to remove from account
     * @return remaining balance
     */
    public double withdraw(double amount) {
        return balance -= amount;
    }

    /**
     * Gets account number
     * @return account number
     */
    public long getNumber() {
        return accountNumber;
    }
}
