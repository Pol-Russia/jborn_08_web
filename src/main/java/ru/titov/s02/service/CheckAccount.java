package ru.titov.s02.service;

import ru.titov.s02.dao.domain.Account;
import ru.titov.s02.dao.domain.AccountDao;

import java.util.List;

public class CheckAccount {
    private List<Account> downloadListAccount() {
        AccountDao accountDao = new AccountDao();
        return accountDao.findByAll();
    }

    public boolean checkNumberAccount(int number) {

        List<Account> list = downloadListAccount();
        int size = list.size();


        for (int i = 0; i < size; i++) {
            if (number == list.get(i).getNumberAccount()) {
                return false;

            }
        }
        return true;
    }
}
