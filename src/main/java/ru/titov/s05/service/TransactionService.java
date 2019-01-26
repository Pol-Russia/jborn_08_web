package ru.titov.s05.service;

import ru.titov.s05.dao.AccountDao;
import ru.titov.s05.dao.CategorieDao;
import ru.titov.s05.dao.TransactionDao;
import ru.titov.s05.dao.domain.Account;
import ru.titov.s05.dao.domain.Categorie;
import ru.titov.s05.dao.domain.Transaction;
import ru.titov.s05.service.converters.TransactionConverter;
import ru.titov.s05.service.dto.AccountDto;
import ru.titov.s05.service.dto.CategorieDto;
import ru.titov.s05.service.dto.TransactionDto;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import static ru.titov.s05.dao.DaoFactory.getConnection;

public class TransactionService {
    private final TransactionDao transactionDao;
    private final CategorieDao categorieDao;
    private final TransactionConverter transactionConverter;
    private final AccountDao accountDao;

    public TransactionService(TransactionDao transactionDao, CategorieDao categorieDao, TransactionConverter transactionConverter, AccountDao accountDao) {
        this.transactionDao = transactionDao;
        this.categorieDao = categorieDao;
        this.transactionConverter = transactionConverter;
        this.accountDao = accountDao;
    }

    public boolean transfer(Account accountFrom, Account accountTo, BigDecimal sum, Categorie categorie, Connection connection) throws SQLException {


        try {
            connection.setAutoCommit(false);

            if (accountFrom.getBalance().compareTo(sum) >= 0) {

                //вычесть средства со счета
                accountFrom.setBalance(accountFrom.getBalance().add(sum.negate()));

                //положить
                accountTo.setBalance(accountTo.getBalance().add(sum));

                //внести изменения на счета
                accountDao.update(accountFrom, connection);
                accountDao.update(accountTo, connection);


                //Закончить транзакцию успешно
                Transaction transaction = new Transaction();
                transaction.setAccountID(accountFrom.getId());
                transaction.setSum((sum).negate());
                transaction.setCategorieID(categorie.getId());
                transaction.setDate(now());
                transactionDao.insert(transaction, connection);

                Transaction transaction2 = transaction;
                transaction2.setSum(sum);
                transactionDao.insert(transaction2, connection);

                connection.commit();
                return true;
            }
            else {
                connection.rollback();
                return false;
            }

        }

        catch (Exception sql) {
            connection.rollback();
            return false;
        }

        finally {
            connection.setAutoCommit(true);

            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            }
            catch (Exception exp) {
                throw  new RuntimeException(exp);
            }
        }
    }

    public boolean deleteTransfer(Account account1, Account account2, BigDecimal sum, Categorie categorie, Connection connection) throws SQLException {

        try {
            connection.setAutoCommit(false);


            account1.setBalance(account1.getBalance().add(sum));
            account2.setBalance(account2.getBalance().add(sum.negate()));

            //внести изменения на счета
            accountDao.update(account1, connection);
            accountDao.update(account2, connection);

            //Закончить транзакцию успешно

            Transaction transaction = new Transaction();
            transaction.setAccountID(account1.getId());
            transaction.setCategorieID(categorie.getId());
            transaction.setDate(now());
            transaction.setSum(sum);
            transactionDao.insert(transaction, connection);

            Transaction transaction2 = transaction;
            transaction2.setSum(sum.negate());
            transactionDao.insert(transaction2, connection);

            connection.commit();
            return true;
        }

        catch (SQLException sql) {
            connection.rollback();
        }

        finally {
            connection.setAutoCommit(true);
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            }
            catch (Exception exp) {
                throw  new RuntimeException(exp);
            }
        }
        return false;
    }

    public Transaction addSum(Account account, BigDecimal sum,  Categorie categorie, Connection connection) throws SQLException { //Положить на счет


        try {
            connection.setAutoCommit(false);

            //положить
            account.setBalance(account.getBalance().add(sum));

            //внести изменения на счета
            accountDao.update(account, connection);


            //Закончить транзакцию успешно

            Transaction transaction = new Transaction();
            transaction.setSum(sum);
            transaction.setAccountID(account.getId());
            transaction.setCategorieID(categorie.getId());
            transaction.setDate(now());
            transactionDao.insert(transaction, connection);

            connection.commit();
            return transaction;
        }
        catch (Exception exp){
            connection.rollback();
            throw new RuntimeException(exp);

        }

        finally {

            connection.setAutoCommit(true);
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            }
            catch (Exception exp) {
                throw  new RuntimeException(exp);
            }
        }
    }

    public Transaction takeSum(Account account, BigDecimal sum,  Categorie categorie, Connection connection) throws SQLException {

        try {
            connection.setAutoCommit(false);

            //положить
            account.setBalance(account.getBalance().add(sum.negate()));

            //внести изменения на счета
            accountDao.update(account, connection);


            //Закончить транзакцию успешно
            Transaction transaction = new Transaction();
            transaction.setSum(sum.negate());
            transaction.setAccountID(account.getId());
            transaction.setCategorieID(categorie.getId());
            transaction.setDate(now());
            transactionDao.insert(transaction, connection);

            connection.commit();
            return transaction;
        }
        catch (Exception exp){
            connection.rollback();
            throw new RuntimeException(exp);

        }

        finally {

            connection.setAutoCommit(true);
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            }
            catch (Exception exp) {
                throw  new RuntimeException(exp);
            }
        }

    }


    private boolean checkAccountId(AccountDto accountDto, Connection connection) {
        AccountDao accountDao = null;

        if (accountDao.findById(accountDto.getId(), connection) != null) {
            return true;
        }

        return false;
    }

    private boolean checkCategorieId(CategorieDto categorieDto, Connection connection) {

        if (categorieDao.findById(categorieDto.getId(), connection) != null) { //Существует
            return true;
        }

        return false; //Не существует

    }

    public TransactionDto createNewTransaction(TransactionDto transactionDto, Connection connection) {

        if (transactionDto == null) {
            return null;
        }

        if (accountDao.findById(transactionDto.getAccountID(), connection) != null && categorieDao.findById(transactionDto.getCategorieID(), connection) != null) {

            Transaction transaction = transactionConverter.transactionDtoToTransactionConvert(transactionDto);
            transactionDto = transactionConverter.transactionToTransactionDtoConvert(transactionDao.insert(transaction, connection));
            return transactionDto;
        }
        return null;
    }

    public TransactionDto updateTransaction(TransactionDto transactionDto, Connection connection) {

        Transaction transaction = transactionDao.findById(transactionDto.getId(), connection);

        if (transaction != null) {

            transactionDto = transactionConverter.transactionToTransactionDtoConvert(transactionDao.update(transaction, connection));

            return transactionDto;
        }

        return null;
    }

    public boolean deleteTransaction(TransactionDto transactionDto, Connection connection) {

        return transactionDao.delete(transactionDto.getId(), connection);
    }

    public List<TransactionDto> findTransactionByAccountId(AccountDto accountDto, Connection connection) {

        return  transactionConverter.listTransactionToListTransactionDtoConvert(transactionDao.findByAccountId(accountDto.getId(), connection));
    }

    public List<TransactionDto> getAllTransaction(AccountDto accountDto, Connection connection) {

        return  transactionConverter.listTransactionToListTransactionDtoConvert(transactionDao.findAll());
    }

    private String now() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        return dateFormat.format(new java.util.Date());
    }


}


