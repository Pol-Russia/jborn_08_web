package ru.titov.s02.view.console;

import ru.titov.s02.dao.domain.Account;
import ru.titov.s02.dao.domain.Categorie;
import ru.titov.s02.dao.domain.Transaction;
import ru.titov.s02.service.TransactionService;
import ru.titov.s02.service.converters.TransactionConverter;
import ru.titov.s02.view.dto.AccountDto;
import ru.titov.s02.view.dto.CategorieDto;
import ru.titov.s02.view.dto.TransactionDto;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransactionView {
    private Scanner scanner = new Scanner(System.in);

    public Transaction createNewTransaction(TransactionDto transactionDto) {

        Transaction transaction = new TransactionConverter().transactionDtoToTransactionConvert(transactionDto);

        if (new TransactionService().createNewTransaction(transaction) != null) {
            System.out.println("New account number =" + transaction.getId() + " successfully created!");
            return transaction;

        } else {
            System.out.println("Ошибка записи транзакции в БД!");
            return null;
        }
    }

    public TransactionDto createAccountDto(AccountDto accountDto) {

        System.out.println("Please print your sum and press <Enter>");
        BigDecimal sum = scanner.nextBigDecimal();

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAccountID(accountDto.getId());
        transactionDto.setDate(new Date());
        accountDto.setId(-11);

        while (true) {
            //Категория транзакции
                System.out.println("Please print your account description and press <Enter>");
             //Вывести список имеющихся категорий транзакци
            // Либо создать НОВУЮ
                        List<Categorie> list = new CategorieView().findAllCategorieDto();
                        int count = 1;
                        for (Categorie categorie : list) {
                            System.out.println("please press " + count + " for choose " + categorie.getDescription());
                        }
                        System.out.println("please press 0 for create new categorie description ");
                        System.out.println("for exit press q or Q");
                        String value = scanner.nextLine().trim();

                        if (value.equalsIgnoreCase("q")) {
                            System.out.println("Вы выбрали завершение операции!");
                            return null;
                        }
                        else {
                            int number = Integer.parseInt(value);
                            if (number == 0) {
                                //Создать Описание!

                                CategorieView categorieView = new CategorieView();
                                CategorieDto categorieDto = new CategorieView().createCategorieDto();
                                Categorie categorie = categorieView.createNewCategorie(categorieDto);

                                if (categorie != null) {
                                    transactionDto.setCategorieID(categorie.getId());
                                    return transactionDto;
                                }
                            }
                            else {
                                transactionDto.setCategorieID(list.get( number - 1).getId());
                                return transactionDto;
                            }
                        }
                }

       }
    }

