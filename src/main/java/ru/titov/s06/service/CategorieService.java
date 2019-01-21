package ru.titov.s06.service;

import ru.titov.s06.dao.CategorieDao;
import ru.titov.s06.dao.domain.Categorie;
import ru.titov.s06.service.converters.CategorieConverter;
import ru.titov.s06.service.dto.CategorieDto;

import java.util.List;

public class CategorieService {
    private final CategorieDao categorieDao;
    private final CategorieConverter categorieConverter;

    public CategorieService(CategorieDao categorieDao, CategorieConverter categorieConverter) {
        this.categorieDao = categorieDao;
        this.categorieConverter = categorieConverter;
    }

    public CategorieDto createNewCategorie(CategorieDto categorieDto) {

        if (checkDescription(categorieDto)) {

            Categorie categorie = categorieConverter.categorieDtoToCategorieConvert(categorieDto);

            categorie = categorieDao.insert(categorie);

            categorieDto = categorieConverter.categorieToCategorieDtoConvert(categorie);

            return categorieDto;
        }

        return null;
    }

    public CategorieDto updateCategorie(CategorieDto categorieDto) {

        Categorie categorie = categorieConverter.categorieDtoToCategorieConvert(categorieDto);

        categorie = categorieDao.findById(categorie.getId());

        if (categorie != null && checkDescription(categorieDto)) {

                categorie = categorieDao.update(categorie);

                return categorieConverter.categorieToCategorieDtoConvert(categorie);

        }

        return null;
    }

    public List<CategorieDto> downloadListCategorie() {

        return  categorieConverter.listCategorieToListCategorieDtoConvert(categorieDao.findByAll());
    }

    public boolean checkDescription(CategorieDto categorieDto) {

        Categorie categorie = categorieConverter.categorieDtoToCategorieConvert(categorieDto);
        categorie = categorieDao.findByDescription(categorie);

        if (categorie != null) {
            return false;
        }

        return true;
    }

    public boolean deleteCategorie(CategorieDto categorieDto) {

        if (categorieDto != null) {
            return categorieDao.delete(categorieDto.getId());
        }

        return false;
    }
}
