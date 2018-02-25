import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Bank {
    private String name;
    private String bank_id;

    private ArrayList<ATM> atms = new ArrayList<ATM>;

    //In lieu of a RDBMS, this data structure will store our Account objects for lookup by card number
    private HashMap<String, Account> accounts = new HashMap<String, Account>();

    public Bank(String bankName, String IIN){
        name = bankName;
        bank_id = IIN;
    }
    public enum CardStatus {
        UNRECOGNIZED, EXPIRED, VALID, INVALID
    }
    // Since an account is limited to one card, we'll treat them as the same.
    // If a customer loses a card, they must close their old account/card and create a new one.
    public Card openAccount(Customer cust, String password, double initialDeposit) {
        Account acct = new Account(cust, password, initialDeposit);

        LocalDate expr = LocalDate.now().plusYears(3);
        card = new Card(bank_id, acct.getNumber(), expr);

        acct.assignCard(card.getNumber());
        accounts.put(card.getNumber, acct);


        return card;
    }

    public void buildATM(int withdrawalLimit) {
        atms.add(new ATM(this, withdrawalLimit))
    }

    /* ****************************************************************
     * Transaction validation helper functions for ATMs
     * **************************************************************** */
    public CardStatus validateCard(String cardNumber){
        CardStatus status = CardStatus.INVALID;

        cardNumber = cardNumber.replace(" ", "")
                               .replace("-", "");
        if (cardNumber.length() != 16) {
            status = CardStatus.INVALID;
        }
        else if (! cardNumber.startsWith(this.bank_id)) {
            status = CardStatus.UNRECOGNIZED;
        }


        return status;
    }

    public String getName() {
        return name;
    }

    public void printAccounts() {
        for (Account acct : this.accounts.values()) {
            System.out.println(acct);
        }
    }


}