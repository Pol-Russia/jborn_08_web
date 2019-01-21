package ru.titov.s04.service;

import ru.titov.s04.dao.DaoFactory;
import ru.titov.s04.dao.PersonDao;
import ru.titov.s04.dao.domain.Person;
import ru.titov.s04.service.converters.UserConverter;
import ru.titov.s04.service.dto.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonService {
    private final PersonDao personDao;
    private final UserConverter userConverter;
    private final SecurityService securityService;

    public PersonService(PersonDao personDao, UserConverter userConverter) {
        this.personDao = personDao;
        this.userConverter = userConverter;
        securityService = ServiceFactory.getSecurityService();
    }

    private List<UserDto> downloadListUserDto() {

        List<Person> person = personDao.findByAll();
        List<UserDto> listPersonDto = new ArrayList<>();

        for (Person p : person) {

            listPersonDto.add(userConverter.personToUserDtoConvert(p));
        }

        return  listPersonDto;
    }

    public boolean checkMail(UserDto personDto) {

         String mail = personDto.getMail();

        if (mail.length() > 60) {
            return false;
        }

        Pattern p = Pattern.compile("[a-zA-Z0-9\\-_]+@[a-zA-Z]+\\.[a-zA-Z]{2,4}");
        Matcher m = p.matcher(mail);

        if (m.matches()) {

            Person person = userConverter.userDtoToPersonConvert(personDto);
            person = personDao.findByMail(person.getMail());


            if (person == null) {
                return true;
            }

        }
        return false;
    }

   public UserDto authorize(UserDto personDto) {

        return securityService.authorize(personDto.getMail(), personDto.getPassword());
    }

    public UserDto findNickNameAndPassword(UserDto personDto) {

        if (personDto == null) {
            return null;
        }

        Person person = userConverter.userDtoToPersonConvert(personDto);
        person = personDao.findByNickAndPassword(person);

        return userConverter.personToUserDtoConvert(person);
    }

    public UserDto createNewPerson(UserDto personDto) {

        if (checkMail(personDto)) {

            Person person = userConverter.userDtoToPersonConvert(personDto);
            return  userConverter.personToUserDtoConvert(personDao.insert(person));
        }

        return null;
    }

    public boolean deletePerson(UserDto personDto) {

        Person person = userConverter.userDtoToPersonConvert(personDto);

        if (person != null) {
            return personDao.delete(person.getId());
        }
        return false;
    }

    public UserDto updatePerson(UserDto personDto) {

        Person person = userConverter.userDtoToPersonConvert(personDto);
        person = personDao.findById(person.getId());

        if (person == null) {
            return null;
        }

         return  userConverter.personToUserDtoConvert(personDao.update(person));
    }
}
