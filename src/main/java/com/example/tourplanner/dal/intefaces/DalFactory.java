package com.example.tourplanner.dal.intefaces;

import com.example.tourplanner.business.ConfigurationManager;
import com.example.tourplanner.dal.dao.TourDao;
import com.example.tourplanner.dal.dao.TourLogDao;

import java.lang.reflect.InvocationTargetException;

public class DalFactory {
    private static String packageName;
    private static Database database;
    private static TourDao tourDao;
    private static TourLogDao tourLogDao;

    /**
     * Initialize configuration property
     */
    public static void init(){
        packageName = ConfigurationManager.getConfigProperty("DalDbPackage");
    }

    public static Database getDatabase(){
        if (database == null){
            database = createDatabase();
        }
        return database;
    }

    /**
     * Helper method to create new instance of Database
     *
     * @return class instance with connection string
     */
    private static Database createDatabase() {
        String connectionString = ConfigurationManager.getConfigProperty("SqlConnectionString");
        return createDatabase(connectionString);
    }

    /**
     * Creates new instance of Database
     *
     * @param connectionString to load the driver and indicate settings required to establish a connection to the data source.
     * @return class instance with connection string
     */
    private static Database createDatabase(String connectionString) {
        try {
            Class<Database> cls = (Class<Database>) Class.forName(packageName + ".DatabaseImpl");
            return cls.getConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static TourDao getTourDao(){
        if (tourDao == null){
            tourDao = createTourDao();
        }
        return tourDao;
    }

    /**
     * Creates new instance of TourDao
     *
     */
    private static TourDao createTourDao() {
        try {
            Class<TourDao> cls = (Class<TourDao>) Class.forName(packageName + ".TourDaoImpl");
            return cls.getConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TourLogDao getTourLogDao() {
        if (tourLogDao == null){
            tourLogDao = createTourLogDao();
        }
        return tourLogDao;
    }

    private static TourLogDao createTourLogDao() {
        try {
            Class<TourLogDao> cls = (Class<TourLogDao>)  Class.forName(packageName + ".TourLogDaoImpl");
            return cls.getConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
        return null;
    }
}
