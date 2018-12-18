package ru.titov.s02.service;

import ru.titov.s02.dao.domain.Currency;
import ru.titov.s02.dao.domain.CurrencyDao;

import static ru.titov.s02.dao.DaoFactory.checkOrderId;

public class NewCurrency {

        private Currency createNewCurrency(String nameCurrency) {

            if (nameCurrency != null && ! nameCurrency.isEmpty()) {
                CurrencyDao currencyDao = new CurrencyDao();
                Currency currency = new Currency();

                String tableName = "categorie";
                int id = checkOrderId(tableName); //Получить актуальный id

                currency.setId(id);
                currency.setNameOfCurrency(nameCurrency);

                return currencyDao.insert(currency);
            }

            return null;
            }
        }
