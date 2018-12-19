package ru.titov.s02.dao.domain;

import ru.titov.s02.dao.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ru.titov.s02.dao.DaoFactory.getConnection;

public class TransactionDao implements Dao<Transaction, Integer> {

    private  Transaction getAccount(ResultSet rs, Transaction transaction) throws SQLException {

        transaction.setId(rs.getInt(1));
        transaction.setAccountID(rs.getInt(2));
        transaction.setSum(rs.getLong(3));
        transaction.setDate(rs.getDate(4));
        transaction.setCategorieID(rs.getInt(5));

        return transaction;
    }

    @Override
    public Transaction findById(Integer id) {
        Transaction transaction = new Transaction();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery("Select * From transaction" +
                    "WHERE (transaction.id = " + id + ")");

            if (rs.next()) {
                return getAccount(rs, transaction);
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }

        return null;
    }

    @Override
    public List<Transaction> findByAll() {
        List<Transaction> list = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery("Select * From transaction");

            while (rs.next()) {
                Transaction transaction = new Transaction();
                list.add(getAccount(rs, transaction));
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }

        return list;
    }

    @Override
    public Transaction insert(Transaction transaction) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO transaction(id," +
                     "account_id, sum, date, categorie_id) VALUES( ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);)
        {

            preparedStatement.setInt(1, transaction.getAccountID());
            preparedStatement.setLong(2, transaction.getSum());
            preparedStatement.setDate(3, (Date) transaction.getDate());
            preparedStatement.setInt(4, transaction.getCategorieID());

            if (preparedStatement.execute()) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        transaction.setId(resultSet.getInt("id"));
                    }

                }
                return transaction;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Transaction update(Transaction transaction) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE transaction SET " +
                     "account_id = ?, sum = ?, date = ?, categorie_id = ? " +
                     "WHERE transaction.id = ?");)
        {
            preparedStatement.setInt(5, transaction.getId());
            preparedStatement.setInt(1, transaction.getAccountID());
            preparedStatement.setLong(2, transaction.getSum());
            preparedStatement.setDate(3, (Date) transaction.getDate());
            preparedStatement.setInt(4, transaction.getCategorieID());

            if (preparedStatement.executeUpdate() > 0) {
                return transaction;
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

            if (statement.executeUpdate("DELETE * FROM  transaction WHERE (transaction.id = " + id + ")") != 0) {
                return true;
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }

        return false;
    }


    public List<Transaction> findByAccountId(int accountId) {
    List<Transaction> list = new ArrayList<>();

    try (Connection connection = getConnection();
         Statement statement = connection.createStatement()) {

        ResultSet rs = statement.executeQuery("Select * From transaction " +
                "WHERE transaction.account_id =" + accountId);

        while (rs.next()) {
            Transaction transaction = new Transaction();
            list.add(getAccount(rs, transaction));
        }
    }
    catch (SQLException exept) {
        throw new RuntimeException(exept);
    }

    return list;
    }

    public List<Transaction> findByAccountIdAndData(int accountId, Date date) {
        List<ru.titov.s02.dao.domain.Transaction> list = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery("Select * From transaction " +
                    "WHERE transaction.account_id =" + accountId + " and transaction.date =" + date);

            while (rs.next()) {
                ru.titov.s02.dao.domain.Transaction transaction = new Transaction();
                list.add(getAccount(rs, transaction));
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }

        return list;
    }

    public List<Transaction> findByAccountIdCategorieId(int accountId, int categorieId) {
        List<ru.titov.s02.dao.domain.Transaction> list = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery("Select * From transaction " +
                    "WHERE transaction.account_id =" + accountId +
                    " and transaction.categorie_id = " + categorieId);

            while (rs.next()) {
                ru.titov.s02.dao.domain.Transaction transaction = new Transaction();
                list.add(getAccount(rs, transaction));
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }

        return list;
    }

    public List<Transaction> findByAccountIdCategorieIdDate(int accountId, int categorieId, Date date) {
        List<ru.titov.s02.dao.domain.Transaction> list = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery("Select * From transaction " +
                    "WHERE transaction.account_id =" + accountId +
                    " and transaction.categorie_id = " + categorieId + " and transaction.date = " + date);

            while (rs.next()) {
                ru.titov.s02.dao.domain.Transaction transaction = new Transaction();
                list.add(getAccount(rs, transaction));
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }

        return list;
    }
}