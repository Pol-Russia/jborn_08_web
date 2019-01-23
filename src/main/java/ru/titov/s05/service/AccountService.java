package ru.titov.s05.service;

import ru.titov.s05.dao.AccountDao;
import ru.titov.s05.dao.CurrencyDao;
import ru.titov.s05.dao.DaoFactory;
import ru.titov.s05.dao.PersonDao;
import ru.titov.s05.dao.domain.Account;
import ru.titov.s05.dao.domain.Person;
import ru.titov.s05.service.converters.AccountConverter;
import ru.titov.s05.service.dto.AccountDto;
import ru.titov.s05.service.dto.UserDto;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private final AccountDao accountDao;
    private final AccountConverter accountConverter;
    private final CurrencyDao currencyDao;
    private final PersonDao personDao;

    public AccountService(AccountDao accountDao, AccountConverter accountConverter, CurrencyDao currencyDao, PersonDao personDao) {
        this.accountDao = accountDao;
        this.accountConverter = accountConverter;
        this.currencyDao = currencyDao;
        this.personDao = personDao;
    }

    public AccountDto createNewAccount(AccountDto accountDto, Connection connection) {

        if (accountDto == null) {
             return null;
         }

         Account account = accountConverter.accountDtoToAccountConvert(accountDto);

         if (personDao.findById(account.getPersonID(), connection) != null) {
             int count = accountDao.countAccountPerson(account.getPersonID(), connection);

             if (count < DaoFactory.maxCountAccount && checkCurrencyId(accountDto, connection) && checkNumberAccount(accountDto, connection)) { // Не более 5 счетов в одни руки!

                 return accountConverter.accountToAccountDtoConvert(accountDao.insert(account, connection));
             }
         }

        return null;
    }

    public List<AccountDto> downloadListAccount() {

        return accountConverter.listAccountToListAccountDtoConvert(accountDao.findByAll());
    }

    public List<AccountDto> findByPesonId(UserDto personDto, Connection connection) {

        return accountConverter.listAccountToListAccountDtoConvert(accountDao.findByPersonId(personDto.getId(), connection));
    }

    private boolean checkNumberAccount(AccountDto accountDto, Connection connection) {

        if (accountDao.findByNumberAccount(accountDto.getNumberAccount(), connection) != null) {

            return false;
        }

        return true;
    }

    private boolean checkPersonId(AccountDto accountDto, Connection connection) {


        if (DaoFactory.getPersonDao().findById(accountDto.getPersonId(), connection) != null) {
            return true;
        }
        return false;
    }

    public boolean checkCurrencyId(AccountDto accountDto, Connection connection) {


        if (currencyDao.findById(accountDto.getCurrencyId(), connection) != null) { //Существует
            return true;
        }

        return false; //Не существует
    }

    public AccountDto updateAccount(AccountDto accountDto, Connection connection) {

        Account account = accountConverter.accountDtoToAccountConvert(accountDto);

        if (accountDao.findById(account.getId(), connection) != null) {

               return accountConverter.accountToAccountDtoConvert(accountDao.update(account, connection));
            }

        return null;
    }

    public boolean deleteAccount(AccountDto accountDto, Connection connection) {

        return accountDao.delete(accountDto.getId(), connection);
    }
}
