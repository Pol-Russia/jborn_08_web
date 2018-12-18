package ru.titov.s02.service;

import ru.titov.s02.dao.domain.Categorie;
import ru.titov.s02.dao.domain.CategorieDao;

public class UpdateCategorie {

    private Categorie update(int id, String  description) {

        Categorie categorie;
        CategorieDao categorieDao = new CategorieDao();
        categorie = categorieDao.findById(id);

        if (categorie != null) {

            if (description != null && ! description.isEmpty()) {
                categorie.setDescription(description);
            }


            categorieDao.update(categorie);

        }
        return categorie;
    }
}
