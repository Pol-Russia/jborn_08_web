package ru.titov.s02.dao.domain;

import ru.titov.s02.dao.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ru.titov.s02.dao.DaoFactory.getConnection;

public class CategorieDao implements Dao<Categorie, Integer> {

    @Override
    public Categorie findById(Integer id) {
        Categorie categorie = new Categorie();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery("Select * From categorie " +
                    "WHERE (categorie.id = " + id + ")");

            if (rs.next()) {
                return getCategorie(rs, categorie);
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }

        return null;
    }

    @Override
    public List<Categorie> findByAll() {
        List<Categorie> list = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery("Select * From categorie ");

            while (rs.next()) {
                Categorie categorie = new Categorie();
                list.add(getCategorie(rs, categorie));
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }

        return list;
    }

    private Categorie getCategorie(ResultSet rs, Categorie categorie) throws SQLException {
        categorie.setId(rs.getInt(1));
        categorie.setDescription(rs.getString(2));

        return categorie;
    }

    @Override
    public Categorie insert(Categorie categorie) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO categorie(" +
                     "description) VALUES( ? )", Statement.RETURN_GENERATED_KEYS);)
        {

            preparedStatement.setString(1, categorie.getDescription());

            if (preparedStatement.executeUpdate() > 0) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        categorie.setId(resultSet.getInt("id"));
                    }

                }
                return categorie;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Categorie update(Categorie categorie) {

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE categorie SET " +
                     "description = ? " +
                     "WHERE categorie.id = ?");)
        {
            preparedStatement.setInt(2, categorie.getId());
            preparedStatement.setString(1, categorie.getDescription());

            if (preparedStatement.executeUpdate() > 0) {

                return categorie;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = getConnection();
        Statement statement = connection.createStatement()) {

            if (statement.executeUpdate("DELETE * FROM categorie WHERE (categorie.id = " + id + ")") != 0) {
                return true;
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }

        return false;
    }
}
