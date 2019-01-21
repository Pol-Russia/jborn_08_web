package ru.titov.s06.service;

import ru.titov.s06.dao.DaoFactory;
import ru.titov.s06.service.converters.*;

public class ServiceFactory {
    private static UserConverter userConverter;
    private static TransactionConverter transactionConverter;
    private static CurrencyConverter currencyConverter;
    private static CategorieConverter categorieConverter;
    private static AccountConverter accountConverter;
    private static DigestService digestService;
    private static SecurityService securityService;
    private static CurrencyService currencyService;
    private static CategorieService categorieService;
    private static TransactionService transactionService;
    private static  AccountService accountService;
    private static PersonService personService;

    public static UserConverter getUserConverter() {
        if (userConverter == null) {
            userConverter = new UserConverter();
        }

        return userConverter;
    }

    public static TransactionConverter getTransactionConverter() {
        if (transactionConverter == null) {
            transactionConverter = new TransactionConverter();
        }

        return transactionConverter;
    }

    public static CurrencyConverter getCurrencyConverter() {
        if (currencyConverter == null) {
            currencyConverter = new CurrencyConverter();
        }

        return currencyConverter;
    }

    public static CategorieConverter getCategorieConverter() {
        if (categorieConverter == null) {
            categorieConverter = new CategorieConverter();
        }

        return categorieConverter;
    }

    public static AccountConverter getAccountConverter() {
        if (accountConverter == null) {
            accountConverter = new AccountConverter();
        }

        return accountConverter;
    }

    public static DigestService getDigestService() {
        if (digestService == null) {
            digestService = new DigestService();
        }

        return digestService;
    }

    public static SecurityService getSecurityService() {
        if (securityService == null) {
            securityService = new SecurityService(DaoFactory.getPersonDao(), getDigestService(), getUserConverter());
        }

        return securityService;
    }

    public static CurrencyService getCurrencyService() {
        if (currencyService == null) {
            currencyService = new CurrencyService(DaoFactory.getCurrencyDao(), currencyConverter);
        }
        return currencyService;
    }

    public static CategorieService getCategorieService() {
        if (categorieService == null) {
            categorieService = new CategorieService(DaoFactory.getCategorieDao(), categorieConverter);
        }

        return categorieService;
    }

    public static TransactionService getTransactionService() {
        if (transactionService == null) {
            transactionService = new TransactionService(DaoFactory.getTransactionDao(), DaoFactory.getCategorieDao(), transactionConverter, DaoFactory.getAccountDao());
        }
        return transactionService;
    }

    public static AccountService getAccountService() {
        if (accountService == null) {
            accountService = new AccountService(DaoFactory.getAccountDao(), accountConverter, DaoFactory.getCurrencyDao(), DaoFactory.getPersonDao());
        }
        return accountService;
    }

    public static PersonService getPersonService() {
        if (personService == null) {
            personService = new PersonService(DaoFactory.getPersonDao(), userConverter);
        }
        return personService;
    }
}
