package ru.titov.s05.dao;
import java.sql.Connection;
import java.util.List;

public interface Dao<Domain, Id> {

    Domain findById(Id id, Connection connection);

    List<Domain> findByAll();

    Domain insert(Domain domain, Connection connection);

    Domain update(Domain domain, Connection connection);

    boolean delete(Id id, Connection connection);
}
