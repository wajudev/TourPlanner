package com.example.tourplanner.dal.postgres;

import com.example.tourplanner.business.ConfigurationManager;
import com.example.tourplanner.dal.intefaces.Database;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class DatabaseImpl implements Database {

    private final String connection = ConfigurationManager.getConfigProperty("SqlConnectionString");
    final Logger logger = LogManager.getLogger(DatabaseImpl.class);

    private Connection createConnection() throws SQLException {
        String user = ConfigurationManager.getConfigProperty("DatabaseUser");
        String password = ConfigurationManager.getConfigProperty("DatabasePassword");
        try {
            return DriverManager.getConnection(connection, user, password);
        } catch (SQLException e) {
            logger.error("Establishing database connection failed.");
            e.printStackTrace();
        }
        throw new SQLException("Establishing database connection failed.");
    }

    @Override
    public List<Map<String, Object>> select(String query, List<Object> params) throws SQLException {
        logger.debug("Execute select statement: " + query + " with parameters: " + params);
        try (
                Connection connection = createConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            // Dynamically assign params
            assignParams(params, statement);
            // Return the ResultSet
            return this.resultAsArrayList(statement.executeQuery());
        } catch (SQLException e) {
            logger.error("Failed to execute select statement: " + query + " with parameters: " + params);
            e.printStackTrace();
        }
        throw new SQLException("Selecting data failed. " + query);
    }

    @Override
    public List<Map<String, Object>> select(String query) throws SQLException {
        logger.debug("Execute select statement: " + query);
        try (
                Connection connection = createConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            // Return the ResultSet
            return this.resultAsArrayList(statement.executeQuery());
        } catch (SQLException e) {
            logger.error("Failed to execute select statement: " + query);
            e.printStackTrace();
        }
        throw new SQLException("Selecting data failed. " + query);
    }

    @Override
    public int insert(String query, List<Object> params) throws SQLException {
        logger.debug("Execute insert statement: " + query + " with parameters: " + params);
        try (
                Connection connection = createConnection();
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            // Dynamically assign params
            assignParams(params, statement);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to execute insert statement: " + query + " with parameters: " + params);
            e.printStackTrace();
        }
        throw new SQLException("Inserting data not successful. " + query);
    }

    @Override
    public boolean update(String query, List<Object> params) throws SQLException {
        logger.debug("Execute update statement: " + query + " with parameters: " + params);
        try {
            return executeUpdateHelper(query, params);
        } catch (SQLException e) {
            logger.error("Failed to execute update statement: " + query + " with parameters: " + params);
            e.printStackTrace();
        }
        throw new SQLException("Update unsuccessful. " + query);
    }

    @Override
    public boolean delete(String query, List<Object> params) throws SQLException {
        logger.debug("Execute delete statement: " + query + " with parameters: " + params);
        try {
            return executeUpdateHelper(query, params);
        } catch (SQLException e) {
            logger.error("Failed to execute delete statement: " + query + " with parameters: " + params);
            e.printStackTrace();
        }
        throw new SQLException("Delete unsuccessful. " + query);
    }

    /**
     * Helper method for the update and delete method.
     *
     * @param query  update or delete.
     * @param params List of params to be updated or deleted.
     * @return boolean
     */
    private boolean executeUpdateHelper(String query, List<Object> params) throws SQLException {
        try (
                Connection connection = createConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            // Dynamically assign params
            assignParams(params, statement);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Method to dynamically assign params.
     *
     * @param params    List of parameters to be assigned.
     * @param statement PreparedStatement to be executed for database.
     * @throws SQLException throws a SQL exception.
     */
    private void assignParams(List<Object> params, PreparedStatement statement) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            if (params.get(i) != null) {
                statement.setString(i + 1, params.get(i).toString());
            } else {
                statement.setNull(i + 1, Types.NULL);
            }
        }
    }

    /**
     * Method to return result set(data) as an array list.
     *
     * @param resultSet points current row of data in database.
     * @return list of results
     * @throws SQLException throws a SQL exception.
     */
    private List<Map<String, Object>> resultAsArrayList(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columns = metaData.getColumnCount();
        List<Map<String, Object>> list = new ArrayList<>();
        while (resultSet.next()) {
            HashMap<String, Object> row = new HashMap<>(columns);
            for (int i = 1; i <= columns; i++) {
                row.put(metaData.getColumnName(i), resultSet.getObject(i));
            }
            list.add(row);
        }
        return list;
    }
}
