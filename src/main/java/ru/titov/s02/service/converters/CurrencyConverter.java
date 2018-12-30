package ru.titov.s02.service.converters;

import ru.titov.s02.dao.domain.Currency;
import ru.titov.s02.service.dto.CurrencyDto;

import java.util.ArrayList;
import java.util.List;

public class CurrencyConverter {

    public Currency currencyDtoToCurrencyConvert(CurrencyDto currencyDto) {

        if (currencyDto != null) {

            Currency currency = new Currency();
            currency.setId(currencyDto.getId());
            currency.setNameOfCurrency(currencyDto.getNameCurrency());

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

    public List<CurrencyDto> listCurrencyToListCurrencyDtoConvert(List<Currency> currency) {

        if (currency == null) {
            return null;
        }

        List<CurrencyDto> listCurrencyDto = new ArrayList<>();
        CurrencyConverter converter = new CurrencyConverter();

        for (Currency c : currency) {

            listCurrencyDto.add(converter.currencyToCurrencyDtoConvert(c));
        }

        return listCurrencyDto;
    }




}
