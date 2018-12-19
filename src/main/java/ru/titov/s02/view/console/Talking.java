package ru.titov.s02.view.console;

import ru.titov.s02.dao.domain.Categorie;
import ru.titov.s02.dao.domain.Currency;
import ru.titov.s02.dao.domain.Person;
import ru.titov.s02.service.NewPerson;

import java.util.Scanner;

public class Talking {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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

                UserDto userDto = new UserDto().newUser();

                if (userDto != null) {

                    System.out.println("Please press \"1\", if you wish create new account");
                    System.out.println("Please press \"2\" if you wish сreate new description your account");
                    System.out.println("Please press \"3\" for your account");
                    System.out.println("for exit press \"q\" or \"Q\"");

                    if (str.equalsIgnoreCase("q")) {
                        System.out.println("good buy!");
                        break;
                    }
                    if (str.equalsIgnoreCase("2")) {

                        CategorieDto categorieDto = new CategorieDto();
                        categorieDto.createCategorie();

                    }
                }



            }
            if (str.equalsIgnoreCase("2")) {
                Person person = new UserDto().isExistUser();
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
                        СurrencyDto currencyDto = new  СurrencyDto();


                        if (currencyDto != null) {
                            System.out.println("Категория " + currencyDto + " успешно создана.");
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
