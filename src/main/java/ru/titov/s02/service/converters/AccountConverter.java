package ru.titov.s02.service.converters;

import ru.titov.s02.dao.AccountDao;
import ru.titov.s02.dao.domain.Account;
import ru.titov.s02.view.dto.AccountDto;

import java.math.BigDecimal;

public class AccountConverter {

    public Account accountDtoToAccountConvert(AccountDto accountDto) {

        if (accountDto != null) {

            Account account = new Account();
            int id = accountDto.getId();

            if (id == -11) {
                account.setBalance(BigDecimal.valueOf(0));
            }
            else {
                account.setBalance(accountDto.getBalance());
            }

            account.setId(id);
            account.setNumberAccount(accountDto.getNumberAccount());
            account.setPersonID(accountDto.getPersonId());
            account.setCurrencyID(accountDto.getCurrencyId());
            account.setDescription(accountDto.getDescription());

            return account;
        }

        return null;
    }

    public AccountDto accountToAccountDtoConvert(Account account) {

        if (account != null) {
            AccountDto accountDto = new AccountDto();

            accountDto.setId(account.getId());
            accountDto.setNumberAccount(account.getNumberAccount());
            accountDto.setPersonId(account.getPersonID());
            accountDto.setBalance(account.getBalance());
            accountDto.setCurrencyId(account.getCurrencyID());
            accountDto.setDescription(account.getDescription());

            return accountDto;
        }
        return null;
    }

}
