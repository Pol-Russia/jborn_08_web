package ru.titov.s02.dao.domain;

import ru.titov.s02.dao.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static  ru.titov.s02.dao.DaoFactory.getConnection;

public class PersonDao implements Dao<Person, Integer> {

    private Person getPerson(ResultSet rs, Person person) throws SQLException {

        person.setId(rs.getInt(1));
        person.setMail(rs.getString(2));
        person.setPassword(rs.getString(3));
        person.setNick(rs.getString(4));
        person.setFullName(rs.getString(5));

        return person;
    }

    @Override
    public Person findById(Integer id) {
        Person person = new Person();

        try (Connection connection = getConnection();
            Statement statement = connection.createStatement()) {

             ResultSet rs = statement.executeQuery("Select * From person " +
                    "WHERE (person.id = " +id + ")");

             if (rs.next()) {
                 return getPerson(rs, person);
             }
        }
        catch (SQLException exept) {
             throw new RuntimeException(exept);
        }
        return null;
    }

    @Override
    public List<Person> findByAll() {
        List<Person> list = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery("Select * From person");

            while (rs.next()) {

                Person person = new Person();
                list.add(getPerson(rs, person));
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }

        return list;
    }

    public Person findByMail(String mail) {
        Person person = new Person();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("Select * From person " +
                     "WHERE person.e_mail = ?"))
        {

              preparedStatement.setString(1, mail);
              ResultSet rs = preparedStatement.executeQuery();

              if (rs.next()) {
                  return getPerson(rs, person);
              }

        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }
        return null;
    }

    public Person findByNick(String nick) {
        Person person = new Person();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("Select * From person " +
                     "WHERE (person.nick_name = ?")) {

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return getPerson(rs, person);
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }
        return null;
    }

    @Override
    public Person insert(Person person) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO person(" +
                     "e_mail, password, nick_name, full_name) VALUES(?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS);)
        {

            preparedStatement.setString(1, person.getMail());
            preparedStatement.setString(2, person.getPassword());
            preparedStatement.setString(3, person.getNick());
            preparedStatement.setString(4, person.getFullName());

            if (preparedStatement.executeUpdate() > 0) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        person.setId(resultSet.getInt("id"));
                    }
                }
                return person;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Person update(Person person) {
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE person SET " +
                "e_mail = ?, password = ?, nick_name = ?, full_name = ? " +
                "WHERE id = ?");)
        {
            preparedStatement.setInt(   5,   person.getId());
            preparedStatement.setString(1, person.getMail());
            preparedStatement.setString(2, person.getPassword());
            preparedStatement.setString(3, person.getNick());
            preparedStatement.setString(4, person.getFullName());

            if (preparedStatement.executeUpdate() > 0) {
                return person;
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

            if (statement.executeUpdate("DELETE * FROM person WHERE (person.id = " + id + ")") != 0) {
                return true;
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }
        return false;
    }
}
