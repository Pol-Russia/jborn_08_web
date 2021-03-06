package ru.titov.s05.integrationTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.titov.s05.dao.*;
import ru.titov.s05.dao.domain.*;
import ru.titov.s05.service.ServiceFactory;
import ru.titov.s05.service.TransactionService;
import ru.titov.s05.service.converters.TransactionConverter;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceIntagrationTest {
    AccountDao accountDao;
    PersonDao personDao;
    CurrencyDao currencyDao;
    CategorieDao categorieDao;
    TransactionDao transactionDao;
    TransactionService subj;
    @Mock TransactionDao transactionDaoMock;
    @Mock TransactionConverter transactionConverterMock;


    @Before
    public void SetUp() throws SQLException {

        System.setProperty("jdbcUrl", "jdbc:h2:mem:testDatabase");//H2 DB работает только в памяти!
        System.setProperty("jdbcUsername", "sa");
        System.setProperty("jdbcPassword", "");

        accountDao = DaoFactory.getAccountDao();
        personDao = DaoFactory.getPersonDao();
        currencyDao = DaoFactory.getCurrencyDao();
        transactionDao = DaoFactory.getTransactionDao();
        categorieDao = DaoFactory.getCategorieDao();
        subj = ServiceFactory.getTransactionService();

    }

    @Test
    public void transfer_testRollback() throws SQLException {
        Connection connection = DaoFactory.getConnection();
        Connection connectionTwo = DaoFactory.getConnection();


        doThrow(new RuntimeException()).when(transactionDaoMock).insert(any(Transaction.class), any(Connection.class));
        subj = new TransactionService(transactionDaoMock, categorieDao, transactionConverterMock, accountDao);


        Currency currency = new Currency();
        currency.setNameOfCurrency("Гривна");
        currencyDao.insert(currency, connectionTwo);


        Categorie categorie = new Categorie();
        categorie.setDescription("гривна");
        categorie = categorieDao.insert(categorie, connectionTwo);

        Person person = new Person();
        person.setMail("iogor@ao.ru");
        person.setPassword("pass");
        Person person2 = new Person();
        person2.setMail("Viвo@ao.ru");
        person2.setPassword("onаe");
        person = personDao.insert(person, connectionTwo);
        person2 = personDao.insert(person2, connectionTwo);

        Account accountTo = new Account();
        accountTo.setPersonID(person2.getId());
        accountTo.setNumberAccount(85522677);
        accountTo.setCurrencyID(currency.getId());
        accountTo.setDescription("Тест2");
        accountTo.setBalance(BigDecimal.valueOf(0));
        accountTo = accountDao.insert(accountTo, connectionTwo);


        Account accountFrom = new Account();
        accountFrom.setPersonID(person.getId());
        accountFrom.setNumberAccount(12333677);
        accountFrom.setCurrencyID(currency.getId());
        accountFrom.setDescription("Тест");
        accountFrom.setBalance(BigDecimal.valueOf(1000));
        accountFrom = accountDao.insert(accountFrom, connectionTwo);


        Transaction transaction = new Transaction();
        transaction.setAccountID(accountFrom.getId());
        transaction.setSum(BigDecimal.valueOf(100));
        transaction.setDate("22-11-78");
        transaction.setCategorieID(categorie.getId());
        transaction = transactionDao.insert(transaction, connectionTwo);


        BigDecimal sum = BigDecimal.valueOf(99);
        categorie.setDescription("перевод2");


        try {
            subj.transfer(accountFrom, accountTo, sum, categorie, connection);
        } catch (RuntimeException s) {
            //Не обрабатываю!
        }
        accountFrom = accountDao.findById(accountFrom.getId(), connectionTwo);
        accountTo = accountDao.findById(accountTo.getId(), connectionTwo);
        System.out.println("Balance from: " + accountFrom.getBalance() + "     Balance to: " + accountTo.getBalance());

        assertEquals(BigDecimal.valueOf(1000.0), accountFrom.getBalance());
        assertEquals(BigDecimal.valueOf(0.0), accountTo.getBalance());
        connectionTwo.close();

    }

    @Test
    public void transfer_ok() throws SQLException {
        Connection connection = DaoFactory.getConnection();
        Connection connectionTwo = DaoFactory.getConnection();


        Currency currency = new Currency();
        currency.setNameOfCurrency("USD");
        currencyDao.insert(currency, connectionTwo);
        Currency currency2 = new Currency();
        currency.setNameOfCurrency("рубль");
        currency = currencyDao.insert(currency, connectionTwo);

        Categorie categorie = new Categorie();
        categorie.setDescription("наличка");
        categorie = categorieDao.insert(categorie, connectionTwo);

        Person person = new Person();
        person.setMail("io@ao.ru");
        person.setPassword("ffffffffff");
        Person person2 = new Person();
        person2.setMail("ioc@ao.ru");
        person2.setPassword("fffffcfffff");
        person = personDao.insert(person, connectionTwo);
        person2 = personDao.insert(person2, connectionTwo);

        Account accountTo = new Account();
        accountTo.setPersonID(person2.getId());
        accountTo.setNumberAccount(88045677);
        accountTo.setCurrencyID(currency.getId());
        accountTo.setDescription("Тест");
        accountTo.setBalance(BigDecimal.valueOf(0));
        accountTo = accountDao.insert(accountTo, connectionTwo);


        Account accountFrom = new Account();
        accountFrom.setPersonID(person.getId());
        accountFrom.setNumberAccount(12045677);
        accountFrom.setCurrencyID(currency.getId());
        accountFrom.setDescription("Тест");
        accountFrom.setBalance(BigDecimal.valueOf(1000));
        accountFrom = accountDao.insert(accountFrom, connectionTwo);


        Transaction transaction = new Transaction();
        transaction.setAccountID(accountFrom.getId());
        transaction.setSum(BigDecimal.valueOf(100));
        transaction.setDate("22-11-78");
        transaction.setCategorieID(categorie.getId());
        transaction = transactionDao.insert(transaction, connectionTwo);


        BigDecimal sum = BigDecimal.valueOf(99);
        categorie.setDescription("перевод");


        try {
            subj.transfer(accountFrom, accountTo, sum, categorie, connection);
        } catch (RuntimeException s) {
            //Не обрабатываю!
        }
        accountFrom = accountDao.findById(accountFrom.getId(), connectionTwo);
        accountTo = accountDao.findById(accountTo.getId(), connectionTwo);
        System.out.println("Balance from: " + accountFrom.getBalance() + "     Balance to: " + accountTo.getBalance());

        assertEquals(BigDecimal.valueOf(901.0), accountFrom.getBalance());
        assertEquals(BigDecimal.valueOf(99.0), accountTo.getBalance());

        connectionTwo.close();
        connection.close();

    }

    @Test
    public void addSum_ok() throws  SQLException {
        Connection connection = DaoFactory.getConnection();
        Connection connectionTwo = DaoFactory.getConnection();

        Currency currency = new Currency();
        currency.setNameOfCurrency("Евро_");
        currencyDao.insert(currency, connectionTwo);

        Categorie categorie = new Categorie();
        categorie.setDescription("карта_");
        categorie = categorieDao.insert(categorie, connectionTwo);

        Person person = new Person();
        person.setMail("iввввввor@ao.ru");
        person.setPassword("ffffrfdqdffff");
        person = personDao.insert(person, connectionTwo);

        Account account = new Account();
        account.setPersonID(person.getId());
        account.setNumberAccount(88045697);
        account.setCurrencyID(currency.getId());
        account.setDescription("ЕщёТест_");
        account.setBalance(BigDecimal.valueOf(0));
        account = accountDao.insert(account, connectionTwo);

        BigDecimal sum = BigDecimal.valueOf(9201.0);


        try {
            subj.addSum(account, sum, categorie, connection);
        } catch (RuntimeException s) {
            //Не обрабатываю!
        }
        account = accountDao.findById(account.getId(), connectionTwo);

        System.out.println("Balance: " + account.getBalance());

        assertEquals(BigDecimal.valueOf(9201.0), account.getBalance());
        connectionTwo.close();
        connection.close();

    }

    @Test
    public void takeSum_ok() throws  SQLException {
        Connection connection = DaoFactory.getConnection();
        Connection connectionTwo = DaoFactory.getConnection();

        Currency currency = new Currency();
        currency.setNameOfCurrency("Евро-2");
        currencyDao.insert(currency, connectionTwo);

        Categorie categorie = new Categorie();
        categorie.setDescription("карта-4");
        categorie = categorieDao.insert(categorie, connectionTwo);

        Person person = new Person();
        person.setMail("ioКr@ao.ru");
        person.setPassword("ffffrffцfff");
        person = personDao.insert(person, connectionTwo);

        Account account = new Account();
        account.setPersonID(person.getId());
        account.setNumberAccount(88045697);
        account.setCurrencyID(currency.getId());
        account.setDescription("ЕщёОдинТест");
        account.setBalance(BigDecimal.valueOf(901));
        account = accountDao.insert(account, connectionTwo);

        BigDecimal sum = BigDecimal.valueOf(2201.0);


        try {
            subj.takeSum(account, sum, categorie, connection);
        } catch (RuntimeException s) {
            //Не обрабатываю!
        }
        account = accountDao.findById(account.getId(), connectionTwo);

        System.out.println("Balance: " + account.getBalance());

        assertEquals(BigDecimal.valueOf(-1300.0), account.getBalance());
        connectionTwo.close();
        connection.close();

    }


    @Test
    public void takeSum_Exception() throws  SQLException {
        Connection connection = DaoFactory.getConnection();
        Connection connectionTwo = DaoFactory.getConnection();

        doThrow(new RuntimeException()).when(transactionDaoMock).insert(any(Transaction.class), any(Connection.class));
        subj = new TransactionService(transactionDaoMock, categorieDao, transactionConverterMock, accountDao);

        Currency currency = new Currency();
        currency.setNameOfCurrency("Тугрик");
        currencyDao.insert(currency, connectionTwo);

        Categorie categorie = new Categorie();
        categorie.setDescription("Безналичный");
        categorie = categorieDao.insert(categorie, connectionTwo);

        Person person = new Person();
        person.setMail("iцor@ao.ru");
        person.setPassword("fffуfrfffff");
        person = personDao.insert(person, connectionTwo);

        Account account = new Account();
        account.setPersonID(person.getId());
        account.setNumberAccount(88045697);
        account.setCurrencyID(currency.getId());
        account.setDescription("ЕщёТест");
        account.setBalance(BigDecimal.valueOf(901));
        account = accountDao.insert(account, connectionTwo);

        BigDecimal sum = BigDecimal.valueOf(2201.0);


        try {
            subj.takeSum(account, sum, categorie, connection);
        } catch (RuntimeException s) {
            //Не обрабатываю!
        }
        account = accountDao.findById(account.getId(), connectionTwo);

        System.out.println("Balance: " + account.getBalance());

        assertEquals(BigDecimal.valueOf(901.0), account.getBalance());
        connectionTwo.close();
        connection.close();

    }

    @Test
    public void addSum_Exception() throws  SQLException {
        Connection connection = DaoFactory.getConnection();
        Connection connectionTwo = DaoFactory.getConnection();

        doThrow(new RuntimeException()).when(transactionDaoMock).insert(any(Transaction.class), any(Connection.class));
        subj = new TransactionService(transactionDaoMock, categorieDao, transactionConverterMock, accountDao);

        Currency currency = new Currency();
        currency.setNameOfCurrency("Евро");
        currencyDao.insert(currency, connectionTwo);

        Categorie categorie = new Categorie();
        categorie.setDescription("карта");
        categorie = categorieDao.insert(categorie, connectionTwo);

        Person person = new Person();
        person.setMail("ior@ao.ru");
        person.setPassword("ffffrfffff");
        person = personDao.insert(person, connectionTwo);

        Account account = new Account();
        account.setPersonID(person.getId());
        account.setNumberAccount(88045697);
        account.setCurrencyID(currency.getId());
        account.setDescription("ЕщёТест");
        account.setBalance(BigDecimal.valueOf(100));
        account = accountDao.insert(account, connectionTwo);

        BigDecimal sum = BigDecimal.valueOf(9201.0);


        try {
            subj.addSum(account, sum, categorie, connection);
        } catch (RuntimeException s) {
            //Не обрабатываю!
        }
        account = accountDao.findById(account.getId(), connectionTwo);

        System.out.println("Balance: " + account.getBalance());

        assertEquals(BigDecimal.valueOf(100.0), account.getBalance());
        connectionTwo.close();
        connection.close();

    }



}



