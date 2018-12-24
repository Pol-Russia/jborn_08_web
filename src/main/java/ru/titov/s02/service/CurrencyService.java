package ru.titov.s02.service;

import ru.titov.s02.dao.CurrencyDao;
import ru.titov.s02.dao.domain.Currency;

import java.util.List;

public class CurrencyService {

    private final CurrencyDao currencyDao = new CurrencyDao();

    public Currency createNewCurrency(Currency currency) {

        if (currency != null && ! currency.getNameOfCurrency().isEmpty() && (checkNameOfCurrency(currency))) {//Если валюта
            //уже существует она не будет создана повторно

            return currencyDao.insert(currency);
        }
        return null;
    }

    public List<Currency> downloadListCurrency() {

        return currencyDao.findByAll();
    }

    public boolean checkNameOfCurrency(Currency currency) {

        List<Currency> list = downloadListCurrency();
        int size = list.size();


        for (int i = 0; i < size; i++) {

            if (currency.getNameOfCurrency().equalsIgnoreCase(list.get(i).getNameOfCurrency())) {
                return false;

            }
        }
        return true;
    }

    public Currency updateCurrency(Currency currency) {

        Currency updateCurrency;
        updateCurrency = currencyDao.findById(currency.getId());

            if (updateCurrency != null && ! currency.getNameOfCurrency().isEmpty() && (checkNameOfCurrency(currency))) {

                updateCurrency.setNameOfCurrency(currency.getNameOfCurrency());
                return  currencyDao.update(updateCurrency);
            }


            return null;
    }

    public boolean deleteCurrency(Currency currency) {

        return currencyDao.delete(currency.getId());
    }
}
