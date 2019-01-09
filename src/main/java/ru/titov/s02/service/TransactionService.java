package ru.titov.s02.service;

import ru.titov.s02.dao.AccountDao;
import ru.titov.s02.dao.CategorieDao;
import ru.titov.s02.dao.TransactionDao;
import ru.titov.s02.dao.domain.Transaction;
import ru.titov.s02.service.converters.TransactionConverter;
import ru.titov.s02.service.dto.AccountDto;
import ru.titov.s02.service.dto.CategorieDto;
import ru.titov.s02.service.dto.TransactionDto;
import ru.titov.s02.service.dto.UserDto;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
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

    public TransactionDto updateTransaction(TransactionDto transactionDto) throws ParseException {

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

    public List<TransactionDto> findTransactionByAccountId(UserDto userDto) {

        List<AccountDto> listAccountDto = new AccountService().findByPesonId(userDto);
        if (listAccountDto.isEmpty()) {
            return null;
        }

        TransactionConverter converter = new TransactionConverter();
        List<TransactionDto> transaction = new ArrayList<>();

        for (AccountDto dto : listAccountDto) {
            transaction.addAll(converter.listTransactionToListTransactionDtoConvert(transactionDao.findByAccountId(dto.getId())));
        }

        return  transaction;
    }

    public Transaction transfer(AccountDto accountDto1, AccountDto accountDto2) {

        //transactionDao.

        return null;
    }

}


