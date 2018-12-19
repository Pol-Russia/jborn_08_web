package ru.titov.s02.view.console;

import ru.titov.s02.dao.domain.Currency;
import ru.titov.s02.dao.domain.CurrencyDao;
import ru.titov.s02.service.CheckCurrency;
import ru.titov.s02.service.NewCurrency;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class СurrencyDto {
    String nameCurrency;


    public String getDescription() {
        return nameCurrency;
    }


    public Currency createCurrency() {

        System.out.println("Please print your name of currency and press <Enter>");
        Scanner scanner = new Scanner(System.in);
        String nameCurrency = scanner.nextLine().trim();

        if (validateCurrency(nameCurrency)) {

            if (new CheckCurrency().checkDescription(nameCurrency)) {

                NewCurrency newCurrency = new NewCurrency();
                Currency currency = newCurrency.createNewCurrency(nameCurrency);

                if (currency != null) {
                    System.out.println("New currency " + currency.getNameOfCurrency() + " successfully created!");
                    return currency;
                }
                else {
                    System.out.println("не удалось создать " + currency.getNameOfCurrency());
                    return null;
                }

            }
            else {
                System.out.println("This same description already exist.");
                return null;
            }

        }
        else {
            System.out.println("Что то пошло не так!");
            return null;
        }

    }


    public boolean validateCurrency(String nameCurrency) {
        if (nameCurrency != null && nameCurrency.length() > 15 || nameCurrency.length() < 1) {
            System.out.println("name of currency must contain from 1 to 15 letters!");
            return false;
        }

        Pattern p = Pattern.compile("$?[a-zA-Zа-яА-Я]*");
        Matcher m = p.matcher(nameCurrency);
        return m.matches();
    }

    public List<Currency> getNameCurrency() {
        CurrencyDao currencyDao = new CurrencyDao();
        return currencyDao.findByAll();
    }

    public String selectNameCurrency(int choose) {
        List<Currency> list = getNameCurrency();

        if (list != null && list.size() >= choose && choose > 0) {
            return list.get(choose - 1).getNameOfCurrency();
        }
        System.out.println("currency is out...");
        return null;
    }


}
