package ru.titov.s02.service.dto;



public class CurrencyDto {
    private int id = -11;
    private String nameCurrency;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameCurrency() {
        return nameCurrency;
    }

    public void setNameCurrency(String nameCurrency) {
        this.nameCurrency = nameCurrency;
    }

    @Override
    public String toString() {
        return "Id = " + getId() + "; " + " name currency: " + getNameCurrency() +"; ";
    }
}
