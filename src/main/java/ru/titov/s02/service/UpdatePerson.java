package ru.titov.s02.service;

import org.apache.commons.codec.digest.DigestUtils;
import ru.titov.s02.dao.domain.Person;
import ru.titov.s02.dao.domain.PersonDao;

public class UpdatePerson {

    private String toMD5(String start) {
        return DigestUtils.md5Hex(start);
    }

    private Person update(int id, String mail, String password, String nickName, String fullName) {
        Person person;
        PersonDao personDao = new PersonDao();
        person = personDao.findById(id);

        if (person != null) {

            if (mail != null && !mail.isEmpty()) {
                person.setMail(mail);
            }

            if (password != null && !password.isEmpty()) {
                person.setPassword(toMD5(password));
            }

            if (nickName != null && !nickName.isEmpty()) {
                person.setNick(nickName);
            }

            if (fullName != null && !fullName.isEmpty()) {
                person.setFullName(fullName);
            }

            personDao.update(person);
        }

         return person;
    }
}
