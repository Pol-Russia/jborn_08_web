package ru.titov.s02.view.console;

import ru.titov.s02.dao.domain.Account;
import ru.titov.s02.dao.domain.Currency;
import ru.titov.s02.dao.domain.Person;
import ru.titov.s02.service.NewPerson;

import java.util.Scanner;

public class Talking {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Person user = null;

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

                user = new UserDto().newUser();

                if (user != null) {

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
                }



            }
            if (str.equalsIgnoreCase("2")) {
                Person person = new UserDto().isExistUser();
                Person p = new NewPerson().createNewPerson("igor@igor.ru", "igorigor", "IG", "Игорев Игорь Степанович" );
                if (person != null) {

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

                        //CategorieDto categorieDto = new CategorieDto();
                        //Categorie categorie = categorieDto.createCategorie();
                        //CurrencyDto currencyDto = new CurrencyDto();
                        //Currency currency = currencyDto.createCurrency();
                        AccountDto accountDto = new AccountDto(p);
                        Account account = accountDto.createAccount();


                        if (account != null) {
                            System.out.println(account.getId()+": " + account.getNumberAccount()+": " + account.getPersonID()+": " +account.getDescription());
                        }
                        else {
                            System.out.println("your categorie not created!");
                        }

                    }


                } else {
                    System.out.println("Неверный e-mail or password!");
                }

            }


        }
    }
}
