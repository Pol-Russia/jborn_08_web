package ru.titov.s05.integrationTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import ru.titov.s05.dao.AccountDao;
import ru.titov.s05.dao.CurrencyDao;
import ru.titov.s05.dao.DaoFactory;
import ru.titov.s05.dao.PersonDao;
import ru.titov.s05.dao.domain.Account;
import ru.titov.s05.dao.domain.Currency;
import ru.titov.s05.dao.domain.Person;
import ru.titov.s05.service.AccountService;
import ru.titov.s05.service.ServiceFactory;
import ru.titov.s05.service.converters.AccountConverter;
import ru.titov.s05.service.dto.AccountDto;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;


public class AccountDaoIntTest {

    AccountDao accountDao = DaoFactory.getAccountDao();
    AccountService subj;
    Person person = null;
    Currency currency = null;

    @Before
    public void setUp() throws SQLException {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:testDatabase");//H2 DB работает только в памяти!
        System.setProperty("jdbcUsername", "sa");
        System.setProperty("jdbcPassword", "");


        person = new Person();
        person.setMail("fot@ru.ru");
        person.setPassword("dddde");
        Connection connection = DaoFactory.getConnection();

        person = DaoFactory.getPersonDao().insert(person, connection);


        currency = new Currency();
        currency.setNameOfCurrency("Валюта");


        currency = DaoFactory.getCurrencyDao().insert(currency, connection);

        connection.close();

        CurrencyDao currencyDao = DaoFactory.getCurrencyDao();
        AccountConverter accountConverter = ServiceFactory.getAccountConverter();
        PersonDao personDao = DaoFactory.getPersonDao();

        accountDao = DaoFactory.getAccountDao();
        subj = new AccountService(accountDao, accountConverter, currencyDao, personDao);


    }


    @Test
    public void accountInsert_ok() throws SQLException {

        AccountDto account = new AccountDto();
        Connection connection = DaoFactory.getConnection();


        account.setPersonId(person.getId()); //Нет реального человека - нет счёта!
        account.setNumberAccount(12045677);
        account.setCurrencyId(currency.getId()); //Нет такой валюты - нет счёта!
        account.setDescription("Тест");
        account.setBalance(BigDecimal.valueOf(100));

        assertEquals(-11, account.getId()); //ключ ещё не сгенерирован!


        account = subj.createNewAccount(account, connection);


        assertNotNull(account);
        assertNotEquals(-11, account.getId()); // Проверил генерацию ключа по умолчанию -11
        connection.close();
    }

    @Test
    public void accountInsert_personIdWrong() throws SQLException {

        Connection connection = DaoFactory.getConnection();
        AccountDto account = new AccountDto();


        account.setPersonId(10000); //Нет реального человека - нет счёта!
        account.setNumberAccount(12545677);
        account.setCurrencyId(currency.getId()); //Нет такой валюты - нет счёта!
        account.setDescription("Тест");
        account.setBalance(BigDecimal.valueOf(100));

        assertEquals(-11, account.getId());


        account = subj.createNewAccount(account, connection);


        assertNull(account);
        connection.close();
    }

    @Test
    public void accountInsert_CurrencyIdWrong() throws SQLException {

        Connection connection = DaoFactory.getConnection();
        AccountDto account = new AccountDto();

        account.setPersonId(person.getId()); //Нет реального человека - нет счёта!
        account.setNumberAccount(14045677);
        account.setCurrencyId(1200); //Нет такой валюты - нет счёта!
        account.setDescription("Тест");
        account.setBalance(BigDecimal.valueOf(100));

        assertEquals(-11, account.getId());


        account = subj.createNewAccount(account, connection);


        assertNull(account);
        connection.close();
    }

    @Test
    public void accountUpdate_ok() throws SQLException {
        AccountDto account = new AccountDto();
        Connection connection = DaoFactory.getConnection();


        account.setPersonId(person.getId()); //Нет реального человека - нет счёта!
        account.setNumberAccount(11045677);
        account.setCurrencyId(currency.getId()); //Нет такой валюты - нет счёта!
        account.setDescription("Тест");
        account.setBalance(BigDecimal.valueOf(100));

        account = subj.createNewAccount(account, connection);
        account.setBalance(BigDecimal.valueOf(560));
        account = subj.updateAccount(account, connection);


        assertEquals(BigDecimal.valueOf(560), account.getBalance());

        connection.close();
    }

