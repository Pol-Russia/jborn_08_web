package ru.titov.s02.service;

import ru.titov.s02.dao.domain.Transaction;
import ru.titov.s02.dao.domain.TransactionDao;

import java.util.Date;

import static ru.titov.s02.dao.DaoFactory.checkOrderId;

public class NewTransaction {
    private Transaction createNewTransaction(int accountId, long sum, Date date, int categorieId) {

            Transaction transaction = new Transaction();
            setTransaction(transaction, accountId, sum, date, categorieId);
            TransactionDao transactionDao = new TransactionDao();

            return  transactionDao.insert(transaction);
    }

    private void setTransaction(Transaction transaction, int accountId, long sum, Date date, int categorieId) {
        transaction.setAccountID(accountId);
        transaction.setSum(sum);
        transaction.setDate(date);
        transaction.setCategorieID(categorieId);

        String tableName = "transaction";
        int id = checkOrderId(tableName);

        transaction.setId(id);
    }
}
