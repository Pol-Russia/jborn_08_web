package ru.titov.s04.service;

import ru.titov.s04.dao.AccountDao;
import ru.titov.s04.dao.CategorieDao;
import ru.titov.s04.dao.DaoFactory;
import ru.titov.s04.dao.TransactionDao;
import ru.titov.s04.dao.domain.Account;
import ru.titov.s04.dao.domain.Categorie;
import ru.titov.s04.dao.domain.Transaction;
import ru.titov.s04.service.converters.TransactionConverter;
import ru.titov.s04.service.dto.AccountDto;
import ru.titov.s04.service.dto.CategorieDto;
import ru.titov.s04.service.dto.TransactionDto;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static ru.titov.s04.dao.DaoFactory.getConnection;

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

    public boolean transfer(Account account1, Account account2, BigDecimal sum, Categorie categorie) throws SQLException {


        Connection connection = getConnection();

        try {
            connection.setAutoCommit(false);

            if (account1.getBalance().compareTo(sum) >= 0) {

                //вычесть средства со счета
                account1.setBalance(account1.getBalance().subtract(sum));

                //положить
                account2.setBalance(account2.getBalance().add(sum));

                //внести изменения на счета
                accountDao.update(account1);
                accountDao.update(account2);

                //Закончить транзакцию успешно

                Transaction transaction = new Transaction();
                transaction.setAccountID(account1.getId());
                transaction.setSum((sum).negate());
                transaction.setCategorieID(categorie.getId());
                transaction.setDate(now());
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
            account2.setBalance(account2.getBalance().subtract(sum));

            //внести изменения на счета
            accountDao.update(account1);
            accountDao.update(account2);

            //Закончить транзакцию успешно

            Transaction transaction = new Transaction();
            transaction.setAccountID(account1.getId());
            transaction.setCategorieID(categorie.getId());
            transaction.setDate(now());
            transaction.setSum(sum);
            transactionDao.insert(transaction);

            Transaction transaction2 = transaction;
            transaction2.setSum(sum.negate());
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

    public Transaction addSum(Account account, BigDecimal sum,  Categorie categorie) throws SQLException { //Положить на счет
        Connection connection = getConnection();

        try {
            connection.setAutoCommit(false);

            //положить
            account.setBalance(account.getBalance().add(sum));

            //внести изменения на счета
            accountDao.update(account);


            //Закончить транзакцию успешно

            Transaction transaction = new Transaction();
            transaction.setSum(sum);
            transaction.setAccountID(account.getId());
            transaction.setCategorieID(categorie.getId());
            transaction.setDate(now());
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

    public Transaction takeSum(Account account, BigDecimal sum,  Categorie categorie) throws SQLException {
        Connection connection = getConnection();

        try {
            connection.setAutoCommit(false);

            //положить
            account.setBalance(account.getBalance().subtract(sum));

            //внести изменения на счета
            accountDao.update(account);


            //Закончить транзакцию успешно
            Transaction transaction = new Transaction();
            transaction.setSum(sum.negate());
            transaction.setAccountID(account.getId());
            transaction.setCategorieID(categorie.getId());
            transaction.setDate(now());
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


    private boolean checkAccountId(AccountDto accountDto) {
        AccountDao accountDao = null;

        if (accountDao.findById(accountDto.getId()) != null) {
            return true;
        }

        return false;
    }

    private boolean checkCategorieId(CategorieDto categorieDto) {

        if (categorieDao.findById(categorieDto.getId()) != null) { //Существует
            return true;
        }

        return false; //Не существует

    }

    public TransactionDto createNewTransaction(TransactionDto transactionDto) {

        if (transactionDto == null) {
            return null;
        }

        if (accountDao.findById(transactionDto.getAccountID()) != null && categorieDao.findById(transactionDto.getCategorieID()) != null) {

            Transaction transaction = transactionConverter.transactionDtoToTransactionConvert(transactionDto);
            transactionDto = transactionConverter.transactionToTransactionDtoConvert(transactionDao.insert(transaction));
            return transactionDto;
        }
        return null;
    }

    public TransactionDto updateTransaction(TransactionDto transactionDto) {

        Transaction transaction = transactionDao.findById(transactionDto.getId());

        if (transaction != null) {

            transactionDto = transactionConverter.transactionToTransactionDtoConvert(transactionDao.update(transaction));

            return transactionDto;
        }

        return null;
    }

    public boolean deleteTransaction(TransactionDto transactionDto) {

        return transactionDao.delete(transactionDto.getId());
    }

    public List<TransactionDto> findTransactionByAccountId(AccountDto accountDto) {

        return  transactionConverter.listTransactionToListTransactionDtoConvert(transactionDao.findByAccountId(accountDto.getId()));
    }

    private String now() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        return dateFormat.format(new java.util.Date());
    }


}


