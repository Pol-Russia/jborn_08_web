package ru.titov.s02.dao.domain;

import ru.titov.s02.dao.Dao;
import static  ru.titov.s02.dao.DaoFactory.getConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao implements Dao<Account, Integer> {
    @Override
    public Account findById(Integer id) {
        Account account = new Account();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery("Select * From account " +
                    "WHERE (account.id = " + id + ")");

            if (rs.next()) {
                return getAccount(rs, account);
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }
        return null;
    }

    private  Account getAccount(ResultSet rs, Account account) throws SQLException {

        account.setId(rs.getInt(1));
        account.setNumberAccount(rs.getInt(2));
        account.setPersonID(rs.getInt(3));
        account.setBalance(rs.getLong(4));
        account.setCurrencyID(rs.getInt(5));
        account.setDescription(rs.getString(6));

        return account;
    }

    @Override
    public List<Account> findByAll() {
        List<Account> list = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery("Select * From account ");

            while (rs.next()) {
                Account account = new Account();
                list.add(getAccount(rs, account));
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }

        return list;
    }

    @Override
    public Account insert(Account account) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO account(id," +
                     "number_account, person_id, balance, currency_id, description) VALUES(?, ?, ?, ?, ?, ?)");)
        {
            preparedStatement.setInt(   1, account.getId());
            preparedStatement.setInt(2, account.getNumberAccount());
            preparedStatement.setInt(3, account.getPersonID());
            preparedStatement.setLong(4, account.getBalance());
            preparedStatement.setInt(5, account.getCurrencyID());
            preparedStatement.setString(6, account.getDescription());

            if (preparedStatement.execute()) {
                return account;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Account update(Account account) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE account SET " +
                     "number_account = ?, person_id = ?, balance = ?, currency_id = ?, description = ? " +
                     "WHERE account.id = ?");)
        {
            preparedStatement.setInt(6, account.getId());
            preparedStatement.setInt(1, account.getNumberAccount());
            preparedStatement.setInt(2, account.getPersonID());
            preparedStatement.setLong(3, account.getBalance());
            preparedStatement.setInt(4, account.getCurrencyID());
            preparedStatement.setString(5, account.getDescription());

            if (preparedStatement.execute()) {
                return account;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            if (statement.executeUpdate("DELETE * FROM account WHERE (account.id = " + id + ")") != 0) {
                return true;
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }

        return false;
    }


    public Account findByNumberAccount(Integer numberAccount) {
        Account account = new Account();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery("Select * From account " +
                    "WHERE (account.number_account = " + numberAccount + ")");

            if (rs.next()) {
                return getAccount(rs, account);
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }

        return  null;
    }

    public List<Account> findByPersonId(Integer personId) {
        List<Account> list = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery("Select * From account " +
                    "WHERE (account.person_id = " + personId + ")");

            while (rs.next()) {
                Account account = new Account();
                list.add(getAccount(rs, account));
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }

        return list;
    }



}
