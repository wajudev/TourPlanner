package com.example.tourplanner.dal.intefaces;

import com.example.tourplanner.business.ConfigurationManager;
import com.example.tourplanner.dal.dao.TourDao;

import java.lang.reflect.InvocationTargetException;

public class DalFactory {
    private static String packageName;
    private static Database database;
    private static TourDao tourDao;

    public static void init(){
        packageName = ConfigurationManager.getConfigProperty("DalDbPackage");
    }

    public static Database getDatabase(){
        if (database == null){
            database = createDatabase();
        }
        return database;
    }

    private static Database createDatabase() {
        String connectionString = ConfigurationManager.getConfigProperty("SqlConnectionString");
        return createDatabase(connectionString);
    }

    private static Database createDatabase(String connectionString) {
        try {
            Class<Database> cls = (Class<Database>) Class.forName(packageName + ".DatabaseImpl");
            return cls.getConstructor(String.class).newInstance(connectionString);
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

    private static TourDao createTourDao() {
        try {
            Class<TourDao> cls = (Class<TourDao>) Class.forName(packageName + ".TourDaoImpl");
            return cls.getConstructor(String.class).newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
