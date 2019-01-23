package ru.titov.s05.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.titov.s05.dao.PersonDao;
import ru.titov.s05.dao.domain.Person;
import ru.titov.s05.service.converters.UserConverter;
import ru.titov.s05.service.dto.UserDto;

import java.sql.Connection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SecurityServiceTest {

   @InjectMocks SecurityService subj;
    @Mock PersonDao personDao;
    @Mock DigestService digestService;
    @Mock UserConverter userConverter;
    @Mock Connection connectionMock;


    @Test
    public void authorize_userNotFoundByEmail() {

        when(personDao.findByMail("popov@mail.ru", connectionMock)).thenReturn(null);
        UserDto userDto = subj.authorize("popov@mail.ru", "pass", connectionMock);

        assertNull(userDto);
    }

    @Test
    public void authorize_passwordWrong() {
        Person person = new Person();
        person.setPassword("769d372e15ea74d8b644fe7e5ae62b79");

        when(personDao.findByMail("popov@mail.ru", connectionMock)).thenReturn(person);
        when(digestService.hash("password")).thenReturn("777777777777");

        UserDto userDto = subj.authorize("popov@mail.ru", "password", connectionMock);

        assertNull(userDto);
    }

    @Test
    public void authorize_OK() {
        Person person = new Person();
        person.setId(100);
        person.setPassword("some password");


        UserDto userDto = new UserDto();
        userDto.setId(100);

        when(personDao.findByMail("popov@mail.ru", connectionMock)).thenReturn(person);
        when(digestService.hash("password")).thenReturn("some password");
        when(userConverter.personToUserDtoConvert(person)).thenReturn(userDto);


        UserDto userDtoFromService = subj.authorize("popov@mail.ru", "password", connectionMock);
        assertEquals(userDto, userDtoFromService);

    }
}