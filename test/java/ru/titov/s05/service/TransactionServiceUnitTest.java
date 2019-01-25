package ru.titov.s05.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.runners.MockitoJUnitRunner;
import ru.titov.s05.dao.*;
import ru.titov.s05.dao.domain.*;
import ru.titov.s05.service.converters.TransactionConverter;
import ru.titov.s05.service.dto.AccountDto;
import ru.titov.s05.service.dto.TransactionDto;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceUnitTest {


    @InjectMocks TransactionService subj;
    AccountDao accountDao;
    PersonDao personDao;
    CurrencyDao currencyDao;
    CategorieDao categorieDao;
    @Mock TransactionConverter transactionConverter;
    @Mock TransactionDao transactionDao;
    @Mock AccountDao accountDaoMock;
    @Mock CategorieDao categorieDaoMock;
    @Mock TransactionDao transactionDaoMock;
    @Mock Connection connectionMock;



    @Test
    public void createNewTransaction() {

        TransactionDto transactionDto = new TransactionDto();
        Transaction transaction = new Transaction();
        transactionDto.setCategorieID(22);
        transactionDto.setAccountID(2);
        Account account = new Account();
        Categorie categorie = new Categorie();

            when(transactionConverter.transactionDtoToTransactionConvert(transactionDto)).thenReturn(transaction);
            when(accountDaoMock.findById(2, connectionMock)).thenReturn(account);
            when(categorieDaoMock.findById(22, connectionMock)).thenReturn(categorie);
            when(transactionDaoMock.insert(transaction, connectionMock)).thenReturn(transaction);
            when(transactionConverter.transactionToTransactionDtoConvert(transaction)).thenReturn(transactionDto);


        TransactionDto transactionDtoFromService = subj.createNewTransaction(transactionDto, connectionMock);
        assertEquals(transactionDto, transactionDtoFromService);

    }

    @Test
    public void updateTransaction_transactionDtoIdWrong() {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(22);
        Transaction transaction = new Transaction();

        when(transactionDaoMock.findById(23, connectionMock)).thenReturn(transaction);
        when(transactionDaoMock.update(transaction, connectionMock)).thenReturn(transaction);
        when(transactionConverter.transactionToTransactionDtoConvert(transaction)).thenReturn(transactionDto);
        TransactionDto transactionDtoFromService = subj.updateTransaction(transactionDto, connectionMock);

        assertNotEquals(transactionDto, transactionDtoFromService);
    }

    @Test
    public void updateTransaction_ok() {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(22);
        Transaction transaction = new Transaction();

        when(transactionConverter.transactionToTransactionDtoConvert(transaction)).thenReturn(transactionDto);
        when(transactionDaoMock.findById(22, connectionMock)).thenReturn(transaction);
        when(transactionDaoMock.update(transaction, connectionMock)).thenReturn(transaction);
        TransactionDto transactionDtoFromService = subj.updateTransaction(transactionDto, connectionMock);

        assertEquals(transactionDto, transactionDtoFromService);
    }

    @Test
    public void deleteTransaction_ok() {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(22);

        when(transactionDaoMock.delete(22, connectionMock)).thenReturn(true);
        Boolean deleteTrue = subj.deleteTransaction(transactionDto, connectionMock);

        assertTrue(deleteTrue);
    }

    @Test
    public void deleteTransaction_transactionIdWrong() {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(22);

        when(transactionDaoMock.delete(23, connectionMock)).thenReturn(true);
        Boolean deleteCategorieTrue = subj.deleteTransaction(transactionDto, connectionMock);

        assertFalse(deleteCategorieTrue);
    }


    }