import java.time.LocalDate;

public class Card {
    private String bank_id;
    private String accountNumber;
    private String cardNumber;
    private LocalDate expiration;

    public Card(String bank_id, long acct, String IIN, long IAI, LocalDate expiration) {
        this.bank_id = bank_id;
        this.accountNumber = String.valueOf(acct);

        String temp = IIN + String.valueOf(IAI);          // Concat the main parts of the card number together
        temp += String.valueOf(luhn(Long.valueOf(temp))); // Concat the checksum to the end of the card number
        this.cardNumber = String.valueOf(temp);           // Store final result (String)

        this.expiration = expiration;

    }

    public String getNumber() {
        return cardNumber;
    }

    public boolean isExpired() {
        return expiration.isBefore(LocalDate.now());
    }

    public String toString() {
        return "Card Number: " + cardNumber + "; Exp: " + expiration;
    }

    public long luhn(long value) {
        // Ran out of time.  Just pretend for now.
        return value % 10;
    }
}