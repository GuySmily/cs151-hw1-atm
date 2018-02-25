import java.time.LocalDate;

public class Card {
    private String bank_id;
    private String cardNumber;
    private LocalDate expiration;

    public Card(String bank_id, String cardNumber, LocalDate expiration) {
        this.bank_id = bank_id;
        this.cardNumber = cardNumber;
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
}