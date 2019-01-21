package ru.titov.s06.service.converters;

import ru.titov.s06.dao.domain.Transaction;
import ru.titov.s06.service.dto.TransactionDto;

import java.util.ArrayList;
import java.util.List;

public class TransactionConverter {

    public Transaction transactionDtoToTransactionConvert(TransactionDto transactionDto) {

        if (transactionDto != null) {

            Transaction transaction = new Transaction();

            transaction.setId(transactionDto.getId());
            transaction.setAccountID(transactionDto.getAccountID());
            transaction.setSum(transactionDto.getSum());
            transaction.setDate(transactionDto.getDate());
            transaction.setCategorieID(transactionDto.getCategorieID());

            return transaction;
        }

        return null;
    }

    public TransactionDto transactionToTransactionDtoConvert(Transaction transaction) {

        if (transaction != null) {

            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setId(transaction.getId());
            transactionDto.setAccountID(transaction.getCategorieID());
            transactionDto.setSum(transaction.getSum());
            transactionDto.setDate(transaction.getDate());
            transactionDto.setCategorieID(transaction.getCategorieID());

            return transactionDto;
        }

        return null;
    }

    public List<TransactionDto> listTransactionToListTransactionDtoConvert(List<Transaction> transactions) {

        if (transactions == null) {
            return null;
        }

        List<TransactionDto> listTransactionDto = new ArrayList<>();
        TransactionConverter converter = new TransactionConverter();

        for (Transaction tran : transactions) {

            listTransactionDto.add(converter.transactionToTransactionDtoConvert(tran));
        }

        return listTransactionDto;
    }



}
