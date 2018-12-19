package ru.titov.s02.service;


import ru.titov.s02.dao.domain.Categorie;
import ru.titov.s02.dao.domain.CategorieDao;



public class NewCategorie {

    public Categorie createNewCategorie(String description) {

            if (description != null && ! description.isEmpty()) {
                CategorieDao categorieDao = new CategorieDao();
                Categorie categorie = new Categorie();
                categorie.setDescription(description);

                return categorieDao.insert(categorie);
            }
            return null;
        }



}
