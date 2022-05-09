package com.example.tourplanner.dal.postgres;

import com.example.tourplanner.dal.dao.TourLogDao;
import com.example.tourplanner.models.TourLog;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TourLogDaoImpl implements TourLogDao {
    @Override
    public Optional<TourLog> get(long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<TourLog> getAll() throws SQLException {
        return null;
    }

    @Override
    public int save(TourLog tourLog) throws SQLException {
        return 0;
    }

    @Override
    public boolean update(TourLog tourLog) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }
}
