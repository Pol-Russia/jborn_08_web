package ru.titov.s05.service;

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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

    @InjectMocks PersonService subj;
    @Mock UserConverter userConverter;
    @Mock PersonDao personDao;
    @Mock Connection connectionMock;

    @Test
    public void checkMail_ok() {
        UserDto userDto = new UserDto();
        userDto.setMail("ui@mail.ru");

        Person person = new Person();
        person.setMail("ui@mail.ru");

        when(userConverter.userDtoToPersonConvert(userDto)).thenReturn(person);
        when(personDao.findByMail("ui@mail.ru", connectionMock)).thenReturn(person);

        Boolean mailFromService = subj.checkMail(userDto, connectionMock);
        assertTrue(mailFromService);
    }

    @Test
    public void checkMail_badMail() {
        UserDto userDto = new UserDto();
        userDto.setMail("uimail.ru");

        Person person = new Person();
        person.setMail("ui@mail.ru");

        when(userConverter.userDtoToPersonConvert(userDto)).thenReturn(person);
        when(personDao.findByMail("ui@mail.ru", connectionMock)).thenReturn(person);

        Boolean mailFromService = subj.checkMail(userDto, connectionMock);
        assertFalse(mailFromService);
    }

    @Test
    public void findNickNameAndPassword_ok() {
        UserDto userDto = new UserDto();
        Person person = new Person();

        when(userConverter.userDtoToPersonConvert(userDto)).thenReturn(person);
        when(personDao.findByNickAndPassword(person, connectionMock)).thenReturn(person);
        when(userConverter.personToUserDtoConvert(person)).thenReturn(userDto);

        UserDto userFromService = subj.findNickNameAndPassword(userDto, connectionMock);

        assertEquals(userDto, userFromService);

    }

    @Test
    public void createNewPerson_ok() {
        UserDto userDto = new UserDto();
        userDto.setMail("bf@vr.ru");
        Person person = new Person();


        when(userConverter.userDtoToPersonConvert(userDto)).thenReturn(person);
        when(personDao.insert(person, connectionMock)).thenReturn(person);
        when(personDao.findByMail("bf@vr.ru", connectionMock)).thenReturn(null);
        when(userConverter.personToUserDtoConvert(person)).thenReturn(userDto);

        UserDto userFromService = subj.createNewPerson(userDto, connectionMock);
        assertEquals(userDto, userFromService);
    }

    @Test
    public void createNewPerson_badMail() {
        UserDto userDto = new UserDto();
        userDto.setMail("bfvr.ru");
        Person person = new Person();


        when(userConverter.userDtoToPersonConvert(userDto)).thenReturn(person);
        when(personDao.insert(person, connectionMock)).thenReturn(person);
        when(userConverter.personToUserDtoConvert(person)).thenReturn(userDto);

        UserDto userFromService = subj.createNewPerson(userDto, connectionMock);
        assertNotEquals(userDto, userFromService);
    }

    @Test
    public void deletePerson_ok() {
        UserDto userDto = new UserDto();
        Person person = new Person();
        person.setId(89);

        when(userConverter.userDtoToPersonConvert(userDto)).thenReturn(person);
        when(personDao.delete(89, connectionMock)).thenReturn(true);

        Boolean deletePerson = subj.deletePerson(userDto, connectionMock);

        assertTrue(deletePerson);
    }

    @Test
    public void deletePerson_personIdWrong() {
        UserDto userDto = new UserDto();
        Person person = new Person();
        person.setId(89);

        when(userConverter.userDtoToPersonConvert(userDto)).thenReturn(person);
        when(personDao.delete(91, connectionMock)).thenReturn(true);

        Boolean deletePerson = subj.deletePerson(userDto, connectionMock);

        assertFalse(deletePerson);
    }

    @Test
    public void updatePerson_ok() {

        UserDto userDto = new UserDto();
        Person person = new Person();
        person.setId(89);

        when(userConverter.userDtoToPersonConvert(userDto)).thenReturn(person);
        when(personDao.findById(89, connectionMock)).thenReturn(person);
        when(personDao.update(person, connectionMock)).thenReturn(person);
        when(userConverter.personToUserDtoConvert(person)).thenReturn(userDto);

        UserDto personFromService = subj.updatePerson(userDto, connectionMock);

        assertEquals(userDto, personFromService);
    }

    @Test
    public void updatePerson_personIdWrong() {

        UserDto userDto = new UserDto();
        Person person = new Person();
        person.setId(89);

        when(userConverter.userDtoToPersonConvert(userDto)).thenReturn(person);
        when(personDao.findById(9, connectionMock)).thenReturn(person);
        when(personDao.update(person, connectionMock)).thenReturn(person);
        when(userConverter.personToUserDtoConvert(person)).thenReturn(userDto);

        UserDto personFromService = subj.updatePerson(userDto, connectionMock);

        assertNotEquals(userDto, personFromService);
    }
}