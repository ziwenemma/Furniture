package com.example.furniture;

/**
 * paymment page
 * details of credit or debit card
 */
public class Payment {

    public Payment() {
    }

    String id, name, cardnumber, pin, expiry;

    public Payment(String id, String name, String cardnumber, String pin, String expiry) {
        this.id = id;
        this.name = name;
        this.cardnumber = cardnumber;
        this.pin = pin;
        this.expiry = expiry;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }
}


