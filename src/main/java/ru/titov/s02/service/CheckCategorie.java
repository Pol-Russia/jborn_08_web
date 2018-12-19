package ru.titov.s02.service;

import ru.titov.s02.dao.domain.Categorie;
import ru.titov.s02.dao.domain.CategorieDao;

import java.util.List;

public class CheckCategorie {

    private List<Categorie> downloadListCategorie() {
        CategorieDao categorieDao = new CategorieDao();
        return categorieDao.findByAll();
    }

    public boolean checkDescription(String description) {

        List<Categorie> list = downloadListCategorie();
        int size = list.size();

        if (size > 0) {
            for (int i = 0; i < size; i++) {
                if (description.equalsIgnoreCase(list.get(i).getDescription())) {
                    return true;
                }
            }
        }
        else {
            return true;
        }
        return false;
    }
}
