package ru.titov.s02.service;

import org.apache.commons.codec.digest.DigestUtils;
import ru.titov.s02.dao.domain.Person;
import ru.titov.s02.dao.domain.PersonDao;


public class NewPerson {

    private String toMD5(String start) {
        return DigestUtils.md5Hex(start);
    }

    public Person createNewPerson(String mail, String password, String nick, String fullName) {

        if (! new CheckPerson().checkMail(mail)) {
            Person person = new Person();
            setPerson(person, mail, toMD5(password), nick, fullName);
            PersonDao personDao = new PersonDao();

            return  personDao.insert(person);
        }
        return null;
    }

    private void setPerson(Person person, String mail, String password, String nick, String fullName) {
        person.setMail(mail);
        person.setPassword(password);
        person.setNick(nick);
        person.setFullName(fullName);
    }
}
