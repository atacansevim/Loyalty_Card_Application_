package com.example.loyalty_card_application;

public class CardTransactionElement {
    private String Date;
    private String Amount;

    public CardTransactionElement(String date, String amount) {
        Date = date;
        Amount = amount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
