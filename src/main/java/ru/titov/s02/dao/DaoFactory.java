package ru.titov.s02.dao;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class DaoFactory {
    private static DataSource dataSource;
    public static final int maxCountAccount = 5;

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

    private DaoFactory() {
    }
}
