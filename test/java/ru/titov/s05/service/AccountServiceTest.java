package ru.titov.s05.service;

import javafx.beans.binding.When;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.titov.s05.dao.AccountDao;
import ru.titov.s05.dao.CurrencyDao;
import ru.titov.s05.dao.DaoFactory;
import ru.titov.s05.dao.PersonDao;
import ru.titov.s05.dao.domain.Account;
import ru.titov.s05.dao.domain.Currency;
import ru.titov.s05.dao.domain.Person;
import ru.titov.s05.service.converters.AccountConverter;
import ru.titov.s05.service.converters.UserConverter;
import ru.titov.s05.service.dto.AccountDto;
import ru.titov.s05.service.dto.UserDto;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @InjectMocks AccountService subj;
    @Mock AccountDao accountDao;
    @Mock AccountConverter accountConverter;
    @Mock AccountService accountService;
    @Mock CurrencyDao currencyDao;
    @Mock PersonDao personDao;
    @Mock Connection connectionMock;



    @Test
    public void createNewAccount_ok() {
        Account account = new Account();
        account.setId(3);
        account.setPersonID(13);

        Person person = new Person();

        AccountDto accountDto = new AccountDto();
        accountDto.setCurrencyId(123);
        accountDto.setNumberAccount(9);
        Currency currency = new Currency();


        when(accountConverter.accountDtoToAccountConvert(accountDto)).thenReturn(account);
        when(personDao.findById(13, connectionMock)).thenReturn(person);
        when(accountDao.countAccountPerson(3, connectionMock)).thenReturn(0);
        when(accountService.checkCurrencyId(accountDto, connectionMock)).thenReturn(true);
        when(currencyDao.findById(123, connectionMock)).thenReturn(currency);
        when(accountDao.findByNumberAccount(9, connectionMock)).thenReturn(null);


        when(accountDao.insert(account, connectionMock)).thenReturn(account);
        when(accountConverter.accountToAccountDtoConvert(account)).thenReturn(accountDto);


        AccountDto accountDtoFromService = subj.createNewAccount(accountDto, connectionMock);
        assertEquals(accountDto, accountDtoFromService);
    }

    @Test
    public void findByPesonId_ok() {
        UserDto userDto = new UserDto();
        userDto.setId(12);
        Account account = new Account();
        AccountDto accountDto = new AccountDto();
        List<AccountDto> list = new ArrayList<>();
        list.add(accountDto);
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);



        when(accountDao.findByPersonId(12, connectionMock)).thenReturn(accountList);
        when(accountConverter.listAccountToListAccountDtoConvert(accountList)).thenReturn(list);

        List<AccountDto>  listFromService = subj.findByPesonId(userDto, connectionMock);
        assertEquals(list, listFromService);
    }

    @Test
    public void findByPesonId_idWrong() {
        UserDto userDto = new UserDto();
        userDto.setId(12);
        Account account = new Account();
        AccountDto accountDto = new AccountDto();
        List<AccountDto> list = new ArrayList<>();
        list.add(accountDto);
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);



        when(accountDao.findByPersonId(120, connectionMock)).thenReturn(accountList);
        when(accountConverter.listAccountToListAccountDtoConvert(accountList)).thenReturn(list);

        List<AccountDto>  listFromService = subj.findByPesonId(userDto, connectionMock);
        assertNotEquals(list, listFromService);
    }


    @Test
    public void updateAccount_ok() {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(45);

        Account account = new Account();
        account.setId(3);

        when(accountConverter.accountDtoToAccountConvert(accountDto)).thenReturn(account);
        when(accountDao.findById(3, connectionMock)).thenReturn(account);
        when(accountDao.update(account, connectionMock)).thenReturn(account);
        when(accountConverter.accountToAccountDtoConvert(account)).thenReturn(accountDto);


        AccountDto deleteAccount = subj.updateAccount(accountDto, connectionMock);

        assertEquals(accountDto, deleteAccount);

    }

    @Test
    public void updateAccount_accountIdWrong() {
        AccountDto accountDto = new AccountDto();

        Account account = new Account();
        account.setId(30);

        when(accountConverter.accountDtoToAccountConvert(accountDto)).thenReturn(account);
        when(accountDao.findById(3, connectionMock)).thenReturn(account);
        when(accountDao.update(account, connectionMock)).thenReturn(account);
        when(accountConverter.accountToAccountDtoConvert(account)).thenReturn(accountDto);


        AccountDto deleteAccount = subj.updateAccount(accountDto, connectionMock);

        assertNotEquals(accountDto, deleteAccount);

    }

    @Test
    public void deleteAccount_ok() {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(23);

        when(accountDao.delete(23, connectionMock)).thenReturn(true);

        Boolean deleteAccount = subj.deleteAccount(accountDto, connectionMock);

        assertTrue(deleteAccount);
    }

    @Test
    public void deleteAccount_accountDtoIdWrong() {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(23);

        when(accountDao.delete(230, connectionMock)).thenReturn(true);

        Boolean deleteAccount = subj.deleteAccount(accountDto, connectionMock);

        assertFalse(deleteAccount);
    }

    @Test
    public void checkCurrencyId_ok() {
        AccountDto accountDto = new AccountDto();
        accountDto.setCurrencyId(102);
        Currency currency = new Currency();

        when(currencyDao.findById(102, connectionMock)).thenReturn(currency);

        Boolean result = subj.checkCurrencyId(accountDto, connectionMock);
        assertTrue(result);

    }



}