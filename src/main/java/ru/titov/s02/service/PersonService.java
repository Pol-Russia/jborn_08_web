package ru.titov.s02.service;

import ru.titov.s02.dao.PersonDao;
import ru.titov.s02.dao.domain.Person;

import java.util.List;

public class PersonService {
    private  PersonDao personDao = new PersonDao();

    private void setPerson(Person person, String mail, String password, String nick, String fullName) {
        person.setMail(mail);
        person.setPassword(password);
        person.setNick(nick);
        person.setFullName(fullName);
    }

    private List<Person> downloadListPerson() {
        return personDao.findByAll();
    }

    public boolean checkMail(Person person) {

        if (person != null) {
            List<Person> list = downloadListPerson();
            int size = list.size();

            for (int i = 0; i < size; i++) {
                if (person.getMail().equalsIgnoreCase(list.get(i).getMail())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkPassword(Person person) {

        if (person != null) {

            Person newPerson = personDao.findByMail(person.getMail());

            if (newPerson != null) {
                if (newPerson.getPassword().equals(person.getPassword())) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean checkNickNameAndPassword(Person person) {

        if (person == null) {
            return false;
        }

        Person newPerson = personDao.findByNickAndPassword(person.getNick(), person.getPassword());

            if (newPerson != null) {
                return true;
            }

        return false;
    }

    public Person createNewPerson(Person person) {

        if (! checkMail(person)) {

            setPerson(person, person.getMail(), person.getPassword(), person.getNick(), person.getFullName());
            return  personDao.insert(person);
        }

        return null;
    }

    public boolean deletePerson(Person person) {

        if (person != null) {
            return personDao.delete(person.getId());
        }
        return false;
    }

    public Person updatePerson(Person person) {

        Person newPerson = personDao.findById(person.getId());

        if (newPerson == null) {
            return null;
        }

        personDao.update(person);
        return person;
    }
}
