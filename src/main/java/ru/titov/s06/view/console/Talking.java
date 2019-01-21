package ru.titov.s06.view.console;

import ru.titov.s06.service.*;
import ru.titov.s06.service.dto.*;

import java.util.List;
import java.util.Scanner;

public class Talking {

    public UserDto newUser() {
        UserView userView = new UserView();
        UserDto userDto = userView.createUserDto();

        if (userDto != null) {
            UserDto person = userView.createNewPerson(userDto);
            return person;
        }
        return null;
    }

    public UserDto isRegisteredUser() {

        UserDto person = new UserView().isExistUser();

            return person;
    }

    public CurrencyDto newCurrency() {
        CurrencyView currencyView = new CurrencyView();
        CurrencyDto currencyDto = currencyView.createCurrencyDto();

        if (currencyDto != null) {
            currencyDto = currencyView.createNewCurrency(currencyDto);
            return currencyDto;
        }
        return null;
    }

    public List<CurrencyDto> showListCurrencyDto() {
        return new CurrencyView().findAllCurrencyDto();
    }

    public CategorieDto newCategorie() {
        CategorieView categorieView = new CategorieView();
        CategorieDto categorieDto = categorieView.createCategorieDto();

        if (categorieDto != null) {
            categorieDto = categorieView.createNewCategorie(categorieDto);
            return categorieDto;
        }
        return null;
    }

    public List<CategorieDto> showCategorieDto() {
        return  new CategorieView().findAllCategorieDto();
    }

    public AccountDto newAccount(UserDto userDto) {
        AccountView accountView = new AccountView();
        AccountDto accountDto = accountView.createAccountDto(userDto);

        if (accountDto != null) {
            accountDto = accountView.createNewAccount(accountDto);
            return accountDto;
        }
        return null;
    }

    public List<AccountDto> showPersonAccount(UserDto userDto) {
        return new AccountView().ShowAccount(userDto);
    }

    public TransactionDto newTransaction(AccountDto accountDto) {
        TransactionView transactionView = new TransactionView();
        TransactionDto transactionDto = transactionView.createTransactionDto(accountDto);

        if (transactionDto != null) {
            transactionDto = transactionView.createNewTransaction(transactionDto);
            return transactionDto;
        }
        return null;
    }

    public List<TransactionDto> showTransaction(AccountDto accountDto) {
        return new TransactionView().ShowAccount(accountDto);
    }





    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final Talking talking = new Talking();

        SecurityService securityService = ServiceFactory.getSecurityService();
        UserDto userDto = securityService.authorize("Igor@iGor.ru", "igorigor");
        System.out.println(userDto.toString());


        while (true) {

            System.out.println("Please press 1, if you new user");
            System.out.println("Please press 2 if you already registered here");
            System.out.println("for exit press q or Q");
            //Отображение и создание типов счетов
            //
            //Отображение и создание категорий транзакций
            //Реализовать действие - вывести список счетов юзера
            //Реализовать перевод денежных средств с одного счета на другой используя транзакции

            String str = scanner.nextLine();
            if (str.equalsIgnoreCase("q")) {
                System.out.println("good buy!");
                break;
            }
            if (str.equals("1")) {

                userDto = talking.newUser();

                if (userDto != null) {

                    System.out.println("Please press \"1\", if you wish create new account");
                    System.out.println("Please press \"2\" if you wish сreate new description your account");
                    System.out.println("Please press \"3\" for your account");
                    System.out.println("Please press \"4\" for view list your accounts ");
                    System.out.println("for exit press \"q\" or \"Q\"");

                    if (str.equalsIgnoreCase("q")) {
                        System.out.println("good buy!");
                        break;
                    }
                    if (str.equalsIgnoreCase("1")) {

                        AccountDto accountDto = talking.newAccount(userDto);

                    }
                    if (str.equalsIgnoreCase("4")) {

                        List<AccountDto> list = talking.showPersonAccount(userDto);
                        System.out.println("list = " + list.toString());
                    }
                }


            }
            if (str.equalsIgnoreCase("2")) {


                userDto = new UserView().isExistUser();


                if (userDto != null) {
                    System.out.println("Проверка прошла успешно!");

                    System.out.println("Please press \"1\", if you wish create new account");
                    System.out.println("Please press \"4\", for view your account");
                    System.out.println("Please press \"2\" if you wish сreate new currency for your account");
                    System.out.println("Please press \"3\" if you wish сreate new transaction");
                    System.out.println("Please press \"3\" if you wish сreate new transaction categorie");
                    System.out.println("for exit press \"q\" or \"Q\"");

                    str = scanner.nextLine();

                    if (str.equalsIgnoreCase("q")) {
                        System.out.println("good buy!");
                        break;
                    }

                    if (str.equalsIgnoreCase("1")) {
                        AccountDto accountDto = talking.newAccount(userDto);

                    }

                    if (str.equalsIgnoreCase("4")) {

                        List<AccountDto> list = new Talking().showPersonAccount(userDto);
                        for (AccountDto accountDto : list) {
                            System.out.println(accountDto.toString());
                        }

                    }

                }
            }
        }

        }
    }





