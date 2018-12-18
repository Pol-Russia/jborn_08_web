package ru.titov.s02.service;

import ru.titov.s02.dao.domain.Currency;
import ru.titov.s02.dao.domain.CurrencyDao;

public class UpdateCurrency {

    private Currency update(int id, String nameCurrency) {

        Currency currency;
        CurrencyDao currencyDao = new CurrencyDao();
        currency = currencyDao.findById(id);

        if (currency != null) {

            if (nameCurrency != null && ! nameCurrency.isEmpty()) {
                currency.setNameOfCurrency(nameCurrency);
            }


            currencyDao.update(currency);

        }
        return currency;
    }
}
