package ru.titov.s06.service;

import ru.titov.s06.dao.PersonDao;
import ru.titov.s06.dao.domain.Person;
import ru.titov.s06.service.converters.UserConverter;
import ru.titov.s06.service.dto.UserDto;

public class SecurityService {
    private final PersonDao personDao;
    private final DigestService digestService;
    private final UserConverter userConverter;

    public SecurityService(PersonDao  personDao, DigestService digestService, UserConverter userConverter) {
        this.personDao = personDao;
        this.digestService = digestService;
        this.userConverter = userConverter;
    }

    public UserDto authorize(String email, String password) {

        Person person = personDao.findByMail(email);

        if (person != null) {

            String passwordHash = digestService.hash(password);

            if (passwordHash.equals(person.getPassword())) {

                return userConverter.personToUserDtoConvert(person);
            }

        }



        return null;
    }

}
