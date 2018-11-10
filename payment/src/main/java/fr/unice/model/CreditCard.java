package fr.unice.model;

public class CreditCard {

    private String cardNumber;
    private String expirationDate;
    private String cardSecurityCode;

    public CreditCard(String cardNumber, String expirationDate, String cardSecurityCode) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cardSecurityCode = cardSecurityCode;
    }

    public CreditCard() {
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCardSecurityCode() {
        return cardSecurityCode;
    }

    public void setCardSecurityCode(String cardSecurityCode) {
        this.cardSecurityCode = cardSecurityCode;
    }

}
