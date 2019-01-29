package ru.titov.s05.service.converters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.titov.s05.dao.domain.Person;
import ru.titov.s05.service.dto.UserDto;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserConverterTest {


    @Mock UserConverter userConverter;

    @Before
    public void startTest() {

    }

    @Test
    public void userDtoToPersonConvert_ok() {

        UserDto userDto = new UserDto();
        Person person = new Person();

        when(userConverter.userDtoToPersonConvert(userDto)).thenReturn(person);

        Person personFromService = userConverter.userDtoToPersonConvert(userDto);
        assertEquals(person, personFromService);
    }

    @Test
    public void personToUserDtoConvert() {
        UserDto userDto = new UserDto();
        Person person = new Person();

        when(userConverter.personToUserDtoConvert(person)).thenReturn(userDto);

        UserDto userDtoFromService = userConverter.personToUserDtoConvert(person);
        assertEquals(userDto, userDtoFromService);
    }

}