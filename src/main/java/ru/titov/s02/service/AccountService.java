package ru.titov.s02.service;

import ru.titov.s02.dao.AccountDao;
import ru.titov.s02.dao.CurrencyDao;
import ru.titov.s02.dao.PersonDao;
import ru.titov.s02.dao.domain.Account;
import ru.titov.s02.dao.domain.Person;

import java.math.BigDecimal;
import java.util.List;

public class AccountService {

    private final AccountDao accountDao = new AccountDao();

    private void setAccount(Account account, int numberAccount, int personId, int currencyId, String description) {
        account.setNumberAccount(numberAccount);
        account.setPersonID(personId);
        account.setBalance(BigDecimal.valueOf(0));
        account.setCurrencyID(currencyId);
        account.setDescription(description);
    }

    public Account createNewAccount(Account account) {

         if (account == null) {
             return null;
         }

        if (accountDao.findByPersonId(account.getPersonID()).size() < 5 && checkPersonId(account.getPersonID()) && checkCurrencyId(account.getCurrencyID())
                && checkNumberAccount(account.getNumberAccount())) { // Не более 5 счетов в одни руки!
            setAccount(account, account.getNumberAccount(), account.getPersonID(), account.getCurrencyID(), account.getDescription());

            return accountDao.insert(account);
        }

        return null;
    }

    public List<Account> downloadListAccount() {
        return accountDao.findByAll();
    }

    public List<Account> findByPesonId(Person person) {
        return accountDao.findByPersonId(person.getId());
    }

    private boolean checkNumberAccount(int number) {

        List<Account> list = downloadListAccount();
        int size = list.size();


        for (int i = 0; i < size; i++) {

            if (number == list.get(i).getNumberAccount()) {
                return false; //Если нашел номер счёта, то он уже есть и не ... его снова добавлять!
            }
        }
        return true;
    }

    private boolean checkPersonId(int personId) {
        PersonDao personDao = null;

        if (personDao.findById(personId) != null) {
            return true;
        }
        return false;
    }

    private boolean checkCurrencyId(int currencyId) {
        CurrencyDao currencyDao = null;

        if (currencyDao.findById(currencyId) != null) { //Существует
            return true;
        }

        return false; //Не существует
    }

    public Account updateAccount(int id, int numberAccount, int personId, BigDecimal balance, int currencyId, String description) {

        Account account;
        account = accountDao.findById(id);

        if (account != null) {

            if (description != null && ! description.isEmpty() && (! checkNumberAccount(numberAccount)) && checkPersonId(personId) && (checkCurrencyId(currencyId))) {
                account.setDescription(description);
                account.setNumberAccount(numberAccount);
                account.setCurrencyID(currencyId);
                account.setPersonID(personId);
                account.setBalance(balance);
                account.setId(id);
               return accountDao.update(account);
            }



        }
        return null;
    }

    public boolean deleteAccount(int id) {

        return accountDao.delete(id);
    }
}
