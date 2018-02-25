/*
 *
 * Prompts:
 * Insert Card -> Expired card
 *             -> Unrecognized card
 *             -> Invalid entry
 *             -> Enter Pin         -> Rejected by bank (reason)
 *                                  -> Enter withdrawal amount -> Over ATM limit
 *                                                             -> Over account limit (try again)
 *                                                             -> Processing -> Success (dispensing)
 */

import java.util.Scanner;

/**
 * An ATM machine at a bank
 */
public class ATM {
    private Bank hostBank;
    private int atmNumber;  // Keep track of ATM number for receipt printing, etc
    private long withdrawalLimit;  // Whole dollars, please.
    private String state;

    /**
     * Creates an ATM machine
     * @param hostBank The bank that this ATM belongs to
     * @param atmNumber The ID for this ATM at this bank, starting at 1
     * @param withdrawalLimit The maximum withdrawal amount at this ATM
     */
    public ATM(Bank hostBank, int atmNumber, long withdrawalLimit) {
        this.hostBank = hostBank;
        this.atmNumber = atmNumber;
        this.withdrawalLimit = withdrawalLimit;
        this.state = "idle";
    }

    /**
     * Controls the entire transaction process, handling communication between bank and user.
     */
    public void transact() {
        Scanner sc = new Scanner(System.in);  // This should be replaced by ATM touchscreen/keypad input.
        String cardNumber = null;
        long amount = 0;

        Bank.CardStatus cardStatus = Bank.CardStatus.INVALID;
        while (cardStatus != Bank.CardStatus.VALID) {
            /* ****************************************************************
             * Welcome message / insert card
             * **************************************************************** */
            display("Welcome to " + hostBank.getName() + "!  This ATM only supports withdrawals at this time.  " +
                    "Limit: $" + withdrawalLimit + ".\n" +
                    "Insert card to begin ((enter card number)): ", false);
            cardNumber = String.valueOf(sc.nextLong());  //Get int from sc to reduce input validation?
            sc.nextLine();  // Eat the newline

            /* ****************************************************************
             * Card validation
             * **************************************************************** */
            display("Validating card.  Please wait...");
            cardStatus = hostBank.validateCard(cardNumber);
            //IMPORTANT: We adopt guard clauses here to avoid massive nesting, so we're using return instead of break!
            //CANCEL THAT.  If we want to go back to ejecting the card and starting over from scratch, then go back to returns.
            switch (cardStatus) {
                case EXPIRED:
                    // The expiration date is stored in the magstripe, but banks should check their DB for security reasons.
                    display("Card expired.  Please take your card.");
                    break; // return;
                case UNRECOGNIZED:
                    display("Card number not recognized.  Are you at the right bank?  Please take your card.");
                    break; // return;
                case INVALID:  // In reality this would come from the ATM machine, not the bank.  Just pretend.
                    display("Unable to read your card.  ((Please enter 16 digit numbers only.))");
                    break; // return;
                case VALID:
                    // accountNumber = hostBank.getAccountNumberFromCardNumber(cardNumber);
                    break;
                default:
                    display("An error occured.  Please take your card.");
                    break; // return;
            }
            System.out.println();
        }

        /* ****************************************************************
         * Password validation
         * **************************************************************** */
        boolean validPassword = false;
        while (!validPassword) {
            display("Please enter your password (leave blank to quit): ", false);
            String password = sc.nextLine();  //Our fancy bank supports alphanumerics!
            if (password.length() == 0) {
                display("Need help?  See a teller inside.  Please take your card.");
                return;
            }
            validPassword = hostBank.validatePassword(cardNumber, password);
            if (!validPassword) display("Password rejected by bank.");
        }
        System.out.println();

        /* ****************************************************************
         * Withdrawal amount
         * **************************************************************** */
        display("Our machines can give you bills in any denomination!");

        boolean anotherTransaction = true;
        while (anotherTransaction) {
            boolean validAmount = false;
            while (!validAmount) {
                display("Please enter withdrawal amount, up to $" + withdrawalLimit + ": ", false);
                amount = sc.nextLong();

                if (amount < 0) {
                    display("Sorry, no deposits at this machine.  Please go inside.  Don't forget your card!");
                    return;
                } else if (amount == 0) {
                    display("Get outta here!  And take your card with you!");
                    return;
                } else if (amount > withdrawalLimit) {
                    display("Sorry, the maximum withdrawal amount at this machine is: " + withdrawalLimit);
                    // Let user try again.
                } else if (amount <= withdrawalLimit) {
                    display("Processing.  Please wait.");
                    validAmount = hostBank.validateWithdrawalAmount(cardNumber, amount);
                    if (!validAmount) display("Withdrawal declined.  Try another amount?");
                    // Let user try again.
                }
                System.out.println();
            }

            /* ****************************************************************
             * Post transaction
             * **************************************************************** */
            display("Withdrawal approved!  Please wait while transaction is processed.");
            double balance = hostBank.postTransaction(cardNumber, amount);

            display("Transaction processed.  Your new balance is: $" + balance);
            display("Please take your receipt.");
            display("Please take your cash.");

            System.out.print("Another transaction (y/n)? ");
            anotherTransaction = String.valueOf(sc.next().charAt(0)).toLowerCase().equals("y");
        }

        display("Please take your card.");
        display("Thank you for banking with " + hostBank.getName() + "!");

    }

    /**
     * Print a message to the ATM's display.
     * @param message text to display.
     * @param newLine whether to print a newline after message.
     */
    private void display(String message, boolean newLine) {
        System.out.print(message + (newLine ? "\n" : ""));
    }

    /**
     * Print a message to the ATM's display, followed by a newline.
     * @param message text to display
     */
    private void display(String message) {
        display(message, true);
    }

    public long getWithdrawalLimit() {
        return withdrawalLimit;
    }
    public int getAtmNumber() {
        return atmNumber;
    }
    public Bank getHostBank() {
        return hostBank;
    }
    public String toString() {
        return "I am ATM " + atmNumber + " at Bank '" + hostBank.getName() + "'.  Withdrawal limit: $" + withdrawalLimit;
    }
}