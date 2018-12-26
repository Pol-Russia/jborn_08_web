package ru.titov.s02.view.console;

import ru.titov.s02.dao.domain.Account;
import ru.titov.s02.dao.domain.Currency;
import ru.titov.s02.dao.domain.Person;
import ru.titov.s02.service.AccountService;
import ru.titov.s02.service.converters.AccountConverter;
import ru.titov.s02.service.converters.UserConverter;
import ru.titov.s02.service.dto.AccountDto;
import ru.titov.s02.service.dto.CurrencyDto;
import ru.titov.s02.service.dto.UserDto;


import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountView {

        private Scanner scanner = new Scanner(System.in);


        public AccountDto createNewAccount(AccountDto accountDto) {


        if (new AccountService().createNewAccount(accountDto) != null) {
            System.out.println("New account number =" + accountDto.getNumberAccount() + " successfully created!");
            return accountDto;

        } else {
               System.out.println("Не удалось создать счёт! Проверьте данные! ");
               return null;
        }
    }


        public AccountDto createAccountDto(UserDto userDto) {
            System.out.println("Please print your number account and press <Enter>");
            int numberAccount = scanner.nextInt();
            AccountDto accountDto = new AccountDto();
            accountDto.setPersonId(userDto.getId());
            accountDto.setBalance(BigDecimal.valueOf(0));
            accountDto.setId(-11);

            while (true) {
                if (validateNumberAccount(numberAccount)) {
                    System.out.println("Please print your account description and press <Enter>");
                    accountDto.setNumberAccount(numberAccount);
                    String description = scanner.nextLine().trim();

                    while (true) {
                        if (validateDescription(description)) {
                            accountDto.setDescription(description);

                            //Вывести список имеющихся валют
                            // Либо создать свою валюту
                            List<CurrencyDto> list = new CurrencyView().findAllCurrencyDto();
                            int count = 1;
                            if (list == null) {
                                //Создаю валюту
                            }
                            for (CurrencyDto currency : list) {
                                System.out.println("please press " + count + " for choose " + currency.getNameCurrency());
                            }
                            System.out.println("please press 0 for create new currency ");
                            System.out.println("for exit press q or Q");
                            String value = scanner.nextLine().trim();

                            if (value.equalsIgnoreCase("q")) {
                                System.out.println("Вы выбрали завершение операции!");
                                return null;
                            }
                            else {
                                int number = Integer.parseInt(value);
                                if (number == 0) {
                                    //Создать новую Валюту!

                                    CurrencyView currencyView = new CurrencyView();
                                    CurrencyDto currencyDto = new CurrencyView().createCurrencyDto();
                                    CurrencyDto currency = currencyView.createNewCurrency(currencyDto);

                                    if (currency != null) {
                                        accountDto.setCurrencyId(currency.getId());
                                        return accountDto;
                                    }
                                }
                                else {
                                    accountDto.setCurrencyId(list.get(number - 1).getId());
                                    return accountDto;
                                }
                            }
                        }
                        else {
                            System.out.println("Our account description is not valid. Please try again");
                            System.out.println("for exit press q or Q");
                            description = scanner.nextLine().trim();

                            if (description.equalsIgnoreCase("q")) {
                                return null;
                            }
                        }
                    }

                } else {
                    System.out.println("Our number account is not valid. Please try again");
                    System.out.println("for exit press 0");
                    numberAccount = scanner.nextInt();

                    if (numberAccount == 0) {
                        return null;
                    }
                }
            }
        }

        private boolean validateNumberAccount(int numberAccount) {
        if (String.valueOf(numberAccount).length() == 8) { //Номер счета 8 цифр всегда, можно заморочиться и проверить
            // префикс или сделать 12 и т д

            return true;
        }
        System.out.println("Number account must contain from 8 numbers!");
        return false;
    }


        private boolean validateDescription(String description) {

        Pattern p = Pattern.compile("[A-Za-zА-Яа-я]+");
        Matcher m = p.matcher(description);
        return m.matches();

    }

        public List<AccountDto> ShowAccount(UserDto userDto) {

         return new AccountService().findByPesonId(userDto);
    }







}
