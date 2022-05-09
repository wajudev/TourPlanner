package com.example.tourplanner.dal.postgres;

import com.example.tourplanner.dal.dao.TourDao;
import com.example.tourplanner.dal.intefaces.DalFactory;
import com.example.tourplanner.dal.intefaces.Database;
import com.example.tourplanner.models.Tour;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TourDaoImpl implements TourDao {

    private final Database database;

    public TourDaoImpl() {
        this.database = DalFactory.getDatabase();
    }

    @Override
    public Optional<Tour> get(long tourId) throws SQLException {
        ArrayList<Object> params = new ArrayList<>();
        params.add(tourId);

        String SQL_GET_TOUR = "SELECT * FROM public.\"tours\" WHERE \"tourId\" = CAST(? AS INTEGER);";
        List<Map<String, Object>> rows = database.select(SQL_GET_TOUR, params);
        List<Tour> tours = this.parseTours(rows);

        return tours.size() > 0 ?
                Optional.of(tours.get(0)) :
                Optional.empty();
    }

    @Override
    public List<Tour> getAll() throws SQLException {
        String SQL_GET_ALL_TOURS = "SELECT * FROM public.\"tours\";";
        List<Map<String, Object>> rows = database.select(SQL_GET_ALL_TOURS);
        return this.parseTours(rows);
    }

    @Override
    public int save(Tour tour) throws SQLException {
        String SQL_SAVE_TOUR = "INSERT INTO public.\"tours\" (\"tourName\", \"tourDescription\", \"from\", \"to\", \"transportType\", \"distance\", \"estimatedTime\") VALUES(?, ?, ?, ?, ?, CAST(? AS DECIMAL), ?);";
        return database.insert(SQL_SAVE_TOUR, tourList(tour));
    }

    @Override
    public boolean update(Tour tour) throws SQLException {
        ArrayList<Object> params = tourList(tour);
        params.add(tour.getTourId());

        String SQL_UPDATE_TOUR = "UPDATE public.\"tours\" SET \"tourName\" = ?, \"tourDescription\" = ?, \"from\" = ?, \"to\" = ?, \"transportType\" = ?, \"distance\" = CAST(? AS DECIMAL), \"estimatedTime\" = ? WHERE \"tourId\" = CAST(? AS INTEGER);";
        return database.update(SQL_UPDATE_TOUR, params);
    }

    @Override
    public boolean delete(int tourId) throws SQLException {
        ArrayList<Object> params = new ArrayList<>();
        params.add(tourId);

        String SQL_DELETE_TOUR = "DELETE FROM public.\"tours\" WHERE \"tourId\" = CAST(? AS INTEGER);";
        return database.delete(SQL_DELETE_TOUR, params);
    }

    /**
     * Helper method for the update and save method.
     *
     * @param tour  object of class Tour.
     * @return ArrayList of tours
     */
    private ArrayList<Object> tourList(Tour tour) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(tour.getTourName());
        params.add(tour.getTourDescription());
        params.add(tour.getFrom());
        params.add(tour.getTo());
        params.add(tour.getTransportType());
        params.add(tour.getDistance());
        params.add(tour.getEstimatedTime());
        return params;
    }

    /**
     * Tour parser.
     *
     * @param rows  list of row maps.
     * @return ArrayList of parsed params
     */

    private List<Tour> parseTours(List<Map<String, Object>> rows){
        List<Tour> list = new ArrayList<>();


        for (Map<String, Object> row : rows){
            list.add(new Tour(
                    (Integer) row.get("tourId"),
                    (String) row.get("tourName"),
                    (String) row.get("tourDescription"),
                    (String) row.get("from"),
                    (String) row.get("to"),
                    (String) row.get("transportType"),
                    row.get("distance") != null ? ((BigDecimal) row.get("distance")).floatValue() : null,
                    (String) row.get("estimatedTime")
            ));
        }
        return list;
    }


}
