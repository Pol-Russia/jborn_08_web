package ru.titov.s02.service.converters;

import ru.titov.s02.dao.domain.Transaction;
import ru.titov.s02.view.console.Talking;
import ru.titov.s02.view.dto.TransactionDto;

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


}
