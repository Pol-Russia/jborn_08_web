package ru.titov.s05.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.titov.s05.dao.CategorieDao;
import ru.titov.s05.dao.domain.Categorie;
import ru.titov.s05.service.converters.CategorieConverter;
import ru.titov.s05.service.dto.CategorieDto;

import java.sql.Connection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategorieServiceTest {

    @InjectMocks CategorieService subj;
    @Mock CategorieDao categorieDao;
    @Mock CategorieConverter categorieConverter;
    @Mock Connection connectionMock;

    @Test
    public void createNewCategorie_categorieIsNull() {

        CategorieDto categorieDto = null;

        CategorieDto categorieDtoFromService = subj.createNewCategorie(categorieDto, connectionMock);

        assertNull(categorieDtoFromService);

}

    @Test
    public void createNewCategorie_ok() {

        Categorie categorie = new Categorie();
        CategorieDto categorieDto = new CategorieDto();
        categorieDto.setDescription("dddddd");

        when(categorieConverter.categorieDtoToCategorieConvert(categorieDto)).thenReturn(categorie);
        when(categorieDao.findByDescription(categorie, connectionMock)).thenReturn(null);
        when(categorieDao.insert(categorie, connectionMock)).thenReturn(categorie);
        when(categorieConverter.categorieToCategorieDtoConvert(categorie)).thenReturn(categorieDto);

        CategorieDto categorieDtoFromService = subj.createNewCategorie(categorieDto, connectionMock);
        assertEquals(categorieDto, categorieDtoFromService);
    }

    @Test
    public void updateCategorie_ok() {
        Categorie categorie = new Categorie();

        CategorieDto categorieDto = new CategorieDto();
        categorieDto.setDescription("ddddddddddddd");
        categorieDto.setId(10);
        categorie.setId(10);


        when(categorieConverter.categorieDtoToCategorieConvert(categorieDto)).thenReturn(categorie);
        when(categorieConverter.categorieToCategorieDtoConvert(categorie)).thenReturn(categorieDto);
        when(categorieDao.findById(10, connectionMock)).thenReturn(categorie);
        when(categorieDao.update(categorie, connectionMock)).thenReturn(categorie);


        CategorieDto categorieDtoFromService = subj.updateCategorie(categorieDto, connectionMock);
        assertEquals(categorieDto, categorieDtoFromService);
    }

    @Test
    public void updateCategorie_idIsWrong() {
        Categorie categorie = new Categorie();

        CategorieDto categorieDto = new CategorieDto();
        categorie.setId(10);

        when(categorieConverter.categorieDtoToCategorieConvert(categorieDto)).thenReturn(categorie);
        when(categorieConverter.categorieToCategorieDtoConvert(categorie)).thenReturn(categorieDto);
        when(categorieDao.findById(11, connectionMock)).thenReturn(categorie);



        CategorieDto categorieDtoFromService = subj.updateCategorie(categorieDto, connectionMock);
        assertNull(categorieDtoFromService);
    }

    @Test
    public void checkDescription_ok() {
        Categorie categorie = new Categorie();
        CategorieDto categorieDto = new CategorieDto();
        categorieDto.setDescription("bla-bk");

        when(categorieConverter.categorieDtoToCategorieConvert(categorieDto)).thenReturn(categorie);
        when(categorieDao.findByDescription(categorie, connectionMock)).thenReturn(null);
        Boolean checkDescriptionTrue = subj.checkDescription(categorieDto, connectionMock);

        assertTrue(checkDescriptionTrue);
    }

    @Test
    public void deleteCategorie_ok() {

        CategorieDto categorieDto = new CategorieDto();
        categorieDto.setId(100);

        when(categorieDao.delete(100, connectionMock)).thenReturn(true);
        Boolean deleteCategorieTrue = subj.deleteCategorie(categorieDto, connectionMock);

                 assertTrue(deleteCategorieTrue);
            }

    @Test
    public void deleteCategorie_idIsWrong() {

        CategorieDto categorieDto = new CategorieDto();
        categorieDto.setId(100);

        when(categorieDao.delete(22, connectionMock)).thenReturn(true);
        Boolean deleteCategorie = subj.deleteCategorie(categorieDto, connectionMock);

        assertFalse(deleteCategorie);
    }
}
