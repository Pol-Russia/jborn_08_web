package ru.titov.s04.dao;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class DaoFactory {
    private static DataSource dataSource;
    private static PersonDao personDao;
    private static CategorieDao categorieDao;
    private static CurrencyDao currencyDao;
    private static TransactionDao transactionDao;
    private static AccountDao accountDao;
    public static final int maxCountAccount = 5; // Максимальное число счетов у одной персоны

    public static DataSource getDataSource() {
        if (dataSource == null) {
            HikariDataSource ds = new HikariDataSource();
            ds.setJdbcUrl("jdbc:postgresql://127.0.0.1:5432/postgres");
            ds.setUsername("postgres");
            ds.setPassword("123");

            dataSource = ds;
        }

        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
            return getDataSource().getConnection();
    }

    public static PersonDao getPersonDao() {
        if (personDao == null) {
            personDao = new PersonDao(getDataSource());
        }

        return personDao;
    }

    public static  CategorieDao getCategorieDao() {
        if (categorieDao == null) {
            categorieDao = new CategorieDao();
        }

        return categorieDao;
    }

    public static CurrencyDao getCurrencyDao() {
        if (currencyDao == null) {
            currencyDao = new CurrencyDao();
        }
        return currencyDao;
    }

    public static TransactionDao getTransactionDao() {
        if (transactionDao == null) {
            transactionDao = new TransactionDao();
        }
        return transactionDao;
    }

    public static AccountDao getAccountDao() {
        if (accountDao == null) {
            accountDao = new AccountDao();
        }

        return accountDao;
    }
}
