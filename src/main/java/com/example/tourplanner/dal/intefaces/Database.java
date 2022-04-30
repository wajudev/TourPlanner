package com.example.tourplanner.dal.intefaces;

import java.util.List;
import java.util.Map;
import java.sql.SQLException;

public interface Database {
    List<Map<String, Object>> select(String query, List<Object> params) throws SQLException;

    List<Map<String, Object>> select(String query) throws SQLException;

    int insert(String query, List<Object> params) throws SQLException;

    boolean update(String query, List<Object> params) throws SQLException;

    boolean delete(String query, List<Object> params) throws SQLException;
}
