package ru.titov.s02.service;

import ru.titov.s02.dao.PersonDao;
import ru.titov.s02.dao.domain.Person;
import ru.titov.s02.service.converters.UserConverter;
import ru.titov.s02.service.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

public class PersonService {
    private  PersonDao personDao = new PersonDao();

    private List<UserDto> downloadListUserDto() {

        List<Person> person = personDao.findByAll();
        List<UserDto> listPersonDto = new ArrayList<>();
        UserConverter converter = new UserConverter();

        for (Person p : person) {

            listPersonDto.add(converter.personToUserDtoConvert(p));
        }

        return  listPersonDto;
    }

    public boolean checkMail(UserDto personDto) {

        Person person = new UserConverter().userDtoToPersonConvert(personDto);
        person = personDao.findByMail(person.getMail());

        if (person != null) {
            return true;
        }


        return false;
    }

    public UserDto checkPassword(UserDto personDto) {

        Person person = new UserConverter().userDtoToPersonConvert(personDto);
        person = personDao.findByMail(person.getMail());

        if (person != null) {

                if (person.getPassword().equals(personDto.getPassword())) {
                    personDto = new UserConverter().personToUserDtoConvert(person);
                    return personDto;
                }
            }


        return null;
    }

    public UserDto findById(int id) {

        return new UserConverter().personToUserDtoConvert(personDao.findById(id));
    }

    public UserDto findNickNameAndPassword(UserDto personDto) {

        if (personDto == null) {
            return null;
        }

        Person person = new UserConverter().userDtoToPersonConvert(personDto);
        person = personDao.findByNickAndPassword(person);

        return new UserConverter().personToUserDtoConvert(person);
    }

    public UserDto createNewPerson(UserDto personDto) {

        Person person = new UserConverter().userDtoToPersonConvert(personDto);

        if (! checkMail(personDto)) {

            return  new UserConverter().personToUserDtoConvert(personDao.insert(person));
        }

        return null;
    }

    public boolean deletePerson(UserDto personDto) {

        Person person = new UserConverter().userDtoToPersonConvert(personDto);

        if (person != null) {
            return personDao.delete(person.getId());
        }
        return false;
    }

    public UserDto updatePerson(UserDto personDto) {

        Person person = new UserConverter().userDtoToPersonConvert(personDto);
        person = personDao.findById(person.getId());

        if (person == null) {
            return null;
        }

         return  new UserConverter().personToUserDtoConvert(personDao.update(person));
    }
}
