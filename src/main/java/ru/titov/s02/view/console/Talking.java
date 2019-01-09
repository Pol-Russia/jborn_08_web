package ru.titov.s02.view.console;

import ru.titov.s02.dao.domain.Account;
import ru.titov.s02.dao.domain.Categorie;
import ru.titov.s02.dao.domain.Currency;
import ru.titov.s02.dao.domain.Transaction;
import ru.titov.s02.service.CurrencyService;
import ru.titov.s02.service.converters.UserConverter;
import ru.titov.s02.service.dto.*;

import java.math.BigDecimal;
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

    public AccountDto newAccount(UserDto userDto) throws MaxCountAccountException {
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






    public static void main(String[] args) throws MaxCountAccountException {
        Scanner scanner = new Scanner(System.in);
        UserDto person = null;


        while (true) {

            System.out.println("Please press 1, if you new user");
            System.out.println("Please press 2 if you already registered here");
            System.out.println("for exit press q or Q");

            String str = scanner.nextLine();
            if (str.equalsIgnoreCase("q")) {
                System.out.println("good buy!");
                break;
            }
            if (str.equals("1")) {

                UserView userView = new UserView();
                UserDto userDto = userView.createUserDto();


                if (userDto != null) {
                    person = userView.createNewPerson(userDto);
                }

                if (person != null) {

                    System.out.println("Please press \"1\", if you wish create new account");
                    System.out.println("Please press \"2\" if you wish сreate new description your transaction");
                    System.out.println("Please press \"3\" if you wish сreate new currency");
                    System.out.println("for exit press \"q\" or \"Q\"");
                    str = scanner.nextLine();


                    if (str.equalsIgnoreCase("q")) {
                        System.out.println("good buy!");
                        break;
                    }

                    if (str.equalsIgnoreCase("2")) {

                        CategorieView categorieViewView = new CategorieView();
                        CategorieDto categorieDto = categorieViewView.createCategorieDto();

                        if (categorieDto != null) {
                            CategorieDto categorie = categorieViewView.createNewCategorie(categorieDto);
                        }
                    }

                    if (str.equalsIgnoreCase("1")) {

                        AccountView accountView = new AccountView();
                        AccountDto accountDto = accountView.createAccountDto(userDto);

                        if (accountDto != null) {
                            AccountDto account = accountView.createNewAccount(accountDto);
                        }
                    }

                    if (str.equalsIgnoreCase("3")) {

                        CurrencyView currencyView = new CurrencyView();
                        CurrencyDto currencyDto = currencyView.createCurrencyDto();

                        if (currencyDto != null) {
                            CurrencyDto currency = currencyView.createNewCurrency(currencyDto);
                        }

                    }



                }


            }
            if (str.equalsIgnoreCase("2")) {

                UserDto userDto = new UserView().isExistUser();

                if (userDto != null) {
                    System.out.println("Проверка прошла успешно!");

                }

                while (true) {

                System.out.println("Please press \"1\", for view all your accounts ");
                System.out.println("Please press \"2\" if you wish сreate new description your transaction");
                System.out.println("Please press \"3\" if you wish create new account");
                System.out.println("Please press \"4\" if you wish сreate new currency");
                System.out.println("Please press \"5\" for view all currency");
                System.out.println("Please press \"6\" for view all transaction categorie");
                System.out.println("Please press \"7\" for view all transaction");
                System.out.println("Please press \"8\" Положить на счет");
                System.out.println("Please press \"9\" Снять со счета");
                System.out.println("Please press \"9\" Перевести со счета на счет");
                System.out.println("for exit press \"q\" or \"Q\"");

                str = scanner.nextLine().trim();

                if (str.equalsIgnoreCase("q")) {

                    System.out.println("good buy!");
                    break;
                }
                if (str.equalsIgnoreCase("1")) {

                    AccountView accountView = new AccountView();
                    List<AccountDto> ac = accountView.ShowAccount(userDto);
                    for (AccountDto a : ac) {
                        System.out.println(a.toString());
                    }
                }
                if (str.equalsIgnoreCase("2")) {

                    CategorieView categorieView = new CategorieView();
                    CategorieDto categorieDto = categorieView.createCategorieDto();

                    if (categorieDto != null) {
                        CategorieDto categorie = categorieView.createNewCategorie(categorieDto);
                    }
                }

                if (str.equalsIgnoreCase("3")) {
                    AccountView accountView = new AccountView();
                    AccountDto accountDto = accountView.createAccountDto(userDto);

                    if (accountDto != null) {
                        AccountDto account = accountView.createNewAccount(accountDto);
                    }
                }

                if (str.equalsIgnoreCase("4")) {

                    CurrencyView currencyView = new CurrencyView();
                    CurrencyDto currencyDto = currencyView.createCurrencyDto();

                    if (currencyDto != null) {
                        CurrencyDto currency = currencyView.createNewCurrency(currencyDto);
                    }

                }

                if (str.equalsIgnoreCase("5")) {

                    CurrencyView currencyView = new CurrencyView();
                    List<CurrencyDto> currencyDto = currencyView.findAllCurrencyDto();

                    for (CurrencyDto dto : currencyDto) {
                        System.out.println(dto.toString());
                    }
                }

                if (str.equalsIgnoreCase("6")) {

                    CategorieView categorieView = new CategorieView();
                    List<CategorieDto> categorieDto = categorieView.findAllCategorieDto();

                    for (CategorieDto dto : categorieDto) {
                        System.out.println(dto.toString());
                    }
                }

                    if (str.equalsIgnoreCase("7")) {

                        TransactionView transactionView = new TransactionView();
                        List<TransactionDto> transactionDto = transactionView.ShowAllTransaction(userDto);

                        for (TransactionDto dto : transactionDto) {
                            System.out.println(dto.toString());
                        }
                    }

                    if (str.equalsIgnoreCase("8")) {

                        //ПОложить на счет
                        System.out.println("Выберите номер счета");
                        Currency currency;

                        AccountView accountView = new AccountView();
                        int count = 0;
                        List<AccountDto> account = accountView.ShowAccount(userDto);
                        for (AccountDto a : account) {
                            count++;
                            currency = new CurrencyService().findById(a.getCurrencyId());
                            System.out.println("Please press \"" + count + "\" if you choose --> " + a.getNumberAccount() +" " + currency.getNameOfCurrency());
                        }

                        count = scanner.nextInt();
                        AccountDto accountDto = account.get(count - 1); // Выбрал номер счёта

                        //System.out.println("Please print your sum:");
                        //BigDecimal sum = new BigDecimal(scanner.nextLine());

                        //Теперь транзакция!
                        TransactionView transactionView = new TransactionView();
                        TransactionDto transactionDto = transactionView.createTransactionDto(accountDto);

                        if (transactionDto != null) {
                            TransactionDto transaction = transactionView.createNewTransaction(transactionDto);
                        }

                    }
            }

                }



                }
            }
        }






