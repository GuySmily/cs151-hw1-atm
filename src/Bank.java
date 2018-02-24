import java.time.LocalDate;

public class Bank {
    private String name;
    private String bank_id;

    public Bank(String bankName, String IIN){
        name = bankName;
        bank_id = IIN;
    }
    public enum CardStatus {
        UNRECOGNIZED, EXPIRED, VALID, INVALID
    }
    // Since an account is limited to one card, we'll treat them as the same.
    // If a customer loses a card, they must close their old account/card and create a new one.
    public Card openAccount(Customer cust, double initialDeposit) {
        Account acct = new Account(cust, initialDeposit);

        LocalDate expr = LocalDate.now().plusYears(3);
        card = new Card(bank_id, acct.getNumber(), expr);

        acct.assignCard(card.getNumber());

        return card;
    }

    public ATM buildATM(int withdrawalLimit) {
        return new ATM(this, withdrawalLimit);
    }

    /* ****************************************************************
     * Transaction validation functions
     * ****************************************************************
     * These functions are used by ATM throughout the transaction process.
     */


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
}


}