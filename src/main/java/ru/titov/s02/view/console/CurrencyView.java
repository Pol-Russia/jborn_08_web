package ru.titov.s02.view.console;

import ru.titov.s02.dao.domain.Currency;
import ru.titov.s02.service.CurrencyService;
import ru.titov.s02.service.converters.CurrencyConverter;
import ru.titov.s02.view.dto.CurrencyDto;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyView {
    private final CurrencyService service = new CurrencyService();

    public Currency createNewCurrency(CurrencyDto currencyDto) {

            Currency currency = new CurrencyConverter().currencyDtoToCurrencyConvert(currencyDto);


            if (! service.checkNameOfCurrency(currency)) {

                System.out.println("Возможно данная валюта " + currencyDto.getNameCurrency() + " уже зарегистрирована!" +
                        "\n Повторная регистрация запрещена!");
                return null;
            }

            if (service.createNewCurrency(currency) != null) {
                System.out.println("New currency " + currencyDto.getNameCurrency() + " successfully created!");
                return currency;
        } else {
            System.out.println("Что то пошло не так!");
            return null;
        }
    }

    public CurrencyDto createCurrencyDto() {

        System.out.println("Please print your name of currency and press <Enter>");
        Scanner scanner = new Scanner(System.in);
        String nameCurrency = scanner.nextLine().trim();

        if (validateCurrency(nameCurrency)) {

            CurrencyDto currencyDto = new CurrencyDto();
            currencyDto.setId(-11);
            currencyDto.setNameCurrency(nameCurrency);
            return currencyDto;


        }

        return null;
    }

    public boolean validateCurrency(String nameCurrency) {
        if (nameCurrency != null && nameCurrency.length() > 15 || nameCurrency.length()  < 1) {
            System.out.println("name of currency must contain from 1 to 15 letters!");
            return false;
        }

        Pattern p = Pattern.compile("[$]?[a-zA-Zа-яА-Я]*");
        Matcher m = p.matcher(nameCurrency);
        return m.matches();
    }

    public List<Currency> findAllCurrencyDto() {

        List<Currency> list = service.downloadListCurrency();

        if (list != null && list.size() >0 ) {
            return list;
        }
        return null;
    }


}
