package ru.titov.s02.service.converters;

import org.apache.commons.codec.digest.DigestUtils;
import ru.titov.s02.dao.domain.Person;
import ru.titov.s02.service.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

public class UserConverter  {

    private String toMD5(String start) {
        return DigestUtils.md5Hex(start);
    }


    public Person userDtoToPersonConvert(UserDto userDto) {
        if (userDto != null) {
            int id = userDto.getId();

            Person person = new Person();
            person.setMail(userDto.getMail());

            if (id == -11) {
                person.setPassword(toMD5(userDto.getPassword()));
            }
            else {
                person.setPassword(userDto.getPassword());
            }
            person.setFullName(userDto.getFullName());
            person.setNick(userDto.getNick());
            person.setId(id);

            return person;
        }

        return null;
    }

    public UserDto  personToUserDtoConvert(Person person) {

       if (person != null) {

           UserDto user = new UserDto();
           user.setId(person.getId());
           user.setMail(person.getMail());
           user.setPassword(person.getPassword());
           user.setNick(person.getNick());
           user.setFullName(person.getFullName());
           return user;
       }

        return null;
    }

    public List<UserDto> listPersonToListUserDtoConvert(List<Person> person) {

        if (person == null) {
            return null;
        }

        List<UserDto> listUserDto = new ArrayList<>();
        UserConverter converter = new UserConverter();

        for (Person pers : person) {

            listUserDto.add(converter.personToUserDtoConvert(pers));
        }

        return listUserDto;
    }



}
