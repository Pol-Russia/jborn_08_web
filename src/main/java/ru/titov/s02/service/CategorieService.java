package ru.titov.s02.service;

import ru.titov.s02.dao.CategorieDao;
import ru.titov.s02.dao.domain.Categorie;
import ru.titov.s02.service.converters.CategorieConverter;
import ru.titov.s02.service.dto.CategorieDto;

import java.util.List;

public class CategorieService {

    private final CategorieDao categorieDao = new CategorieDao();

    public CategorieDto createNewCategorie(CategorieDto categorieDto) {

        if (checkDescription(categorieDto)) {

            Categorie categorie = new CategorieConverter().categorieDtoToCategorieConvert(categorieDto);

            categorie = categorieDao.insert(categorie);

            categorieDto = new CategorieConverter().categorieToCategorieDtoConvert(categorie);

            return categorieDto;
        }

        return null;
    }

    public CategorieDto updateCategorie(CategorieDto categorieDto) {

        Categorie categorie = new CategorieConverter().categorieDtoToCategorieConvert(categorieDto);

        categorie = categorieDao.findById(categorie.getId());

        if (categorie != null && checkDescription(categorieDto)) {

                categorie = categorieDao.update(categorie);

                return new CategorieConverter().categorieToCategorieDtoConvert(categorie);

        }

        return null;
    }

    public List<CategorieDto> downloadListCategorie() {

        return  new CategorieConverter().listCategorieToListCategorieDtoConvert(categorieDao.findByAll());
    }

    public boolean checkDescription(CategorieDto categorieDto) {

        Categorie categorie = new CategorieConverter().categorieDtoToCategorieConvert(categorieDto);
        categorie = categorieDao.findByDescription(categorie);

        if (categorie == null) {
            return true;
        }
        return false;
    }

    public boolean deleteCategorie(CategorieDto categorie) {

        if (categorie != null) {
            return categorieDao.delete(categorie.getId());
        }

        return false;
    }
}
