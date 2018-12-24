package ru.titov.s02.service;

import ru.titov.s02.dao.CategorieDao;
import ru.titov.s02.dao.domain.Categorie;

import java.util.List;

public class CategorieService {

    private final CategorieDao categorieDao = new CategorieDao();

    public Categorie createNewCategorie(Categorie categorie) {

        if (categorie != null && ! categorie.getDescription().isEmpty() && checkDescription(categorie)) {

            return categorieDao.insert(categorie);
        }

        return null;
    }

    public Categorie updateCategorie(Categorie categorie) {

        categorie = categorieDao.findById(categorie.getId());

        if (categorie != null && checkDescription(categorie)) {

            if (! categorie.getDescription().isEmpty()) {
                categorie.setDescription(categorie.getDescription());
                return categorieDao.update(categorie);
            }
        }

        return null;
    }

    public List<Categorie> downloadListCategorie() {

        return categorieDao.findByAll();
    }

    public boolean checkDescription(Categorie categorie) {

        List<Categorie> list = downloadListCategorie();
        int size = list.size();


        for (int i = 0; i < size; i++) {

            if (categorie.getDescription().equalsIgnoreCase(list.get(i).getDescription())) {

                return false;
            }
        }
        return true;
    }

    public boolean deleteCategorie(Categorie categorie) {

        if (categorie != null) {
            return categorieDao.delete(categorie.getId());
        }

        return false;
    }
}
