package ru.titov.s02.service;

import ru.titov.s02.dao.domain.Currency;
import ru.titov.s02.dao.domain.CurrencyDao;
import java.util.List;

public class CheckCurrency {
    private List<Currency> downloadListCurrency() {
        CurrencyDao currencyDao = new CurrencyDao();
        return currencyDao.findByAll();
    }

    public boolean checkDescription(String description) {

        List<Currency> list = downloadListCurrency();
        int size = list.size();


        for (int i = 0; i < size; i++) {
            if (description.equalsIgnoreCase(list.get(i).getNameOfCurrency())) {
                return false;

            }
        }
        return true;
    }
}
