package ru.titov.s02.service;

import org.apache.commons.codec.digest.DigestUtils;
import ru.titov.s02.dao.domain.Person;
import ru.titov.s02.dao.domain.PersonDao;

import java.util.List;

public class CheckPerson {

    private String toMD5(String start) {
        return DigestUtils.md5Hex(start);
    }

    private List<Person> downloadListPerson() {
        PersonDao personDao = new PersonDao();
        return personDao.findByAll();
    }

    boolean checkMail(String mail) {

        List<Person> list = downloadListPerson();
        int size = list.size();

        for (int i = 0; i < size; i++) {
            if (mail.equalsIgnoreCase(list.get(i).getMail())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkPassword(String password, String mail) {

        PersonDao personDao = new PersonDao();
        Person person = personDao.findByMail(mail);

        if (person != null) {
            if (toMD5(password).equals(person.getPassword())) {
                return true;
            }
        }

        return false;
    }

    boolean checkNickName(String nick) {

        List<Person> list = downloadListPerson();
        int size = list.size();

        for (int i = 0; i < size; i++) {
            if (nick.equalsIgnoreCase(list.get(i).getNick())) {
                return true;
            }
        }
        return false;
    }

}
