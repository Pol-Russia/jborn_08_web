package ru.titov.s02.view.console;

import ru.titov.s02.dao.CategorieDao;
import ru.titov.s02.dao.domain.Categorie;
import ru.titov.s02.dao.domain.Transaction;
import ru.titov.s02.service.TransactionService;
import ru.titov.s02.service.converters.TransactionConverter;
import ru.titov.s02.service.dto.AccountDto;
import ru.titov.s02.service.dto.CategorieDto;
import ru.titov.s02.service.dto.TransactionDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TransactionView {
    private Scanner scanner = new Scanner(System.in);

    public TransactionDto createNewTransaction(TransactionDto transactionDto) {

        if (new TransactionService().createNewTransaction(transactionDto) != null) {
            System.out.println("New account number =" + transactionDto.getId() + " successfully created!");
            return transactionDto;

        } else {
            System.out.println("Ошибка записи транзакции в БД!");
            return null;
        }
    }

    public TransactionDto createTransactionDto(AccountDto accountDto) {

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
                        List<CategorieDto> list = new CategorieView().findAllCategorieDto();
                        int count = 1;
                        for (CategorieDto categorie : list) {
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
                                categorieDto = categorieView.createNewCategorie(categorieDto);

                                if (categorieDto != null) {
                                    transactionDto.setCategorieID(categorieDto.getId());
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

    public List<TransactionDto> ShowAccount(AccountDto accountDto) {

        return new TransactionService().findTransactionByAccountId(accountDto);
    }

}

