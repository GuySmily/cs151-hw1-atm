import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Banks have ATMs and keep track of accounts and cards, as well as transaction validation.
 */
public class Bank {
    private String name;
    private String bank_id;

    private ArrayList<ATM> atms = new ArrayList<>();
    private HashMap<String, Account> accounts = new HashMap<String, Account>();
    private HashMap<String, Card> cards = new HashMap<String, Card>();

    // These two values should be part of the constructor
    private long nextAcctNumber = 5000;
    private long nextIAI = 123456789;  // Card number = 6 digit IIN + 9 digit IAI + 1 digit checksum

    /**
     * Creates a Bank
     * @param bankName for print output
     * @param IIN Issuer Identification Number - the first 6 digits of the card number for this bank.
     */
    public Bank(String bankName, String IIN){
        this.name = bankName;
        this.bank_id = IIN;
    }

    /**
     * Predefined possible outcomes when trying to read a card at an ATM
     */
    public enum CardStatus {
        UNRECOGNIZED, EXPIRED, VALID, INVALID
    }

    /**
     * Creates an Account for the Customer at this Bank.
     * Since an account is limited to one card, we'll treat them as the same.
     * If a customer loses a card, they must close their old account/card and create a new one.
     * @param cust owner of this account
     * @param password to access this account
     * @param initialDeposit amount this account is being opened with
     */
    public void openAccount(Customer cust, String password, double initialDeposit) {
        // Create account
        Account acct = new Account(this, nextAcctNumber++, cust, password, initialDeposit);

        // Create card that points to this account
        Card card = new Card(bank_id, acct.getNumber(), this.bank_id, nextIAI++, LocalDate.now().plusYears(3));

        // Update acct with new card number
        acct.assignCardNumber(card.getNumber());

        // Store card and account in bank database
        accounts.put(card.getNumber(), acct);
        cards.put(card.getNumber(), card);

    }

    /**
     * Creates an ATM at this Bank
     * @param atmNumber identifies this ATM
     * @param withdrawalLimit max withdrawal amount
     */
    public void buildATM(int atmNumber, long withdrawalLimit) {
        // Caution: It needs to be not allowed to insert over existing atmNumber
        atms.add(atmNumber - 1, new ATM(this, atmNumber, withdrawalLimit));
    }

    /**
     * Begins a transaction at the specified ATM
     * @param atmNumber which ATM to use
     */
    public void useATM(int atmNumber) {
        // Caution: No error checking here yet
        atms.get(atmNumber - 1).transact();
    }

    /**
     * Gives the name of this bank for printing messages
     * @return bank name
     */
    public String getName() {
        return name;
    }

    /**
     * Prints a nice message showing an ATM's information
     */
    public void printATMs() {
        for (ATM atm : this.atms) {
            System.out.println(atm.toString());
        }
    }

    /**
     * Prints detailed report of all accounts at this bank
     */
    public void printAccounts() {
        // Requirements:
        // Show a list of cards associated with an account at a bank with their expiration dates and passwords
        System.out.println("----- " + this.name + ": Account Report -----");
        for (Account acct : this.accounts.values()) {
            System.out.println(acct);
        }
    }

    /* ****************************************************************
     * Transaction validation helper functions for ATMs
     * **************************************************************** */
    /**
     *
     * @param cardNumber
     * @return
     */
    public CardStatus validateCard(String cardNumber){
        CardStatus status = CardStatus.INVALID;

        Card card = cards.get(cardNumber);
        Account acct = accounts.get(cardNumber);

        if (cardNumber.length() != 16) {
            status = CardStatus.INVALID;
        }
        else if (! cardNumber.startsWith(this.bank_id)) {
            status = CardStatus.UNRECOGNIZED;
        }
        else if (card == null) {
            status = CardStatus.UNRECOGNIZED;
        }
        else if (acct == null) {
            status = CardStatus.UNRECOGNIZED;
        }
        else if (card.isExpired()) {
            status = CardStatus.EXPIRED;
        }
        //TODO: Additional checks... acct closed, etc
        else{
            // Card exists in bank DB, related acct exists in DB, card is not expired.
            status = CardStatus.VALID;
        }

        return status;
    }

    /**
     *
     * @param cardNumber
     * @param password
     * @return
     */
    public boolean validatePassword(String cardNumber, String password) {
        return accounts.get(cardNumber).checkPassword(password);
    }

    /**
     *
     * @param cardNumber
     * @param amount
     * @return
     */
    public boolean validateWithdrawalAmount(String cardNumber, long amount) {
        return amount <= accounts.get(cardNumber).getBalance();
    }

    /**
     *
     * @param cardNumber
     * @param amount
     * @return
     */
    /*public boolean postTransaction(String cardNumber, long amount) {
        return accounts.get(cardNumber).withdraw(amount);
    }*/
    public double postTransaction(String cardNumber, long amount) {
        return accounts.get(cardNumber).withdraw(amount);
    }
}