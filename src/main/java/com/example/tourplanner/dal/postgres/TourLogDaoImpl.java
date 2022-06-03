package com.example.tourplanner.dal.postgres;

import com.example.tourplanner.dal.dao.TourDao;
import com.example.tourplanner.dal.dao.TourLogDao;
import com.example.tourplanner.dal.intefaces.DalFactory;
import com.example.tourplanner.dal.intefaces.Database;
import com.example.tourplanner.models.TourLog;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TourLogDaoImpl implements TourLogDao {

    private final Database database;
    private final TourDao tourDao;

    public TourLogDaoImpl() {
        this.database = DalFactory.getDatabase();
        this.tourDao = DalFactory.getTourDao();
    }

    @Override
    public Optional<TourLog> get(long tourLogId) throws SQLException {
        ArrayList<Object> params = new ArrayList<>();
        params.add(tourLogId);

        String SQL_GET_TOUR_LOG = "SELECT * FROM public.\"tour_logs\" WHERE \"tourlog_id\" = CAST(? AS INTEGER);";
        List<Map<String, Object>> rows = database.select(SQL_GET_TOUR_LOG, params);
        List<TourLog> tourLogs = this.parseTourLogs(rows);

        return tourLogs.size() > 0 ?
                Optional.of(tourLogs.get(0)) :
                Optional.empty();
    }


    @Override
    public List<TourLog> getAll() throws SQLException {
        String SQL_GET_ALL_TOUR_LOGS = "SELECT * FROM public.\"tour_logs\";";
        List<Map<String, Object>> rows = database.select(SQL_GET_ALL_TOUR_LOGS);

        return this.parseTourLogs(rows);
    }

    @Override
    public int save(TourLog tourLog) throws SQLException {
        String SQL_SAVE_TOUR_LOG = "INSERT INTO public.\"tour_logs\" (\"date\", \"difficulty\", \"rating\", \"totalTime\", \"comment\", \"tour_id\") VALUES(?, ?, CAST (? AS DECIMAL(6,1)), CAST(? AS INTEGER), ?, CAST(? AS INTEGER));";


        return database.insert(SQL_SAVE_TOUR_LOG, tourLogList(tourLog));
    }

    @Override
    public boolean update(TourLog tourLog) throws SQLException {
        ArrayList<Object> params = tourLogList(tourLog);
        params.add(tourLog.getTourLogId());

        String SQL_UPDATE_TOUR_LOG = "UPDATE public.\"tour_logs\" SET \"date\" = ?, \"difficulty\" = ?, \"rating\" = CAST(? AS DECIMAL(6,1)), \"totalTime\" = CAST(? AS INTEGER), \"comment\" = ?, \"tour_id\" = CAST(? AS INTEGER) WHERE \"tourlog_id\" = CAST(? AS INTEGER);";
        return database.update(SQL_UPDATE_TOUR_LOG, params);
    }


    @Override
    public boolean delete(int tourLogId) throws SQLException {
        ArrayList<Object> params = new ArrayList<>();
        params.add(tourLogId);

        String SQL_DELETE_TOUR_LOG = "DELETE FROM public.\"tour_logs\" WHERE \"tourlog_id\" = CAST(? AS INTEGER);";
        return database.delete(SQL_DELETE_TOUR_LOG, params);
    }

    @Override
    public boolean deleteAll() {
        return false;//Not implemented
    }

    private ArrayList<Object> tourLogList(TourLog tourLog) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        ArrayList<Object> params = new ArrayList<>();
        params.add(tourLog.getDate().format(dateTimeFormatter));
        params.add(tourLog.getDifficulty());
        params.add(tourLog.getRating());
        params.add(tourLog.getTotalTime());
        params.add(tourLog.getComment());
        params.add(tourLog.getTour().getTourId());

        return params;
    }

    private List<TourLog> parseTourLogs(List<Map<String, Object>> rows) throws SQLException {
        List<TourLog> list = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Map<String, Object> row : rows){
            list.add(new TourLog(
                    (Integer) row.get("tourlog_id"),
                    LocalDate.parse((String) row.get("date"), dateTimeFormatter),
                    (String) row.get("difficulty"),
                    row.get("rating") != null ? ((BigDecimal) row.get("rating")).floatValue() : null,
                    (Integer) row.get("totalTime"),
                    (String) row.get("comment"),
                    tourDao.get((Integer) row.get("tour_id")).orElse(null)

            ));
        }
        return list;
    }
}
