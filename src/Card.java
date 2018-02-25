import java.time.LocalDate;

public class Card {
    private String bank_id;
    private int cardNumber;
    private LocalDate expiration;

    public Card(String bank_id, int cardNumber, LocalDate expiration) {
        this.bank_id = bank_id;
        this.cardNumber = cardNumber;
        this.expiration = expiration;
    }

    public String toString() {
        return "Card Number: " + cardNumber + "; Exp: " + expiration;
    }
}