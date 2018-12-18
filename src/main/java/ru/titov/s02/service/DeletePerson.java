package ru.titov.s02.service;

import ru.titov.s02.dao.domain.Person;
import ru.titov.s02.dao.domain.PersonDao;

import java.util.List;

public class DeletePerson {

    private boolean deletePerson(int id) {
        PersonDao personDao = new PersonDao();
        return personDao.delete(id);
    }
}
