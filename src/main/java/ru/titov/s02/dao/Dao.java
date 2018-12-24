package ru.titov.s02.dao;
import java.util.List;

public interface Dao<Domain, Id> {

    Domain findById(Id id);

    List<Domain> findByAll();

    Domain insert(Domain domain);

    Domain update(Domain domain);

    boolean delete(Id id);
}
