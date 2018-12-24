package ru.titov.s02.view.console;

import ru.titov.s02.dao.domain.Categorie;
import ru.titov.s02.service.CategorieService;
import ru.titov.s02.service.converters.CategorieConverter;
import ru.titov.s02.view.dto.CategorieDto;

import java.util.List;
import java.util.Scanner;

public class CategorieView {
    private final CategorieService service = new CategorieService();


    public Categorie createNewCategorie(CategorieDto categorieDto) {

        Categorie categorie = new CategorieConverter().categorieDtoToCategorieConvert(categorieDto);
        CategorieService service = new CategorieService();

        if (! service.checkDescription(categorie)) {
            System.out.println("The same DESCRIPTION already exist....");
            return null;
        }
        else  if (service.createNewCategorie(categorie) != null)  {

                System.out.println("New description " + categorieDto.getDescription() + " successfully created!");
                return categorie;
        }
        else {
            System.out.println("Что-то пошло не так...");
            return null;
        }

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

    public List<Categorie> findAllCategorieDto() {

        List<Categorie> list = service.downloadListCategorie();

        if (list != null && list.size() >0 ) {
            return list;
        }
        return null;
    }


}
