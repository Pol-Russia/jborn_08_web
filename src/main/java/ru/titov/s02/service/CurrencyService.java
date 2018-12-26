package ru.titov.s02.service;

import ru.titov.s02.dao.CurrencyDao;
import ru.titov.s02.dao.domain.Currency;
import ru.titov.s02.service.converters.CurrencyConverter;
import ru.titov.s02.service.dto.CurrencyDto;

import java.util.List;

public class CurrencyService {

    private final CurrencyDao currencyDao = new CurrencyDao();

    public CurrencyDto createNewCurrency(CurrencyDto currencyDto) {

        if (currencyDto != null && ! currencyDto.getNameCurrency().isEmpty() && (checkNameOfCurrency(currencyDto))) {//Если валюта
            //уже существует она не будет создана повторно

            Currency currency = new CurrencyConverter().currencyDtoToCurrencyConvert(currencyDto);
            currency = currencyDao.insert(currency);

            if (currency != null) {
                currencyDto = new CurrencyConverter().currencyToCurrencyDtoConvert(currency);
                return currencyDto;
            }
        }
        return null;
    }

    public List<CurrencyDto> downloadListCurrency() {

        return new CurrencyConverter().listCurrencyToListCurrencyDtoConvert(currencyDao.findByAll());
    }

    public boolean checkNameOfCurrency(CurrencyDto currencyDto) {

        Currency currency = new CurrencyConverter().currencyDtoToCurrencyConvert(currencyDto);
        currency = currencyDao.findByNameCurrency(currency);

        if (currency != null) {
            return false;
        }

        return true;
    }


    public CurrencyDto updateCurrency(CurrencyDto currencyDto) {

        Currency updateCurrency;
        updateCurrency = currencyDao.findById(currencyDto.getId());

            if (updateCurrency != null &&  (checkNameOfCurrency(currencyDto))) {

                updateCurrency = new CurrencyConverter().currencyDtoToCurrencyConvert(currencyDto);
                updateCurrency =  currencyDao.update(updateCurrency);

                return new CurrencyConverter().currencyToCurrencyDtoConvert(updateCurrency);
            }


            return null;
    }

    public boolean deleteCurrency(CurrencyDto currencyDto) {

        return currencyDao.delete(currencyDto.getId());
    }
}
