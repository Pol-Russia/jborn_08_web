package ru.titov.s05.integrationTest;

import org.junit.Before;
import org.junit.Test;
import ru.titov.s05.dao.DaoFactory;
import ru.titov.s05.dao.PersonDao;
import ru.titov.s05.dao.domain.Person;
import ru.titov.s05.service.PersonService;
import ru.titov.s05.service.converters.UserConverter;
import ru.titov.s05.service.dto.UserDto;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static ru.titov.s05.dao.DaoFactory.getDataSource;

public class PersonDaointegrationTest {
    DataSource dataSource = getDataSource();
    PersonService subj;
    PersonDao personDao = new PersonDao(dataSource);
    UserConverter userConverter = new UserConverter();

    @Before
    public void setUp()  {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:testDatabase");//H2 DB работает только в памяти!
        System.setProperty("jdbcUsername", "sa");
        System.setProperty("jdbcPassword", "");

        subj = new PersonService(personDao, userConverter);


    }


    @Test
    public void personInsert_ok() throws SQLException {

        Connection connection = DaoFactory.getConnection();

        Person person = new Person();
        person.setMail("iogor@ao.ru");
        person.setPassword("pass");
        Person personFromDao = personDao.insert(person, connection);



        assertNotEquals(-11, person.getId()); //ключ сгенерирован!

        assertNotNull(personFromDao);
        assertEquals(person.getId(), personFromDao.getId()); // Проверил генерацию ключа
        assertEquals(person, personFromDao);
        connection.close();
    }

    @Test
    public void personInsert_mailWrong() throws SQLException {

        Connection connection = DaoFactory.getConnection();

        Person person = new Person();
        person.setMail("iogorao.ru");
        person.setPassword("pass");
        UserDto personFromDao = subj.createNewPerson(userConverter.personToUserDtoConvert(person), connection);


        assertNull(personFromDao);
        connection.close();
    }


}
