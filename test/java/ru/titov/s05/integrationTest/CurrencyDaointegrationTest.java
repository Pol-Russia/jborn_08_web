package ru.titov.s05.integrationTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.titov.s05.dao.CurrencyDao;
import ru.titov.s05.dao.DaoFactory;
import ru.titov.s05.dao.domain.Currency;
import ru.titov.s05.service.CurrencyService;
import ru.titov.s05.service.converters.CurrencyConverter;
import ru.titov.s05.service.dto.CategorieDto;
import ru.titov.s05.service.dto.CurrencyDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

public class CurrencyDaointegrationTest {
    CurrencyService subj;
    CurrencyDao currencyDao = new CurrencyDao();
    CurrencyConverter currencyConverter = new CurrencyConverter();

    @Before
    public void setUp()  {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:testDatabase");//H2 DB работает только в памяти!
        System.setProperty("jdbcUsername", "sa");
        System.setProperty("jdbcPassword", "");

        subj = new CurrencyService(currencyDao, currencyConverter);


    }


    @Test
    public void currencyInsert_ok() throws SQLException {

        CurrencyDto currencyDto = new CurrencyDto();
        Connection connection = DaoFactory.getConnection();


        currencyDto.setId(-11);
        currencyDto.setNameCurrency("USD");

        assertEquals(-11, currencyDto.getId()); //ключ ещё не сгенерирован!


        CurrencyDto currencyDtoFromService = subj.createNewCurrency(currencyDto, connection);


        assertNotNull(currencyDto);
        assertNotEquals(-11, currencyDtoFromService.getId()); // Проверил генерацию ключа
        assertNotEquals(currencyDto.getId(), currencyDtoFromService.getId());
        assertEquals(currencyDto.getNameCurrency(), currencyDtoFromService.getNameCurrency());
        connection.close();
    }

    @Test
    public void currencyInsert_NameCurrencyWrong() throws SQLException {

        CurrencyDto currencyDto = new CurrencyDto();
        Connection connection = DaoFactory.getConnection();


        currencyDto.setId(-11);
        currencyDto.setNameCurrency("");

        assertEquals(-11, currencyDto.getId()); //ключ ещё не сгенерирован!


        CurrencyDto currencyDtoFromService = subj.createNewCurrency(currencyDto, connection);


        assertNotNull(currencyDto);


        assertNull(currencyDtoFromService);
        connection.close();
    }

    @Test
    public void currencyUpdate_ok() throws SQLException {
        CurrencyDto currencyDto = new CurrencyDto();
        Connection connection = DaoFactory.getConnection();


        currencyDto.setId(-11);
        currencyDto.setNameCurrency("USD");

        assertEquals(-11, currencyDto.getId()); //ключ ещё не сгенерирован!


        CurrencyDto currencyDtoFromService = subj.createNewCurrency(currencyDto, connection);


        assertNotNull(currencyDtoFromService);
        assertEquals(currencyDto.getNameCurrency(), currencyDtoFromService.getNameCurrency());


        currencyDto.setNameCurrency("Euro");
        currencyDto.setId(currencyDtoFromService.getId());
        currencyDtoFromService = subj.updateCurrency(currencyDto, connection);



        assertEquals(currencyDto.getNameCurrency(), currencyDtoFromService.getNameCurrency());
        assertEquals("Euro", currencyDtoFromService.getNameCurrency());
        connection.close();
    }

    @Test
    public void currencyUpdate_idWrong() throws SQLException {
        CurrencyDto currencyDto = new CurrencyDto();
        Connection connection = DaoFactory.getConnection();


        currencyDto.setId(-11);
        currencyDto.setNameCurrency("USD");

        assertEquals(-11, currencyDto.getId());

        CurrencyDto currencyDtoFromService = subj.createNewCurrency(currencyDto, connection);


        assertNotNull(currencyDtoFromService);
        assertEquals(currencyDto.getNameCurrency(), currencyDtoFromService.getNameCurrency());


        currencyDto.setNameCurrency("Euro");
        currencyDto.setId(100);
        currencyDtoFromService = subj.updateCurrency(currencyDto, connection);



        assertNull(currencyDtoFromService);

        connection.close();
    }

