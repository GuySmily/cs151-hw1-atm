public class Account {
    private Bank hostBank;
    private long accountNumber;
    private Customer owner;
    private String password;
    private double balance;
    private String cardNumber;  //Limit one per acct

    public Account(Bank hostBank, long accountNumber, Customer cust, String password, double initialBalance){
        this.hostBank = hostBank;
        this.accountNumber = accountNumber;
        this.owner = cust;
        this.password = password;
        this.balance = initialBalance;
        this.cardNumber = null;
    }
    public void assignCardNumber(String newCardNumber){
        cardNumber = newCardNumber;
    }
    public String getCardNumber() {
        return cardNumber;
    }

    // Show a list of cards associated with an account at a bank with their expiration dates and passwords
    public String toString() {
        return "Acct: " + accountNumber +
               ", Bal: $" + balance +
               ", Card: " + cardNumber +
               ", Card: " + cardNumber +
               ", PW: " + password;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public double getBalance() {
        return balance;
    }

    // Could return balance here, but usually ATMs do not display balance - you have to explicitly request balances.
    public boolean withdraw(double amount) {
        boolean success = false;
        if (balance >= amount) {
            balance -= amount;
            success = true;
        }
        return success;
    }

    public long getNumber() {
        return accountNumber;
    }
}
