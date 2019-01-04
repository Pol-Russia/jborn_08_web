package ru.titov.s02.dao;

import ru.titov.s02.dao.Dao;
import ru.titov.s02.dao.domain.Account;

import static  ru.titov.s02.dao.DaoFactory.getConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao implements Dao<Account, Integer> {
    @Override
    public Account findById(Integer id) {
        Account account = new Account();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("Select * From account " +
                     "WHERE (account.id = ?)")) {

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

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
        account.setBalance(rs.getBigDecimal(4));
        account.setCurrencyID(rs.getInt(5));
        account.setDescription(rs.getString(6));

        return account;
    }

    private void setPreparedStatement(PreparedStatement preparedStatement, Account account) throws SQLException {

        preparedStatement.setInt(1, account.getNumberAccount());
        preparedStatement.setInt(2, account.getPersonID());
        preparedStatement.setBigDecimal(3, account.getBalance());
        preparedStatement.setInt(4, account.getCurrencyID());
        preparedStatement.setString(5, account.getDescription());

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
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO account(" +
                     "number_account, person_id, balance, currency_id, description) VALUES( ?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS);) {

            setPreparedStatement(preparedStatement, account);

            if (preparedStatement.executeUpdate() > 0) {

                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1); //вставленный ключ
                    account.setId(id);
                }

                return account;
            }
        }
                catch (SQLException sql) {
                    throw new RuntimeException(sql);
                }
        return null;
    }

    @Override
    public Account update(Account account) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE account SET " +
                     "number_account = ?, person_id = ?, balance = ?, currency_id = ?, description = ? " +
                     "WHERE account.id = ?"))
        {

            setPreparedStatement(preparedStatement, account);

            if (preparedStatement.executeUpdate() > 0) {
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
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE * FROM account WHERE (account.id = ?)")) {


            preparedStatement.setInt(1, id);

            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }

        return false;
    }


    public List<Account> findByNumberAccount(Integer numberAccount) {
        Account account = new Account();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("Select * From account " +
                     "WHERE (account.number_account = ?)")) {

            preparedStatement.setInt(1, numberAccount);
            ResultSet rs = preparedStatement.executeQuery();
            List<Account> list = new ArrayList();

            while (rs.next()) {
                list.add(getAccount(rs, account));
            }
            return list;
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }
    }

    public List<Account> findByPersonId(Integer personId) {

        List<Account> list = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("Select * From account " +
                     "WHERE (account.person_id = ?)")) {


            preparedStatement.setInt(1, personId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Account account = new Account();
                list.add(getAccount(rs, account));
            }
            return list;
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }
    }

}