    @Test
    public void currencyFindById_ok() throws SQLException {

        CurrencyDto currencyDto = new CurrencyDto();
        Connection connection = DaoFactory.getConnection();


        currencyDto.setId(-11);
        currencyDto.setNameCurrency("USD");

        assertEquals(-11, currencyDto.getId()); //ключ ещё не сгенерирован!


        CurrencyDto currencyDtoFromService = subj.createNewCurrency(currencyDto, connection);


        assertNotNull(currencyDtoFromService);
        assertEquals(currencyDto.getNameCurrency(), currencyDtoFromService.getNameCurrency());



        currencyDto = currencyConverter.currencyToCurrencyDtoConvert(
                currencyDao.findById(currencyDtoFromService.getId(), connection));

        assertEquals(currencyDto.getId(), currencyDtoFromService.getId());
        assertEquals(currencyDto.getNameCurrency(), currencyDtoFromService.getNameCurrency());
    }

    @Test
    public void currencyFindById_idWrong() throws SQLException {

        CurrencyDto currencyDto = new CurrencyDto();
        Connection connection = DaoFactory.getConnection();


        currencyDto.setId(-11);
        currencyDto.setNameCurrency("USD");

        assertEquals(-11, currencyDto.getId()); //ключ ещё не сгенерирован!


        CurrencyDto currencyDtoFromService = subj.createNewCurrency(currencyDto, connection);


        assertNotNull(currencyDtoFromService);
        assertEquals(currencyDto.getNameCurrency(), currencyDtoFromService.getNameCurrency());



        currencyDto = currencyConverter.currencyToCurrencyDtoConvert(
                currencyDao.findById(currencyDto.getId(), connection));

        assertNotEquals(currencyDto, currencyDtoFromService);
    }

    @Test
    public void currencyDelete_ok() throws SQLException {
        CurrencyDto currencyDto = new CurrencyDto();
        Connection connection = DaoFactory.getConnection();


        currencyDto.setId(-11);
        currencyDto.setNameCurrency("USD");

        assertEquals(-11, currencyDto.getId()); //ключ ещё не сгенерирован!


        CurrencyDto currencyDtoFromService = subj.createNewCurrency(currencyDto, connection);


        assertNotNull(currencyDtoFromService);
        assertEquals(currencyDto.getNameCurrency(), currencyDtoFromService.getNameCurrency());



        assertTrue(subj.deleteCurrency(currencyDtoFromService, connection));

        connection.close();
    }

    @Test
    public void currencyDelete_idWrong() throws SQLException {
        CurrencyDto currencyDto = new CurrencyDto();
        Connection connection = DaoFactory.getConnection();


        currencyDto.setId(-11);
        currencyDto.setNameCurrency("USD");

        assertEquals(-11, currencyDto.getId()); //ключ ещё не сгенерирован!


        CurrencyDto currencyDtoFromService = subj.createNewCurrency(currencyDto, connection);


        assertNotNull(currencyDtoFromService);
        assertEquals(currencyDto.getNameCurrency(), currencyDtoFromService.getNameCurrency());



        assertFalse(subj.deleteCurrency(currencyDto, connection));

        connection.close();
    }


    @Test
    public void currencyFindByAll_ok() throws SQLException {

        CurrencyDto currencyDto = new CurrencyDto();
        Connection connection = DaoFactory.getConnection();


        currencyDto.setId(-11);
        currencyDto.setNameCurrency("USD");

        assertEquals(-11, currencyDto.getId()); //ключ ещё не сгенерирован!


        subj.createNewCurrency(currencyDto, connection);
        currencyDto.setNameCurrency("F");
        subj.createNewCurrency(currencyDto, connection);

        List<Currency>  list = currencyDao.findByAll();

        assertNotNull(list);
        assertEquals(2, list.size());

        connection.close();
    }

    @After
    public void setDown() throws SQLException {

        Connection connection = DaoFactory.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM currency;")) {

            preparedStatement.executeUpdate();
            connection.close();


        } catch (SQLException exept) {
            throw new RuntimeException(exept);
        }
    }
}
