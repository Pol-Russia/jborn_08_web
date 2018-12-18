package ru.titov.s02.service;

import ru.titov.s02.dao.domain.AccountDao;
import ru.titov.s02.dao.domain.Categorie;
import ru.titov.s02.dao.domain.CategorieDao;

import static ru.titov.s02.dao.DaoFactory.checkOrderId;

public class NewCategorie {

    private Categorie createNewCategorie(String description) {

        if (description != null && ! description.isEmpty()) {
            CategorieDao categorieDao = new CategorieDao();
            Categorie categorie = new Categorie();

            String tableName = "categorie";
            int id = checkOrderId(tableName); //Получить актуальный id

            categorie.setId(id);
            categorie.setDescription(description);

            return categorieDao.insert(categorie);
        }

        return null;
    }
}
