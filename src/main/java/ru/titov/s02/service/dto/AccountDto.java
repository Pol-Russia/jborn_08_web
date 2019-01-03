package ru.titov.s02.service.dto;


import ru.titov.s02.service.CurrencyService;
import ru.titov.s02.service.PersonService;

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

    @Override
    public String toString() {
        return "User - " + new PersonService().findById(getPersonId()).toString() + "; " + "Number account = " + getNumberAccount() + ";" + " Balance = " + getBalance() + "; "
                + "Currency - " + new CurrencyService().findById(getCurrencyId()).getNameOfCurrency() + "; " + "Description: " + getDescription();
    }
}
