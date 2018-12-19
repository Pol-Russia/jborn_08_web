package ru.titov.s02.view.console;

import ru.titov.s02.dao.domain.Categorie;
import ru.titov.s02.service.CheckCategorie;
import ru.titov.s02.service.NewCategorie;

import java.util.Scanner;

public class CategorieDto {

    String description;


    public String getDescription() {
        return description;
    }


    public Categorie createCategorie() {

        System.out.println("Please print your descripton and press <Enter>");
        Scanner scanner = new Scanner(System.in);
        String description = scanner.nextLine().trim();

            if (validateDescription(description)) {

                if (new CheckCategorie().checkDescription(description)) {

                        NewCategorie newCategorie = new NewCategorie();
                        Categorie categorie =  newCategorie.createNewCategorie(description);

                        if (categorie != null) {
                            System.out.println("New account categorie " + categorie.getDescription() + " successfully created!");
                            return categorie;
                        }
                        else {
                            System.out.println("не удалось создать account categorie " + categorie.getDescription());
                            return null;
                        }

                    }
                    else {
                        System.out.println("This same description already exist.");
                        return null;
                    }

                }
                else {
                    System.out.println("Что то пошло не так!");
                    return null;
                }

            }


    public boolean validateDescription(String description) {
        if (description != null && description.length() > 25 || description.length() < 4) {
            System.out.println("Description must contain from 4 to 25 letters!");
            return false;
        }
        return true;
    }
}
