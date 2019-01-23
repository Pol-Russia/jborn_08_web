package ru.titov.s05.service;

import ru.titov.s05.dao.CurrencyDao;
import ru.titov.s05.dao.domain.Categorie;
import ru.titov.s05.dao.domain.Currency;
import ru.titov.s05.service.converters.CurrencyConverter;
import ru.titov.s05.service.dto.CurrencyDto;

import java.sql.Connection;
import java.util.List;

public class CurrencyService {
    private final CurrencyDao currencyDao;
    private final CurrencyConverter currencyConverter;

    public CurrencyService(CurrencyDao currencyDao, CurrencyConverter currencyConverter){
            this.currencyDao = currencyDao;
            this.currencyConverter = currencyConverter;
        }


    public CurrencyDto createNewCurrency(CurrencyDto currencyDto, Connection connection) {

        if (currencyDto != null && ! currencyDto.getNameCurrency().isEmpty() && (checkNameOfCurrency(currencyDto, connection))) {//Если валюта
            //уже существует она не будет создана повторно

            Currency currency = currencyConverter.currencyDtoToCurrencyConvert(currencyDto);


            currency = currencyDao.insert(currency, connection);

            if (currency != null) {
                currencyDto = currencyConverter.currencyToCurrencyDtoConvert(currency);
                return currencyDto;
            }
        }
        return null;
    }

    public List<CurrencyDto> downloadListCurrency() {

        return currencyConverter.listCurrencyToListCurrencyDtoConvert(currencyDao.findByAll());
    }

    public boolean checkNameOfCurrency(CurrencyDto currencyDto, Connection connection) {

        Currency currency = currencyConverter.currencyDtoToCurrencyConvert(currencyDto);
        currency = currencyDao.findByNameCurrency(currency, connection);

        if (currency == null) {
            return true;
        }

        return false;
    }


    public CurrencyDto updateCurrency(CurrencyDto currencyDto, Connection connection) {

        Currency updateCurrency;
        updateCurrency = currencyDao.findById(currencyDto.getId(), connection);

            if (updateCurrency != null &&  (checkNameOfCurrency(currencyDto, connection))) {

                updateCurrency = currencyConverter.currencyDtoToCurrencyConvert(currencyDto);
                updateCurrency =  currencyDao.update(updateCurrency, connection);

                return currencyConverter.currencyToCurrencyDtoConvert(updateCurrency);
            }


            return null;
    }

    public boolean deleteCurrency(CurrencyDto currencyDto, Connection connection) {

        return currencyDao.delete(currencyDto.getId(), connection);
    }
}
