package ru.titov.s02.service;

import ru.titov.s02.dao.domain.Account;
import ru.titov.s02.dao.domain.AccountDao;

public class UpdateAccount {

    private Account update(int id, int numberAccount, int personId, int currencyId, String description) {

        Account account;
        AccountDao accountDao = new AccountDao();
        account = accountDao.findById(id);

        if (account != null) {

            if (description != null && ! description.isEmpty()) {
                account.setDescription(description);
            }

            if (numberAccount > 0) {
                account.setNumberAccount(numberAccount);
            }

                accountDao.update(account);

        }
        return account;
    }
}