    @Test
    public void accountUpdate_idWrong() throws SQLException {
        AccountDto account = new AccountDto();
        Connection connection = DaoFactory.getConnection();


        account.setPersonId(person.getId()); //Нет реального человека - нет счёта!
        account.setNumberAccount(11045677);
        account.setCurrencyId(currency.getId()); //Нет такой валюты - нет счёта!
        account.setDescription("Тест");
        account.setBalance(BigDecimal.valueOf(100));

        account = subj.createNewAccount(account, connection);
        AccountDto accountTwo = account;
        accountTwo.setId(100);
        accountTwo.setBalance(BigDecimal.valueOf(560));

        account = subj.updateAccount(accountTwo, connection);


        assertNotEquals(account, accountTwo);

        connection.close();
    }


    @Test
    public void accountFindById_ok() throws SQLException {

        AccountDto account = new AccountDto();
        Connection connection = DaoFactory.getConnection();


        account.setPersonId(person.getId()); //Нет реального человека - нет счёта!
        account.setNumberAccount(11045677);
        account.setCurrencyId(currency.getId()); //Нет такой валюты - нет счёта!
        account.setDescription("Тест");
        account.setBalance(BigDecimal.valueOf(12.0));

        account = subj.createNewAccount(account, connection);

        Account accountFromService = accountDao.findById(account.getId(), connection);
        AccountDto fromService = new AccountConverter().accountToAccountDtoConvert(accountFromService);


        assertEquals(account.getCurrencyId(), fromService.getCurrencyId());
        assertEquals(account.getPersonId(), fromService.getPersonId());
        assertEquals(account.getId(), fromService.getId());
        assertEquals(account.getNumberAccount(), fromService.getNumberAccount());
        assertEquals(account.getBalance(), fromService.getBalance());

        connection.close();
    }

    @Test
    public void accountFindById_idWrong() throws SQLException {

        AccountDto account = new AccountDto();
        Connection connection = DaoFactory.getConnection();


        account.setPersonId(person.getId()); //Нет реального человека - нет счёта!
        account.setNumberAccount(11045677);
        account.setCurrencyId(currency.getId()); //Нет такой валюты - нет счёта!
        account.setDescription("Тест");
        account.setBalance(BigDecimal.valueOf(12.0));

        account = subj.createNewAccount(account, connection);

        Account accountFromService = accountDao.findById(-1, connection);
        AccountDto fromService = new AccountConverter().accountToAccountDtoConvert(accountFromService);


        assertNull(accountFromService);

        connection.close();
    }

    @Test
    public void accountFindAll_ok() throws SQLException {
        AccountDto account = new AccountDto();
        Connection connection = DaoFactory.getConnection();


        account.setPersonId(person.getId()); //Нет реального человека - нет счёта!
        account.setNumberAccount(11045677);
        account.setCurrencyId(currency.getId()); //Нет такой валюты - нет счёта!
        account.setDescription("Тест");
        account.setBalance(BigDecimal.valueOf(100));

        account = subj.createNewAccount(account, connection);

        List<Account> list = accountDao.findByAll();
        Account account1 = list.get(0);


        assertEquals(account.getId(), account1.getId());
        assertEquals(account.getPersonId(), account1.getPersonID());
        assertEquals(account.getCurrencyId(), account1.getCurrencyID());


        connection.close();
    }


    @Test
    public void accountDelete_ok() throws SQLException {

        AccountDto account = new AccountDto();
        Connection connection = DaoFactory.getConnection();

        account.setPersonId(person.getId()); //Нет реального человека - нет счёта!
        account.setNumberAccount(11045677);
        account.setCurrencyId(currency.getId()); //Нет такой валюты - нет счёта!
        account.setDescription("Тест");
        account.setBalance(BigDecimal.valueOf(100));

        account = subj.createNewAccount(account, connection);

        assertTrue(subj.deleteAccount(account, connection));


        connection.close();
    }

    @Test
    public void accountDelete_idWrong() throws SQLException {

        AccountDto account = new AccountDto();
        Connection connection = DaoFactory.getConnection();

        account.setPersonId(person.getId()); //Нет реального человека - нет счёта!
        account.setNumberAccount(11045677);
        account.setCurrencyId(currency.getId()); //Нет такой валюты - нет счёта!
        account.setDescription("Тест");
        account.setBalance(BigDecimal.valueOf(100));

        account = subj.createNewAccount(account, connection);

        account.setId(-1);

        assertFalse(subj.deleteAccount(account, connection));


        connection.close();
    }

    @After
    public void setDown() throws SQLException {
        //Код в нем будет "TRUNCATE TABLE USER
        Connection connection = DaoFactory.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM account;")) {

            preparedStatement.executeUpdate();


        } catch (SQLException exept) {
            throw new RuntimeException(exept);
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM person;")) {

            preparedStatement.executeUpdate();

        } catch (SQLException exept) {
            throw new RuntimeException(exept);
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM currency;")) {

            preparedStatement.executeUpdate();

        } catch (SQLException exept) {
            throw new RuntimeException(exept);
        }

        connection.close();

    }

}
