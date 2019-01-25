package ru.titov.s05.service;

import ru.titov.s05.dao.CategorieDao;
import ru.titov.s05.dao.domain.Categorie;
import ru.titov.s05.service.converters.CategorieConverter;
import ru.titov.s05.service.dto.CategorieDto;

import java.sql.Connection;
import java.util.List;

public class CategorieService {
    private final CategorieDao categorieDao;
    private final CategorieConverter categorieConverter;

    public CategorieService(CategorieDao categorieDao, CategorieConverter categorieConverter) {
        this.categorieDao = categorieDao;
        this.categorieConverter = categorieConverter;
    }

    public CategorieDto createNewCategorie(CategorieDto categorieDto, Connection connection) {

        if (checkDescription(categorieDto, connection)) {

            Categorie categorie = categorieConverter.categorieDtoToCategorieConvert(categorieDto);

            categorie = categorieDao.insert(categorie, connection);

            categorieDto = categorieConverter.categorieToCategorieDtoConvert(categorie);

            return categorieDto;
        }

        return null;
    }

    public CategorieDto updateCategorie(CategorieDto categorieDto, Connection connection) {

        Categorie categorie = categorieConverter.categorieDtoToCategorieConvert(categorieDto);

        categorie = categorieDao.findById(categorie.getId(), connection);

        if (categorie != null && checkDescription(categorieDto, connection)) {

                categorie = categorieDao.update(categorie, connection);

                return categorieConverter.categorieToCategorieDtoConvert(categorie);

        }

        return null;
    }

    public List<CategorieDto> downloadListCategorie() {

        return  categorieConverter.listCategorieToListCategorieDtoConvert(categorieDao.findByAll());
    }

    public boolean checkDescription(CategorieDto categorieDto, Connection connection) {

        Categorie categorie = categorieConverter.categorieDtoToCategorieConvert(categorieDto);
        categorie = categorieDao.findByDescription(categorie, connection);

        if (categorie != null) {
            return false;
        }

        return true;
    }

    public boolean deleteCategorie(CategorieDto categorieDto, Connection connection) {

        if (categorieDto != null) {
            return categorieDao.delete(categorieDto.getId(), connection);
        }

        return false;
    }
}
