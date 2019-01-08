package ru.titov.s02.service;

import ru.titov.s02.dao.AccountDao;
import ru.titov.s02.dao.CurrencyDao;
import ru.titov.s02.dao.DaoFactory;
import ru.titov.s02.dao.PersonDao;
import ru.titov.s02.dao.domain.Account;
import ru.titov.s02.service.converters.AccountConverter;
import ru.titov.s02.service.dto.AccountDto;
import ru.titov.s02.service.dto.MaxCountAccountException;
import ru.titov.s02.service.dto.UserDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountService {

    private final AccountDao accountDao = new AccountDao();

    public AccountDto createNewAccount(AccountDto accountDto) throws  MaxCountAccountException{

         if (accountDto == null) {
             return null;
         }

         Account account = new AccountConverter().accountDtoToAccountConvert(accountDto);
         int count = accountDao.checkByPersonId(account.getPersonID());

        if (count < DaoFactory.maxCountAccount && checkCurrencyId(accountDto)) { // Не более maxCountAccount счетов в одни руки!

            return new AccountConverter().accountToAccountDtoConvert(accountDao.insert(account));
        }
        else {
            //Исключение
            throw new MaxCountAccountException("Достигнуто предельное количество счетов " + DaoFactory.maxCountAccount +  " для " + new PersonService().findById(account.getPersonID()).toString() , count);
        }
    }

    public List<AccountDto> downloadListAccount() {

        return new AccountConverter().listAccountToListAccountDtoConvert(accountDao.findByAll());
    }

    public List<AccountDto> findByPesonId(UserDto personDto) {

        return new AccountConverter().listAccountToListAccountDtoConvert(accountDao.findByPersonId(personDto.getId()));
    }

    public boolean checkNumberAccount(AccountDto accountDto) {

        if (accountDao.findByNumberAccount(accountDto.getNumberAccount()).isEmpty()) {

            return true;
        }

        return false;
    }

    private boolean checkPersonId(AccountDto accountDto) {
        PersonDao personDao = new PersonDao();

        if (personDao.findById(accountDto.getPersonId()) != null) {
            return true;
        }
        return false;
    }

    private boolean checkCurrencyId(AccountDto accountDto) {
        CurrencyDao currencyDao = new CurrencyDao();

        if (currencyDao.findById(accountDto.getCurrencyId()) != null) { //Существует
            return true;
        }

        return false; //Не существует
    }

    public AccountDto updateAccount(AccountDto accountDto) {

        Account account = new AccountConverter().accountDtoToAccountConvert(accountDto);

        if (accountDao.findById(account.getId()) != null) {

               return new AccountConverter().accountToAccountDtoConvert(accountDao.update(account));
            }

        return null;
    }

    public boolean deleteAccount(AccountDto accountDto) {

        return accountDao.delete(accountDto.getId());
    }
}
