package ru.titov.s02.view.console;

import ru.titov.s02.dao.domain.Person;
import ru.titov.s02.dao.domain.PersonDao;
import ru.titov.s02.service.NewPerson;

import java.util.Scanner;

public class Talking {

    public void createUser() {
        User user = new User();
        if (user.createUser()) {
            NewPerson newPerson = new NewPerson();
            Person person =  newPerson.createNewPerson(user.getMail(), user.getPassword(), user.getNick(), user.getFullName());

            if (person != null) {
                System.out.println("New user " + user.getMail() + " successfully created!");
                return;
            }
            else {
                System.out.println("Возможно данный e-mail " + user.getMail() + " уже зарегистрирован!" +
                        "\n Повторная регистрация запрещена!");
            }
        }
        else {
            System.out.println("Что то пошло не так!");
        }
    }

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

                new Talking().createUser();
            }

            if (str.equalsIgnoreCase("2")) {
                Person person = new User().isExistUser();
                     if (person != null) {

                }
                else {
                    System.out.println("Неверный e-mail or password!");
                }

            }

        }


    }
}
