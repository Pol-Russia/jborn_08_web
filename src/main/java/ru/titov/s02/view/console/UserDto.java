package ru.titov.s02.view.console;

import ru.titov.s02.dao.domain.Person;
import ru.titov.s02.service.CheckPerson;
import ru.titov.s02.service.NewPerson;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDto {
    private String mail;
    private String password;
    private String nick;
    private String fullName;
    Scanner scanner = new Scanner(System.in);



    private void setMail(String mail) {
        this.mail = mail;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private void setNick(String nick) {
        this.nick = nick;
    }



    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getNick() {
        return nick;
    }

    public String getFullName() {
        return fullName;
    }


    public Person newUser() {

     UserDto user = new UserDto();

        if (user.createUser()) {
            NewPerson newPerson = new NewPerson();
            Person person =  newPerson.createNewPerson(user.getMail(), user.getPassword(), user.getNick(), user.getFullName());

            if (person != null) {
                System.out.println("New userDto " + user.getMail() + " successfully created!");
                return person;
            }
            else {
                System.out.println("Возможно данный e-mail " + user.getMail() + " уже зарегистрирован!" +
                        "\n Повторная регистрация запрещена!");
                return null;
            }
        }
        else {
            System.out.println("Что то пошло не так!");
            return null;
        }
    }



    public boolean createUser() {
        System.out.println("Please print your e-mail and press <Enter>");
        String mail = scanner.nextLine().trim();

        while (true) {
            if (validateMail(mail)) {
                System.out.println("Please print your password and press <Enter>");
                this.mail = mail;
                String password = scanner.nextLine().trim();

                while (true) {
                    if (validatePassword(password)) {
                        System.out.println("Please print your Nick name and press <Enter>");
                        this.password = password;
                        String nick = scanner.nextLine().trim();

                        while (true) {

                            if (validateNickName(nick)) {
                                System.out.println("Please print your full name and press <Enter>");
                                this.nick = nick;
                                String fullName = scanner.nextLine().trim();

                                while (true) {
                                    if (validateFullName(fullName)) {
                                        this.fullName = fullName;
                                        return true;
                                    }
                                    else {
                                        System.out.println("for exit press q or Q");
                                        fullName = scanner.nextLine().trim();

                                        if (fullName.equalsIgnoreCase("q")) {
                                            return false;
                                        }
                                    }
                                }

                            }
                            else {
                                System.out.println("for exit press q or Q");
                                nick = scanner.nextLine().trim();

                                if (nick.equalsIgnoreCase("q")) {
                                    return false;
                                }

                            }
                        }
                    }
                    else {
                        System.out.println("Our password is not valid. Please try again");
                        System.out.println("for exit press q or Q");
                        password = scanner.nextLine().trim();

                        if (password.equalsIgnoreCase("q")) {
                            return false;
                        }
                    }
                }

            } else {
                System.out.println("Our e-mail is not valid. Please try again");
                System.out.println("for exit press q or Q");
                mail = scanner.nextLine().trim();

                if (mail.equalsIgnoreCase("q")) {
                    return false;
                }
            }
        }
    }


    private boolean validateMail(String mail) {

        if (mail.length() > 60) {
            return false;
        }
        Pattern p = Pattern.compile("[a-zA-Z0-9\\-_]+@[a-zA-Z]+\\.[a-zA-Z]{2,4}");
        Matcher m = p.matcher(mail);
        return m.matches();
    }

    private boolean validatePassword(String password) {

        if (password.length() < 5) {
            System.out.println("minimum password length = 6, maximum = 15!");
            return false;
        }

        Pattern p = Pattern.compile("[a-zA-Z0-9\\-\\.\\+\\*\\/!@#$%\\^\\&\\(\\)\\{\\}\\~_\\?\"\':;,]{6,15}");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    private boolean validateNickName(String nick) {

        if (nick != null && nick.length() > 15) {
            System.out.println("Your nickname is not valid, maximum length nickName = 15!");
            return false;
        }

        return true;
    }

    private boolean validateFullName(String fullName) {

        if (fullName != null && fullName.length() > 60) {
            System.out.println("There are too many letters. Try again.");
            return false;
        }

        return true;
    }

    Person isExistUser() {
        UserDto userDto = getMailTalking();

        if (userDto != null) {
            Person person = new Person();
            person.setMail(userDto.getMail());
            person.setPassword(userDto.getPassword());

            if (new CheckPerson().checkPassword(person.getPassword(), person.getMail())) {
                System.out.println("Successfully!");
                return person;
            }
        }
        return null;
    }

    private UserDto getMailTalking() {
        System.out.println("Please print your e-mail and press <Enter>");
        String mail = scanner.nextLine().trim();

        if (validateMail(mail)) {
            System.out.println("Please print your password and press <Enter>");
            String password = scanner.nextLine().trim();

            if (validatePassword(password)) {
                UserDto userDto = new UserDto();
                userDto.setMail(mail);
                userDto.setPassword(password);
                return userDto;
            }
            else {
                System.out.println("Your password is not validate!");
                return null;
            }
        }
        else {
            System.out.println("e-mail is not correct!");
        }
        return null;
    }
}