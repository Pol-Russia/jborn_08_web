package ru.titov.s02.view.console;

import ru.titov.s02.dao.domain.*;
import ru.titov.s02.service.CheckAccount;
import ru.titov.s02.service.NewAccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AccountDto {
    private int id;
    private int numberAccount;
    private int personId;
    private BigDecimal balance;
    private int currencyId;
    private String description;
    private Person person;

    public AccountDto(Person person) {
        this.person = person;
    }

    public int getId() {
        return id;
    }

    public int getNumberAccount() {
        return numberAccount;
    }

    public int getPersonId() {
        return personId;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public String getDescription() {
        return description;
    }

    private boolean setCurrencyId(int choose) {
        if (choose == 0) {
            CurrencyDto currencyDto = new CurrencyDto();
            Currency currency = currencyDto.createCurrency();

            if (currency != null) {
                this.currencyId = currency.getId();
                return true;
            }
        } else {
            this.currencyId = choose;
            return true;
        }
        return false;
    }


    public Account createAccount() {
       // new PersonDao().
        if (person != null && person.getListAccount(person.getId()).size() == 5) {
            System.out.println("В системе запрещено иметь более 5-ти счетов одной персоне! Ваш лимит исчерпан!");
            return null;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please print your number account and press <Enter>");
        int numberAccount = scanner.nextInt();

        CheckAccount checkAccount = new CheckAccount();
        if (!checkAccount.checkNumberAccount(numberAccount)) {
            //Не прошел проверку на уникальность, хотя в БД не уникальный
            return null;
        }


        personId = person.getId();
        balance = BigDecimal.valueOf(0);


        while (true) {
            if (validateNumberAccount(numberAccount)) {
                //Вывести список Валют
                System.out.println("Please choose your currency and press <Enter>");

                CurrencyDao currencyDao = new CurrencyDao();
                List<Currency> listCurrency = currencyDao.findByAll();

                for (Currency currency : listCurrency) {
                    System.out.println("press " + currencyId + " for choose " + currency.getNameOfCurrency());
                }

                int choose;
                do {
                    System.out.println("If you need new currency press 0 ");
                    choose = scanner.nextInt();
                }
                while (setCurrencyId(choose));// Добавил валюту

                //Добавить описание счёта
                System.out.println("print your account description if you want and press <>");
                String description = scanner.nextLine().trim();

                if (validateDescription(description)) {
                    this.description = description;
                } else {
                    this.description = null;
                }

                Account account = new NewAccount().createNewAccount( this.numberAccount, this.personId, this.currencyId,
                        this.description);
                return account;
            }
            else {
                System.out.println("for exit press q or Q");
                String end = scanner.nextLine().trim();

                if (end.equalsIgnoreCase("q")) {
                    return null;
                }
            }

        }

    }




    public boolean validateNumberAccount(int numberAccount) {
        if (String.valueOf(numberAccount).length() == 8) { //Номер счета 8 цифр всегда, можно заморочиться и проверить
            // префикс или сделать 12 и т д

            return true;
        }
        System.out.println("Number account must contain from 8 numbers!");
        return false;
    }

    public boolean validateDescription(String description) {

        Pattern p = Pattern.compile("[A-Za-zА-Яа-я]+");
        Matcher m = p.matcher(description);
        return m.matches();

    }




}
