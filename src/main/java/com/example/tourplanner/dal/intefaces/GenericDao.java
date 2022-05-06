package com.example.tourplanner.dal.intefaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {
    Optional<T> get(long id) throws SQLException;

    List<T> getAll() throws SQLException;

    int save(T t) throws SQLException;

    boolean update(long id, T t) throws SQLException;

    boolean delete(int id) throws SQLException;

}
