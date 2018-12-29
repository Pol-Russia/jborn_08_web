package ru.titov.s02.dao;

import ru.titov.s02.dao.Dao;
import ru.titov.s02.dao.domain.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ru.titov.s02.dao.DaoFactory.getConnection;

public class CurrencyDao implements Dao<Currency, Integer> {

    private Currency getCurrency(ResultSet rs, Currency currency) throws SQLException {
        currency.setId(rs.getInt(1));
        currency.setNameOfCurrency(rs.getString(2));

        return currency;
    }

    @Override
    public Currency findById(Integer id) {
        Currency currency = new Currency();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("Select * From currency " +
                     "WHERE (currency.id = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();


            if (rs.next()) {
                return getCurrency(rs, currency);
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }

        return null;
    }

    @Override
    public List<Currency> findByAll() {
        List<Currency> list = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery("Select * From currency");

            while (rs.next()) {
                Currency currency = new Currency();
                list.add(getCurrency(rs, currency));
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }

        return list;
    }

    @Override
    public Currency insert(Currency currency) {

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO currency(" +
                     "name_of_currency) VALUES(?)", Statement.RETURN_GENERATED_KEYS)) {


            preparedStatement.setString(1, currency.getNameOfCurrency());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1); //вставленный ключ
                currency.setId(id);
            }

                return currency;



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public Currency update(Currency currency) {

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE currency SET " +
                     "name_of_currency = ? " +
                     "WHERE currency.id = ?");)
        {
            preparedStatement.setInt(2, currency.getId());
            preparedStatement.setString(1, currency.getNameOfCurrency());

            if (preparedStatement.executeUpdate() > 0) {
                return currency;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE * FROM currency WHERE (currency.id = ?")) {

            preparedStatement.setInt(1, id);

            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }
        return false;
    }

    public Currency findByNameCurrency(Currency currency) {

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("Select * From currency " +
                     "WHERE (currency.name_of_currency = ? )")) {

            preparedStatement.setString(1, currency.getNameOfCurrency());
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return getCurrency(rs, currency);
            }
        }
        catch (SQLException exept) {
            throw new RuntimeException(exept);
        }

         return null;

    }
}
