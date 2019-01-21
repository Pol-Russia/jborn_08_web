package ru.titov.s04.service;

import ru.titov.s04.dao.CurrencyDao;
import ru.titov.s04.dao.domain.Currency;
import ru.titov.s04.service.converters.CurrencyConverter;
import ru.titov.s04.service.dto.CurrencyDto;

import java.util.List;

public class CurrencyService {
    private final CurrencyDao currencyDao;
    private final CurrencyConverter currencyConverter;

    public CurrencyService(CurrencyDao currencyDao, CurrencyConverter currencyConverter){
            this.currencyDao = currencyDao;
            this.currencyConverter = currencyConverter;
        }


    public CurrencyDto createNewCurrency(CurrencyDto currencyDto) {

        if (currencyDto != null && ! currencyDto.getNameCurrency().isEmpty() && (checkNameOfCurrency(currencyDto))) {//Если валюта
            //уже существует она не будет создана повторно

            Currency currency = currencyConverter.currencyDtoToCurrencyConvert(currencyDto);


            currency = currencyDao.insert(currency);

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

    public boolean checkNameOfCurrency(CurrencyDto currencyDto) {

        Currency currency = currencyConverter.currencyDtoToCurrencyConvert(currencyDto);
        currency = currencyDao.findByNameCurrency(currency);

        if (currency == null) {
            return true;
        }

        return false;
    }


    public CurrencyDto updateCurrency(CurrencyDto currencyDto) {

        Currency updateCurrency;
        updateCurrency = currencyDao.findById(currencyDto.getId());

            if (updateCurrency != null &&  (checkNameOfCurrency(currencyDto))) {

                updateCurrency = currencyConverter.currencyDtoToCurrencyConvert(currencyDto);
                updateCurrency =  currencyDao.update(updateCurrency);

                return currencyConverter.currencyToCurrencyDtoConvert(updateCurrency);
            }


            return null;
    }

    public boolean deleteCurrency(CurrencyDto currencyDto) {

        return currencyDao.delete(currencyDto.getId());
    }
}
