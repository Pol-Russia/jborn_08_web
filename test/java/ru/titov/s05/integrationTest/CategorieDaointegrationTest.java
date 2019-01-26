package ru.titov.s05.integrationTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.titov.s05.dao.CategorieDao;
import ru.titov.s05.dao.DaoFactory;
import ru.titov.s05.dao.domain.Categorie;
import ru.titov.s05.service.CategorieService;
import ru.titov.s05.service.converters.CategorieConverter;
import ru.titov.s05.service.dto.CategorieDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class CategorieDaointegrationTest {
    CategorieService subj;
    CategorieDao categorieDao = new CategorieDao();
    CategorieConverter categorieConverter = new CategorieConverter();

    @Before
    public void setUp()  {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:testDatabase");//H2 DB работает только в памяти!
        System.setProperty("jdbcUsername", "sa");
        System.setProperty("jdbcPassword", "");

        subj = new CategorieService(categorieDao, categorieConverter);


    }


    @Test
    public void categorieInsert_ok() throws SQLException {

        CategorieDto categorieDto = new CategorieDto();
        Connection connection = DaoFactory.getConnection();


        categorieDto.setId(-11);
        categorieDto.setDescription("Наличка");

        assertEquals(-11, categorieDto.getId()); //ключ ещё не сгенерирован!


        CategorieDto categorieDtoFromService = subj.createNewCategorie(categorieDto, connection);


        assertNotNull(categorieDto);
        assertNotEquals(-11, categorieDtoFromService.getId()); // Проверил генерацию ключа
        assertNotEquals(categorieDto.getId(), categorieDtoFromService.getId());
        assertEquals(categorieDto.getDescription(), categorieDtoFromService.getDescription());
        connection.close();
    }

    @Test
    public void categorieInsert_NameCurrencyWrong() throws SQLException {

        CategorieDto categorieDto = new CategorieDto();
        Connection connection = DaoFactory.getConnection();


        categorieDto.setId(-11);
        categorieDto.setDescription("");

        assertEquals(-11, categorieDto.getId());


        CategorieDto categorieDtoFromService = subj.createNewCategorie(categorieDto, connection);


        assertNotNull(categorieDto);


        assertNull(categorieDtoFromService);
        connection.close();
    }

    @Test
    public void categorieUpdate_ok() throws SQLException {
        CategorieDto categorieDto = new CategorieDto();
        Connection connection = DaoFactory.getConnection();


        categorieDto.setId(-11);
        categorieDto.setDescription("Грвн");

        assertEquals(-11, categorieDto.getId());


        CategorieDto categorieDtoFromService = subj.createNewCategorie(categorieDto, connection);


        assertNotNull(categorieDtoFromService);
        assertEquals(categorieDto.getDescription(), categorieDtoFromService.getDescription());


        categorieDto.setDescription("Рубль");
        categorieDto.setId(categorieDtoFromService.getId());
        categorieDtoFromService = subj.updateCategorie(categorieDto, connection);




        assertEquals(categorieDto.getDescription(), categorieDtoFromService.getDescription());
        assertEquals("Рубль", categorieDtoFromService.getDescription());
        connection.close();
    }

    @Test
    public void categorieUpdate_idWrong() throws SQLException {
        CategorieDto categorieDto = new CategorieDto();
        Connection connection = DaoFactory.getConnection();


        categorieDto.setId(-11);
        categorieDto.setDescription("USD");

        assertEquals(-11, categorieDto.getId());

        CategorieDto categorieDtoFromService = subj.createNewCategorie(categorieDto, connection);


        assertNotNull(categorieDtoFromService);
        assertEquals(categorieDto.getDescription(), categorieDtoFromService.getDescription());


        categorieDto.setDescription("Euro");
        categorieDto.setId(100);
        categorieDtoFromService = subj.updateCategorie(categorieDto, connection);



        assertNull(categorieDtoFromService);

        connection.close();
    }

    @Test
    public void categorieFindById_ok() throws SQLException {

        CategorieDto categorieDto = new CategorieDto();
        Connection connection = DaoFactory.getConnection();


        categorieDto.setId(-11);
        categorieDto.setDescription("USD");

        assertEquals(-11, categorieDto.getId());


        CategorieDto categorieDtoFromService = subj.createNewCategorie(categorieDto, connection);


        assertNotNull(categorieDtoFromService);
        assertEquals(categorieDto.getDescription(), categorieDtoFromService.getDescription());



        categorieDto = categorieConverter.categorieToCategorieDtoConvert(
                categorieDao.findById(categorieDtoFromService.getId(), connection));

        assertEquals(categorieDto.getId(), categorieDtoFromService.getId());
        assertEquals(categorieDto.getDescription(), categorieDtoFromService.getDescription());
    }

    @Test
    public void categorieFindById_idWrong() throws SQLException {

        CategorieDto categorieDto = new CategorieDto();
        Connection connection = DaoFactory.getConnection();


        categorieDto.setId(-11);
        categorieDto.setDescription("USD");

        assertEquals(-11, categorieDto.getId()); //ключ ещё не сгенерирован!


        CategorieDto categorieDtoFromService = subj.createNewCategorie(categorieDto, connection);


        assertNotNull(categorieDtoFromService);
        assertEquals(categorieDto.getDescription(), categorieDtoFromService.getDescription());



        categorieDto = categorieConverter.categorieToCategorieDtoConvert(
                categorieDao.findById(categorieDto.getId(), connection));

        assertNotEquals(categorieDto, categorieDtoFromService);
    }

    @Test
    public void categorieDelete_ok() throws SQLException {
        CategorieDto categorieDto = new CategorieDto();
        Connection connection = DaoFactory.getConnection();


        categorieDto.setId(-11);
        categorieDto.setDescription("USD");

        assertEquals(-11, categorieDto.getId()); //ключ ещё не сгенерирован!


        CategorieDto categorieDtoFromService = subj.createNewCategorie(categorieDto, connection);


        assertNotNull(categorieDtoFromService);
        assertEquals(categorieDto.getDescription(), categorieDtoFromService.getDescription());



        assertTrue(subj.deleteCategorie(categorieDtoFromService, connection));

        connection.close();
    }

    @Test
    public void categorieDelete_idWrong() throws SQLException {
        CategorieDto categorieDto = new CategorieDto();
        Connection connection = DaoFactory.getConnection();


        categorieDto.setId(-11);
        categorieDto.setDescription("USD");

        assertEquals(-11, categorieDto.getId()); //ключ ещё не сгенерирован!


        CategorieDto categorieDtoFromService = subj.createNewCategorie(categorieDto, connection);


        assertNotNull(categorieDtoFromService);
        assertEquals(categorieDto.getDescription(), categorieDtoFromService.getDescription());



        assertFalse(subj.deleteCategorie(categorieDto, connection));

        connection.close();
    }


    @Test
    public void categorieFindAll_ok() throws SQLException {

        CategorieDto categorieDto = new CategorieDto();
        Connection connection = DaoFactory.getConnection();


        categorieDto.setId(-11);
        categorieDto.setDescription("USD");

        assertEquals(-11, categorieDto.getId()); //ключ ещё не сгенерирован!


        subj.createNewCategorie(categorieDto, connection);
        categorieDto.setDescription("FRT");
        subj.createNewCategorie(categorieDto, connection);

        List<Categorie> list = categorieDao.findAll();

        assertNotNull(list);
        assertEquals(2, list.size());

        connection.close();
    }

    @After
    public void setDown() throws SQLException {

        Connection connection = DaoFactory.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM categorie;")) {

            preparedStatement.executeUpdate();
            connection.close();


        } catch (SQLException exept) {
            throw new RuntimeException(exept);
        }
    }
}
