package ru.titov.s02.service;

import ru.titov.s02.dao.AccountDao;
import ru.titov.s02.dao.CategorieDao;
import ru.titov.s02.dao.TransactionDao;
import ru.titov.s02.dao.domain.Transaction;
import ru.titov.s02.service.converters.TransactionConverter;
import ru.titov.s02.service.dto.AccountDto;
import ru.titov.s02.service.dto.CategorieDto;
import ru.titov.s02.service.dto.TransactionDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TransactionService {
    private final TransactionDao transactionDao = new TransactionDao();


    private boolean checkAccountId(AccountDto accountDto) {
        AccountDao accountDao = null;

        if (accountDao.findById(accountDto.getId()) != null) {
            return true;
        }

        return false;
    }

    private boolean checkCategorieId(CategorieDto categorieDto) {

        CategorieDao categorieDao = new CategorieDao();

        if (categorieDao.findById(categorieDto.getId()) != null) { //Существует
            return true;
        }

        return false; //Не существует

    }

    public TransactionDto createNewTransaction(TransactionDto transactionDto) {

        if (transactionDto == null) {
            return null;
        }

        Transaction transaction = new TransactionConverter().transactionDtoToTransactionConvert(transactionDto);

        transactionDto = new TransactionConverter().transactionToTransactionDtoConvert(transactionDao.insert(transaction));

        return  transactionDto;
    }

    public TransactionDto updateTransaction(TransactionDto transactionDto) {

        Transaction transaction = transactionDao.findById(transactionDto.getId());

        if (transaction != null) {

            transactionDto = new TransactionConverter().transactionToTransactionDtoConvert(transactionDao.update(transaction));

            return transactionDto;
        }

        return null;
    }

    public boolean deleteTransaction(TransactionDto transactionDto) {

        return transactionDao.delete(transactionDto.getId());
    }

    public List<TransactionDto> findTransactionByAccountId(AccountDto accountDto) {

        return  new TransactionConverter().listTransactionToListTransactionDtoConvert(transactionDao.findByAccountId(accountDto.getId()));
    }


}


