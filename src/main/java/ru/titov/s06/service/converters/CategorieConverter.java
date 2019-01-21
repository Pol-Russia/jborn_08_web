package ru.titov.s04.service.converters;

import ru.titov.s04.dao.domain.Categorie;
import ru.titov.s04.service.dto.CategorieDto;

import java.util.ArrayList;
import java.util.List;

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

    public List<CategorieDto> listCategorieToListCategorieDtoConvert(List<Categorie> categorie) {

        if (categorie == null) {
            return null;
        }

        List<CategorieDto> listCategorieDto = new ArrayList<>();
        CategorieConverter converter = new CategorieConverter();

        for (Categorie cat : categorie) {

            listCategorieDto.add(converter.categorieToCategorieDtoConvert(cat));
        }

        return listCategorieDto;
    }



}
