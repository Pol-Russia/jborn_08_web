package ru.titov.s05.integrationTest;

import org.junit.After;
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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;
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

    @Test
    public void personUpdate_ok() throws SQLException {

        Connection connection = DaoFactory.getConnection();

        Person person = new Person();
        person.setMail("iogor@ao.ru");
        person.setPassword("pass");
        Person personFromDao = personDao.insert(person, connection);

        assertEquals(person.getMail(), personFromDao.getMail());

        personFromDao.setMail("eee@yu.ru");
        person = personDao.update(personFromDao, connection);

        assertEquals("eee@yu.ru", person.getMail());



        assertNotEquals(-11, person.getId()); //ключ сгенерирован!
        assertNotNull(personFromDao);
        connection.close();
    }

    @Test
    public void personUpdate_idWrong() throws SQLException {

        Connection connection = DaoFactory.getConnection();

        Person person = new Person();
        person.setMail("iogor@ao.ru");
        person.setPassword("pass");
        Person personFromDao = personDao.insert(person, connection);

        assertEquals(person.getMail(), personFromDao.getMail());

        personFromDao.setMail("eee@yu.ru");
        personFromDao.setId(120);
        person = personDao.update(personFromDao, connection);

        assertNull(person);
        connection.close();
    }

    @Test
    public void personFindById_ok() throws SQLException {

        Connection connection = DaoFactory.getConnection();

        Person person = new Person();
        person.setMail("iogor@ao.ru");
        person.setPassword("pass");
        Person personFromDao = personDao.insert(person, connection);
        System.out.println(personFromDao.getMail());

        person.setMail("igogogogogo@aou.ru");
        person = personDao.findById(personFromDao.getId(), connection);

        assertEquals(person.getId(), personFromDao.getId());
        assertEquals("iogor@ao.ru", person.getMail());
        connection.close();
    }

    @Test
    public void personFindById_idWrong() throws SQLException {

        Connection connection = DaoFactory.getConnection();

        Person person = new Person();
        person.setMail("iogor@ao.ru");
        person.setPassword("pass");
        Person personFromDao = personDao.insert(person, connection);

        person.setMail("igogogogogo@aou.ru");
        person = personDao.findById(200, connection);


        assertNull(person);
        connection.close();
    }


    @Test
    public void personFindAll_ok() throws SQLException {

        Connection connection = DaoFactory.getConnection();

        Person person = new Person();
        person.setMail("iogor@ao.ru");
        person.setPassword("pass");
        personDao.insert(person, connection);

        Person person2 = new Person();
        person.setMail("iogorW@ao.ru");
        person.setPassword("pasds");
        personDao.insert(person, connection);




        List<Person> list = personDao.findAll();

        assertNotNull(list);
        assertEquals(2, list.size());
        connection.close();
    }

    @Test
    public void personDelete_ok() throws SQLException {

        Connection connection = DaoFactory.getConnection();

        Person person = new Person();
        person.setMail("iogor@ao.ru");
        person.setPassword("pass");
        Person personFromDao = personDao.insert(person, connection);



        assertNotNull(personFromDao);

        assertTrue(personDao.delete(personFromDao.getId(), connection));
        connection.close();
    }

    @Test
    public void personDelete_idWrong() throws SQLException {

        Connection connection = DaoFactory.getConnection();

        Person person = new Person();
        person.setMail("iogor@ao.ru");
        person.setPassword("pass");
        person.setId(10);
        UserDto user = new UserConverter().personToUserDtoConvert(person);

        Person personFromDao = personDao.insert(person, connection);



        assertNotNull(personFromDao);

        assertFalse(subj.deletePerson(user, connection));
        connection.close();
    }

    @After
    public void setDown() throws SQLException {

        Connection connection = DaoFactory.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM person;")) {

            preparedStatement.executeUpdate();
            connection.close();


        } catch (SQLException exept) {
            throw new RuntimeException(exept);
        }
    }


}
