package ru.titov.s02.service;

import ru.titov.s02.dao.AccountDao;
import ru.titov.s02.dao.CategorieDao;
import ru.titov.s02.dao.TransactionDao;
import ru.titov.s02.dao.domain.Transaction;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionService {
    private final TransactionDao transactionDao = new TransactionDao();

    private void setTransaction(Transaction transaction, int accountId, BigDecimal sum, Date date, int categorieId) {
        transaction.setAccountID(accountId);
        transaction.setSum(sum);
        transaction.setDate(date);
        transaction.setCategorieID(categorieId);
    }

    private boolean checkAccountId(int id) {
        AccountDao accountDao = null;

        if (accountDao.findById(id) != null) {
            return true;
        }

        return false;
    }

    private boolean checkCategorieId(int id) {

        CategorieDao categorieDao = new CategorieDao();

        if (categorieDao != null) { //Существует
            return true;
        }

        return false; //Не существует

    }

    public Transaction createNewTransaction(Transaction transaction) {

        if (transaction == null) {
            return null;
        }

        setTransaction(transaction, transaction.getAccountID(), transaction.getSum(), transaction.getDate(), transaction.getCategorieID());

        return  transactionDao.insert(transaction);
    }

    public Transaction updateTransaction(int id, int accountId, BigDecimal sum, Date date, int categorieId) {

        Transaction transaction = transactionDao.findById(id);

        if (transaction == null) {

            setTransaction(transaction, accountId, sum, date, categorieId);

            return transaction;
        }


        return null;
    }

    public boolean deleteTransaction(int id) {

        return transactionDao.delete(id);
    }
}


