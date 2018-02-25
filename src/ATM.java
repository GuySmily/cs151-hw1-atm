import java.util.Scanner;

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
public class ATM {
    private Bank hostBank;
    private int atmNumber;  // Keep track of ATM number for receipt printing, etc
    private int withdrawalLimit;  // Whole dollars, please.
    private String state;

    public ATM(Bank hostBank, int atmNumber, int withdrawalLimit) {
        this.hostBank = hostBank;
        this.atmNumber = atmNumber;
        this.withdrawalLimit = withdrawalLimit;
        this.state = "idle";
    }

    public void transact() {
        Scanner sc = new Scanner(System.in);  // This should be replaced by ATM touchscreen/keypad input.
        int amount = 0;


        /* ****************************************************************
         * Welcome message / insert card
         * **************************************************************** */
        display("Welcome to " + hostBank.getName() + "!  This ATM only supports withdrawals at this time.  " +
                "Limit: $" + withdrawalLimit + ".\n" +
                "Insert card to begin ((enter card number)): ", false);
        String cardNumber = String.valueOf(sc.nextInt());  //Get int from sc to reduce input validation

        /* ****************************************************************
         * Card validation
         * **************************************************************** */
        display("Validating card.  Please wait.");
        Bank.CardStatus cardStatus = hostBank.validateCard(cardNumber);
        //IMPORTANT: We adopt guard clauses here to avoid massive nesting, so we're using return instead of break!
        switch (cardStatus) {
            case EXPIRED:
                // The expiration date is stored in the magstripe, but banks should check their DB for security reasons.
                display("Card expired.  Please take your card.");
                return;
            case UNRECOGNIZED:
                display("Card number not recognized.  Are you at the right bank?  Please take your card.");
                return;
            case INVALID:  // In reality this would come from the ATM machine, not the bank.  Just pretend.
                display("Unable to read your card.  ((Please enter 16 digit numbers only.))");
                return;
            case VALID:
                // accountNumber = hostBank.getAccountNumberFromCardNumber(cardNumber);
                break;
            default:
                display("An error occured.  Please take your card.");
                return;
        }

        /* ****************************************************************
         * Password validation
         * **************************************************************** */
        boolean validPassword = false;
        while (!validPassword) {
            display("Please enter your password (leave blank to quit): ", false);
            char[] password = System.console().readPassword();  //Our fancy bank supports alphanumerics!
            if (password.length == 0) {
                display("Need help?  See a teller inside.  Please take your card.");
                return;
            }
            validPassword = hostBank.validatePassword(cardNumber, password);
            if (!validPassword) display("Password rejected by bank.");
        }

        /* ****************************************************************
         * Withdrawal amount
         * **************************************************************** */
        display("Our machines can give you bills in any denomination, up to $" + withdrawalLimit + "!");
        boolean validAmount = false;
        while (!validAmount) {
            display("Please enter withdrawal amount: ", false);
            amount = sc.nextInt();

            if (amount < 0) {
                display("Sorry, no deposits at this machine.  Please go inside.  Don't forget your card!");
                return;
            }
            else if (amount == 0) {
                display("Get outta here!  And take your card with you!");
                return;
            }
            else if (amount > withdrawalLimit) {
                display("Sorry, the maximum withdrawal amount at this machine is: " + withdrawalLimit);
                // Let user try again.
            }
            else if (amount <= withdrawalLimit) {
                display("Processing.  Please wait.");
                validAmount = hostBank.validateWithdrawalAmount(cardNumber, amount);
                if (!validAmount) display("Withdrawal declined.  Try another amount?");
                // Let user try again.
            }
        }

        /* ****************************************************************
         * Post transaction
         * **************************************************************** */
        display("Withdrawal approved!  Please wait while transaction is processed.");
        if (hostBank.postTransaction(cardNumber, amount)) {
            display("Transaction processed.  Please take your card.", false);
            sc.nextLine();
            display("Please take your receipt.", false);
            sc.nextLine();
            display("Please take your cash.", false);
            sc.nextLine();
            display("Thank you for banking with " + hostBank.getName() + "!");
        }
        else {
            display("The transaction could not be completed.  SOMETHING BAD HAPPENED.");
        }
    }

    private Card getCardFromNumber()

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

    public int getWithdrawalLimit() {
        return withdrawalLimit;
    }
    public int getAtmNumber() {
        return atmNumber;
    }
    public Bank getHostBank() {
        return hostBank;
    }
    public String toString() {
        return "I am ATM" + atmNumber + "at Bank '" + hostBank.getName() + ", withdrawal limit $" + withdrawalLimit;
    }
}