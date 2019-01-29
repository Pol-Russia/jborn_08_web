package ru.titov.s05.service;

import ru.titov.s05.dao.PersonDao;
import ru.titov.s05.dao.domain.Person;
import ru.titov.s05.service.converters.UserConverter;
import ru.titov.s05.service.dto.UserDto;

import java.sql.Connection;

public class SecurityService {
    private final PersonDao personDao;
    private final DigestService digestService;
    private final UserConverter userConverter;

    public SecurityService(PersonDao  personDao, DigestService digestService, UserConverter userConverter) {
        this.personDao = personDao;
        this.digestService = digestService;
        this.userConverter = userConverter;
    }

    public UserDto authorize(String email, String password, Connection connection) {

        Person person = personDao.findByMail(email, connection);

        if (person != null) {

            String passwordHash = digestService.hash(password);

            if (passwordHash.equals(person.getPassword())) {

                return userConverter.personToUserDtoConvert(person);
            }

        }



        return null;
    }

}
