package ru.titov.s06.service;

import ru.titov.s06.dao.AccountDao;
import ru.titov.s06.dao.CurrencyDao;
import ru.titov.s06.dao.DaoFactory;
import ru.titov.s06.dao.PersonDao;
import ru.titov.s06.dao.domain.Account;
import ru.titov.s06.dao.domain.Person;
import ru.titov.s06.service.converters.AccountConverter;
import ru.titov.s06.service.dto.AccountDto;
import ru.titov.s06.service.dto.UserDto;

import java.math.BigDecimal;
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

    public AccountDto createNewAccount(AccountDto accountDto) {

        if (accountDto == null) {
             return null;
         }

         Account account = accountConverter.accountDtoToAccountConvert(accountDto);

         if (personDao.findById(account.getPersonID()) != null) {
             int count = accountDao.countAccountPerson(account.getPersonID());

             if (count < DaoFactory.maxCountAccount && checkCurrencyId(accountDto) && checkNumberAccount(accountDto)) { // Не более 5 счетов в одни руки!

                 return accountConverter.accountToAccountDtoConvert(accountDao.insert(account));
             }
         }

        return null;
    }

    public List<AccountDto> downloadListAccount() {

        return accountConverter.listAccountToListAccountDtoConvert(accountDao.findByAll());
    }

    public List<AccountDto> findByPesonId(UserDto personDto) {

        return accountConverter.listAccountToListAccountDtoConvert(accountDao.findByPersonId(personDto.getId()));
    }

    private boolean checkNumberAccount(AccountDto accountDto) {

        if (accountDao.findByNumberAccount(accountDto.getNumberAccount()) != null) {

            return false;
        }

        return true;
    }

    private boolean checkPersonId(AccountDto accountDto) {


        if (DaoFactory.getPersonDao().findById(accountDto.getPersonId()) != null) {
            return true;
        }
        return false;
    }

    public boolean checkCurrencyId(AccountDto accountDto) {


        if (currencyDao.findById(accountDto.getCurrencyId()) != null) { //Существует
            return true;
        }

        return false; //Не существует
    }

    public AccountDto updateAccount(AccountDto accountDto) {

        Account account = accountConverter.accountDtoToAccountConvert(accountDto);

        if (accountDao.findById(account.getId()) != null) {

               return accountConverter.accountToAccountDtoConvert(accountDao.update(account));
            }

        return null;
    }

    public boolean deleteAccount(AccountDto accountDto) {

        return accountDao.delete(accountDto.getId());
    }
}
