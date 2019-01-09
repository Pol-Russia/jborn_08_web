package ru.titov.s02.dao;
import java.text.ParseException;
import java.util.List;

public interface Dao<Domain, Id> {

    Domain findById(Id id);

    List<Domain> findByAll();

    Domain insert(Domain domain);

    Domain update(Domain domain) throws ParseException;

    boolean delete(Id id);
}
