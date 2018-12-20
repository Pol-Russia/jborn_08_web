package ru.titov.s02.service;

import ru.titov.s02.dao.domain.Account;
import ru.titov.s02.dao.domain.AccountDao;



public class NewAccount {

    public Account createNewAccount(int numberAccount, int personId, int currencyId, String description) {

        AccountDao accountDao = new AccountDao();
        Account account = new Account();

        if (accountDao.findByPersonId(personId).size() < 5) { // Не более 5 счетов в одни руки!
            setAccount(account, numberAccount, personId, currencyId, description);

            return accountDao.insert(account);
        }

       return null;
    }

    private void setAccount(Account account, int numberAccount, int personId, int currencyId, String description) {
        account.setNumberAccount(numberAccount);
        account.setPersonID(personId);
        account.setBalance(0);
        account.setCurrencyID(currencyId);
        account.setDescription(description);
    }
}
