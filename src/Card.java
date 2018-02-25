import java.time.LocalDate;

public class Card {
    private String bank_id;
    private String accountNumber;
    private String cardNumber;
    private LocalDate expiration;

    /**
     * Debit Card which allows a customer to access their funds at a Bank
     * @param bank_id Note: this value is redundant with IIN
     * @param acct Account number this card identifies
     * @param IIN First 6 digits of card number
     * @param IAI Remaining digits of card number except last digit, which will be calculated
     * @param expiration Date this card becomes unacceptable for transactions
     */
    public Card(String bank_id, long acct, String IIN, long IAI, LocalDate expiration) {
        this.bank_id = bank_id;
        this.accountNumber = String.valueOf(acct);

        String temp = IIN + String.valueOf(IAI);          // Concat the main parts of the card number together
        temp += String.valueOf(luhn(Long.valueOf(temp))); // Concat the checksum to the end of the card number
        this.cardNumber = String.valueOf(temp);           // Store final result (String)

        this.expiration = expiration;

    }

    /**
     * Returns card number in string format.
     * @return card number in string format.
     */
    public String getNumber() {
        return cardNumber;
    }

    /**
     * Returns true if this card is expired.
     * @return if this card is expired or not.
     */
    public boolean isExpired() {
        return expiration.isBefore(LocalDate.now());
    }

    /**
     * Creates nice string for printing card information
     * @return string with card information
     */
    public String toString() {
        return "Card Number: " + cardNumber + "; Exp: " + expiration;
    }

    /**
     * Calculates checksum for card, called Luhn Algorithm
     * @param value All digits of the card number (except the last digit, which this function calculates)
     * @return Checksum for given card number.
     */
    public long luhn(long value) {
        // Ran out of time.  Just pretend for now.
        return value % 10;
    }
}