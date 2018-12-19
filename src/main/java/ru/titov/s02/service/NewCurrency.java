package ru.titov.s02.service;

import ru.titov.s02.dao.domain.Currency;
import ru.titov.s02.dao.domain.CurrencyDao;



public class NewCurrency {

        private Currency createNewCurrency(String nameCurrency) {

            if (nameCurrency != null && ! nameCurrency.isEmpty()) {
                CurrencyDao currencyDao = new CurrencyDao();
                Currency currency = new Currency();


                currency.setNameOfCurrency(nameCurrency);

                return currencyDao.insert(currency);
            }

            return null;
            }
        }
