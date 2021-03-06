package ru.titov.s05.view.console;

import ru.titov.s05.dao.DaoFactory;
import ru.titov.s05.service.CategorieService;
import ru.titov.s05.service.ServiceFactory;
import ru.titov.s05.service.dto.CategorieDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CategorieView {
    private final CategorieService categorieService = ServiceFactory.getCategorieService();



    public CategorieDto createNewCategorie(CategorieDto categorieDto) throws SQLException {

        if (categorieService.checkDescription(categorieDto, DaoFactory.getConnection())) {
            System.out.println("The same DESCRIPTION already exist....");
            return null;
        }
        else {
            categorieDto = categorieService.createNewCategorie(categorieDto, DaoFactory.getConnection());

            if (categorieDto != null) {

                System.out.println("New description " + categorieDto.getDescription() + " successfully created!");
                return categorieDto;
            }
        }

            System.out.println("Что-то пошло не так...");
            return null;
    }

    public CategorieDto createCategorieDto() {

        System.out.println("Please print your descripton and press <Enter>");
        Scanner scanner = new Scanner(System.in);
        String description = scanner.nextLine().trim();

        if (validateDescription(description)) {

            CategorieDto categorieDto = new CategorieDto();
            categorieDto.setDescription(description);
            categorieDto.setId(-11);

            return categorieDto;

        }

        return null;
    }

    public boolean validateDescription(String description) {
        if (description != null && description.length() < 25 && description.length() > 4) {
            System.out.println("Description must contain from 4 to 25 letters!");
            return false;
        }
        return true;
    }

    public List<CategorieDto> findAllCategorieDto() {

        List<CategorieDto> list = categorieService.getAllCategorie();

        if (list != null && list.size() >0 ) {
            return list;
        }
        return null;
    }


}
