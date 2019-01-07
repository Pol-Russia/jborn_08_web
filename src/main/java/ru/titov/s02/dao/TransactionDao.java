package ru.titov.s02.dao;

import ru.titov.s02.dao.domain.Account;
import ru.titov.s02.dao.domain.Categorie;
import ru.titov.s02.dao.domain.Transaction;

import java.math.BigDecimal;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static ru.titov.s02.dao.DaoFactory.getConnection;

public class TransactionDao implements Dao<Transaction, Integer> {

    private  Transaction getTransaction(ResultSet rs, Transaction transaction) throws SQLException {

        transaction.setId(rs.getInt(1));
        transaction.setAccountID(rs.getInt(2));
        transaction.setSum(rs.getBigDecimal(3));
        transaction.setDate(rs.getString(4));
        transaction.setCategorieID(rs.getInt(5));

        return transaction;
    }

    private void setTransaction(PreparedStatement preparedStatement, Transaction transaction) throws SQLException {
        preparedStatement.setInt(1, transaction.getAccountID());
        preparedStatement.setBigDecimal(2, transaction.getSum());
        preparedStatement.setString(3, transaction.getDate());
        preparedStatement.setInt(4, transaction.getCategorieID());
    }

    @Override
    public Transaction findById(Integer id) {
        Transaction transaction = new Transaction();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("Select * From transaction" +
                     "WHERE (transaction.id = ?)")) {

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return getTransaction(rs, transaction);
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
                list.add(getTransaction(rs, transaction));
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
                     "account_id, sum, date, categorie_id) VALUES( ?, ?, ?, ? )", Statement.RETURN_GENERATED_KEYS);)
        {

            setTransaction(preparedStatement, transaction);
            preparedStatement.executeUpdate();

                return transaction;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Transaction update(Transaction transaction) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE transaction SET " +
                     "account_id = ?, sum = ?, date = ?, categorie_id = ? " +
                     "WHERE transaction.id = ?");)
        {
            preparedStatement.setInt(5, transaction.getId());
            setTransaction(preparedStatement, transaction);


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
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE * FROM  transaction WHERE (transaction.id = ?")) {


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


    public List<Transaction> findByAccountId(int accountId) {
    List<Transaction> list = new ArrayList<>();

    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement("Select * From transaction " +
                 "WHERE transaction.account_id = ?")) {

        preparedStatement.setInt(1, accountId);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            Transaction transaction = new Transaction();
            list.add(getTransaction(rs, transaction));
        }
    }
    catch (SQLException exept) {
        throw new RuntimeException(exept);
    }

    return list;
    }

    public List<Transaction> findByAccountIdAndData(int accountId, Date date) {
        List<Transaction> list = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("Select * From transaction " +
                     "WHERE transaction.account_id = ? and transaction.date = ?")) {

            preparedStatement.setInt(1, accountId);
            preparedStatement.setDate(2, date);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction();
                list.add(getTransaction(rs, transaction));
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }

        return list;
    }

    public List<Transaction> findByAccountIdCategorieId(int accountId, int categorieId) {
        List<Transaction> list = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("Select * From transaction " +
                     "WHERE transaction.account_id = ? and transaction.categorie_id = ?")) {

            preparedStatement.setInt(1, accountId);
            preparedStatement.setInt(2, categorieId);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction();
                list.add(getTransaction(rs, transaction));
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }

        return list;
    }

    public List<Transaction> findByAccountIdCategorieIdDate(int accountId, int categorieId, Date date) {
        List<Transaction> list = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("Select * From transaction WHERE transaction.account_id =? and transaction.categorie_id = ? and transaction.date = ?")) {

            preparedStatement.setInt(1, accountId);
            preparedStatement.setInt(2, categorieId);
            preparedStatement.setDate(3, date);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ru.titov.s02.dao.domain.Transaction transaction = new Transaction();
                list.add(getTransaction(rs, transaction));
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }

        return list;
    }

    public boolean transfer(Account account1, Account account2, BigDecimal sum, Categorie categorie) throws SQLException {


           Connection connection = getConnection();

        try {
           connection.setAutoCommit(false);

           if (account1.getBalance().compareTo(sum) >= 0) {

               //вычесть средства со счета
               account1.setBalance(account1.getBalance().divide(sum));

               //положить
               account2.setBalance(account2.getBalance().add(sum));

               //внести изменения на счета
               AccountDao accountDao = new AccountDao();
               accountDao.update(account1);
               accountDao.update(account2);

               //Закончить транзакцию успешно

               Transaction transaction = new Transaction();
               transaction.setAccountID(account1.getId());
               BigDecimal number = BigDecimal.valueOf(0);
               transaction.setSum(number.divide(sum));
               transaction.setCategorieID(categorie.getId());
               transaction.setDate(now());
               TransactionDao transactionDao = new TransactionDao();
               transactionDao.insert(transaction);

               Transaction transaction2 = transaction;
               transaction2.setSum(sum);
               transactionDao.insert(transaction2);

               connection.commit();
               return true;
           }
           else {
               connection.rollback();
               return false;
           }

       }

       catch (SQLException sql) {
           connection.rollback();
           return false;
       }

       finally {
                 connection.setAutoCommit(true);
       }
    }

    public boolean deleteTransfer(Account account1, Account account2, BigDecimal sum, Categorie categorie) throws SQLException {

        Connection connection = getConnection();

        try {
            connection.setAutoCommit(false);


                account1.setBalance(account1.getBalance().add(sum));
                account2.setBalance(account2.getBalance().divide(sum));

                //внести изменения на счета
                AccountDao accountDao = new AccountDao();
                accountDao.update(account1);
                accountDao.update(account2);

                //Закончить транзакцию успешно

                Transaction transaction = createTransaction(account1, categorie);
                transaction.setSum(sum);
                TransactionDao transactionDao = new TransactionDao();
                transactionDao.insert(transaction);

                Transaction transaction2 = transaction;
                transaction2.setSum(sum.multiply(BigDecimal.valueOf(-1)));
                transaction2.setSum(sum);
                transactionDao.insert(transaction2);

                connection.commit();
                return true;
        }

        catch (SQLException sql) {
            connection.rollback();
        }

        finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    public Transaction addSum(Account account, BigDecimal sum,  Categorie categorie) throws SQLException {
        Connection connection = getConnection();

        try {
            connection.setAutoCommit(false);

                //положить
                account.setBalance(account.getBalance().add(sum));

                //внести изменения на счета
                AccountDao accountDao = new AccountDao();
                accountDao.update(account);


                //Закончить транзакцию успешно

            Transaction transaction = createTransaction(account, categorie);
            transaction.setSum(sum);

            TransactionDao transactionDao = new TransactionDao();
            transactionDao.insert(transaction);

                connection.commit();
                return transaction;
            }
            catch (SQLException exp){
                connection.rollback();
                throw new RuntimeException(exp);

            }

         finally {

            connection.setAutoCommit(true);
        }
    }

    public Transaction divideSum(Account account, BigDecimal sum,  Categorie categorie) throws SQLException {
        Connection connection = getConnection();

        try {
            connection.setAutoCommit(false);

            //положить
            account.setBalance(account.getBalance().divide(sum));

            //внести изменения на счета
            AccountDao accountDao = new AccountDao();
            accountDao.update(account);


            //Закончить транзакцию успешно
            Transaction transaction = createTransaction(account, categorie);
            transaction.setSum(sum.multiply(BigDecimal.valueOf(-1)));

            TransactionDao transactionDao = new TransactionDao();
            transactionDao.insert(transaction);

            connection.commit();
            return transaction;
        }
        catch (SQLException exp){
            connection.rollback();
            throw new RuntimeException(exp);

        }

        finally {

            connection.setAutoCommit(true);
        }

    }

    private String now() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        return dateFormat.format(new java.util.Date());
    }

    private Transaction createTransaction(Account account, Categorie categorie) {

        Transaction transaction = new Transaction();
        transaction.setAccountID(account.getId());
        transaction.setCategorieID(categorie.getId());
        transaction.setDate(now());
        TransactionDao transactionDao = new TransactionDao();
        transactionDao.insert(transaction);

        return transaction;

    }
}