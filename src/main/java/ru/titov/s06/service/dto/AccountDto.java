package ru.titov.s04.service.dto;


import ru.titov.s04.service.CurrencyService;

import java.math.BigDecimal;


public class AccountDto {
    private int id = -11;
    private int numberAccount;
    private int personId;
    private BigDecimal balance;
    private int currencyId;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberAccount() {
        return numberAccount;
    }

    public void setNumberAccount(int numberAccount) {
        this.numberAccount = numberAccount;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return  "id = " + id + "; number account = " + numberAccount + "; person ID = " + personId + "; balance = " + balance +
                "; currency = " + currencyId + "; description = " + description + ";";
    }
}
