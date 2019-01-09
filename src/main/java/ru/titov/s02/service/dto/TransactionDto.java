package ru.titov.s02.service.dto;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionDto {
    private int id = -11;
    private  int accountID;
    private BigDecimal sum;
    private String date;
    private int categorieID;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCategorieID() {
        return categorieID;
    }

    public void setCategorieID(int categorieID) {
        this.categorieID = categorieID;
    }

    @Override
    public String toString() {
        return "Id = " + getId() + "; " + "Sum = " + getSum() + "; " + "Date: " + getDate() + "; " + "categorie - " + getCategorieID()  + "; ";
    }
}
