package ru.titov.s02.service.converters;

import ru.titov.s02.dao.domain.Categorie;
import ru.titov.s02.view.dto.CategorieDto;

public class CategorieConverter {

    public Categorie categorieDtoToCategorieConvert(CategorieDto categorieDto) {

        if (categorieDto != null) {

            Categorie categorie = new Categorie();
            categorie.setId(categorieDto.getId());
            categorie.setDescription(categorieDto.getDescription());

            return categorie;
        }

        return null;
    }


    public CategorieDto categorieToCategorieDtoConvert(Categorie categorie) {

        if (categorie != null) {

            CategorieDto categorieDto = new CategorieDto();
            categorieDto.setId(categorie.getId());
            categorieDto.setDescription(categorie.getDescription());

            return categorieDto;
        }

        return null;
    }


}
