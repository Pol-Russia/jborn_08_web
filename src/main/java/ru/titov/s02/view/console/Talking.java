package ru.titov.s02.view.console;

import ru.titov.s02.dao.domain.Account;
import ru.titov.s02.dao.domain.Person;
import ru.titov.s02.service.converters.UserConverter;
import ru.titov.s02.view.dto.AccountDto;
import ru.titov.s02.view.dto.UserDto;

import java.util.List;
import java.util.Scanner;

public class Talking {



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Person person = null;


        while (true) {

            System.out.println("Please press 1, if you new user");
            System.out.println("Please press 2 if you already registered here");
            System.out.println("for exit press q or Q");
            //Отображение и создание типов счетов
            //
            //Отображение и создание категорий транзакций
            //Реализовать действие - вывести список счетов

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
                    System.out.println("Please press \"2\" if you wish сreate new description your account");
                    System.out.println("Please press \"3\" for your account");
                    System.out.println("for exit press \"q\" or \"Q\"");

                    if (str.equalsIgnoreCase("q")) {
                        System.out.println("good buy!");
                        break;
                    }
                    if (str.equalsIgnoreCase("2")) {


                    }
                    else if (str.equalsIgnoreCase("1")) {

                        AccountView accountView = new AccountView();
                        AccountDto accountDto = accountView.createAccountDto(userDto);

                        if (accountDto != null) {
                            Account account = accountView.createNewAccount(accountDto);
                        }



                    }
                }


            }
            if (str.equalsIgnoreCase("2")) {

                Person p = new UserView().isExistUser();
                if (person != null) {

                    System.out.println("Проверка прошла успешно!");
                }

                System.out.println("Please press \"1\", if you wish create new account");
                System.out.println("Please press \"2\" if you wish сreate new description your account");
                System.out.println("Please press \"3\" for your account");
                System.out.println("for exit press \"q\" or \"Q\"");

                str = scanner.nextLine();

                if (str.equalsIgnoreCase("q")) {
                    System.out.println("good buy!");
                    break;
                }
                if (str.equalsIgnoreCase("2")) {

                    AccountView accountView = new AccountView();
                    List<Account> ac = accountView.ShowAccount(new UserConverter().personToUserDtoConvert(p));
                    for (Account a : ac) {
                        System.out.println("PersonID: " + a.getPersonID());
                        System.out.println("Description: " + a.getDescription());
                    }

                    //CategorieDto categorieDto = new CategorieDto();
                    //Categorie categorie = categorieDto.createCategorie();
                    //CurrencyDto currencyDto = new CurrencyDto();
                    //Currency currency = currencyDto.createCurrency();


                }
            }
        }
    }
}




