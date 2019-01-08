package ru.titov.s02.view.console;

import ru.titov.s02.dao.CategorieDao;
import ru.titov.s02.dao.domain.Categorie;
import ru.titov.s02.dao.domain.Transaction;
import ru.titov.s02.service.TransactionService;
import ru.titov.s02.service.converters.TransactionConverter;
import ru.titov.s02.service.dto.AccountDto;
import ru.titov.s02.service.dto.CategorieDto;
import ru.titov.s02.service.dto.TransactionDto;
import ru.titov.s02.service.dto.UserDto;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TransactionView {
    private Scanner scanner = new Scanner(System.in);

    private String now() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        return dateFormat.format(new java.util.Date());
    }

    public TransactionDto createNewTransaction(TransactionDto transactionDto) {

        if (new TransactionService().createNewTransaction(transactionDto) != null) {
            System.out.println("New transaction id =" + transactionDto.getId() + " successfully created!");
            System.out.println();
            System.out.println();
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
        transactionDto.setDate(now());
        transactionDto.setSum(sum);

        while (true) {
            //Категория транзакции
                System.out.println("Please print your account description and press <Enter>");
             //Вывести список имеющихся категорий транзакци
            // Либо создать НОВУЮ
                        List<CategorieDto> list = new CategorieView().findAllCategorieDto();
                        int count = 0;
                        for (CategorieDto categorie : list) {
                            count++;
                            System.out.println("please press " + count + " for choose --> " + categorie.getDescription());
                        }
                        System.out.println("please press 0 for create new categorie description ");
                        System.out.println("for exit press q or Q");
                        Scanner scanner = new Scanner(System.in);
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
                                }
                                return transactionDto;
                            }
                            else {
                                transactionDto.setCategorieID(list.get( number - 1).getId());
                                return transactionDto;
                            }
                        }
                }

       }

    public List<TransactionDto> ShowAllTransaction(UserDto userDto) {

        return new TransactionService().findTransactionByAccountId(userDto);
    }

}

