package ru.titov.s06.view.console;

import ru.titov.s06.dao.CategorieDao;
import ru.titov.s06.dao.domain.Categorie;
import ru.titov.s06.dao.domain.Transaction;
import ru.titov.s06.service.CategorieService;
import ru.titov.s06.service.ServiceFactory;
import ru.titov.s06.service.TransactionService;
import ru.titov.s06.service.converters.TransactionConverter;
import ru.titov.s06.service.dto.AccountDto;
import ru.titov.s06.service.dto.CategorieDto;
import ru.titov.s06.service.dto.TransactionDto;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TransactionView {
    private Scanner scanner = new Scanner(System.in);
    private final TransactionService transactionService = ServiceFactory.getTransactionService();

    private String now() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        return dateFormat.format(new java.util.Date());
    }



    public TransactionDto createNewTransaction(TransactionDto transactionDto) {

        if (transactionService.createNewTransaction(transactionDto) != null) {
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
        transactionDto.setDate(now());
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

        return transactionService.findTransactionByAccountId(accountDto);
    }

}

