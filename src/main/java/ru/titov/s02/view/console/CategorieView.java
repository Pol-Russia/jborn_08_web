package ru.titov.s02.view.console;

import ru.titov.s02.dao.domain.Categorie;
import ru.titov.s02.service.CategorieService;
import ru.titov.s02.service.converters.CategorieConverter;
import ru.titov.s02.service.dto.CategorieDto;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CategorieView {
    private final CategorieService service = new CategorieService();


    public CategorieDto createNewCategorie(CategorieDto categorieDto) {

        CategorieService service = new CategorieService();

        if (service.checkDescription(categorieDto)) {

            categorieDto = service.createNewCategorie(categorieDto);

            if (categorieDto != null) {

                System.out.println("New description " + categorieDto.getDescription() + " successfully created!");
                return categorieDto;
            }
                    }
        else {

            System.out.println("The same DESCRIPTION already exist....");
            return null;
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
        else {
            System.out.println("Your description isnt validate!");
            System.out.println("Please press \"1\" if you wish Try again");
            System.out.println("for exit press \"q\" or \"Q\"");
            String str = scanner.nextLine().trim();

            if (str.equalsIgnoreCase("q")) {
                System.out.println("You closed create description!");
                return null;
            }
            else if (str.equalsIgnoreCase("1"))  {

                return createCategorieDto();
            }
        }


        return null;
    }

    public boolean validateDescription(String description) {
        if (description == null || description.length() > 25 || description.length() < 4) {
            System.out.println("Description must contain from 4 to 25 letters!");
            return false;
        }

        Pattern p = Pattern.compile("[a-zA-Zа-яА-Я-_]+");
        Matcher m = p.matcher(description);
        return m.matches();
    }

    public List<CategorieDto> findAllCategorieDto() {

        List<CategorieDto> list = service.downloadListCategorie();

        if (list != null && list.size() >0 ) {
            return list;
        }
        return null;
    }


}
