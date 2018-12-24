package ru.titov.s02.service.converters;

import ru.titov.s02.dao.domain.Currency;
import ru.titov.s02.view.dto.CurrencyDto;

public class CurrencyConverter {

    public Currency currencyDtoToCurrencyConvert(CurrencyDto currencyDto) {

        if (currencyDto != null) {

            Currency currency = new Currency();
            currency.setId(currencyDto.getId());
            currency.setNameOfCurrency(currency.getNameOfCurrency());

            return currency;
        }

        return null;
    }


    public CurrencyDto currencyToCurrencyDtoConvert(Currency currency) {

        if (currency != null) {

            CurrencyDto currencyDto = new CurrencyDto();
            currencyDto.setId(currency.getId());
            currencyDto.setNameCurrency(currency.getNameOfCurrency());

            return currencyDto;
        }

        return null;
    }



}
