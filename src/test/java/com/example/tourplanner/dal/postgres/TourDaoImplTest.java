package com.example.tourplanner.dal.postgres;

import com.example.tourplanner.dal.intefaces.DalFactory;
import com.example.tourplanner.dal.intefaces.Database;
import com.example.tourplanner.models.Tour;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class TourDaoImplTest {

    @Mock
    private Database databaseMock;

    private TourDaoImpl tourDao;

    private MockedStatic<DalFactory> dalFactoryMockedStatic;

    private static final Tour MOCK_TOUR_ONE = new Tour(1, "Name 1", "Description 1", "Vienna", "Linz", "Car", 0F, "Time 1");

    @BeforeEach
    void setUp(){
        dalFactoryMockedStatic = Mockito.mockStatic(DalFactory.class);
        dalFactoryMockedStatic.when(DalFactory::getDatabase).thenReturn(databaseMock);

        tourDao = new TourDaoImpl();
    }

    @AfterEach
    void tearDown() {
        dalFactoryMockedStatic.close();
    }

    @Test
    @DisplayName("Get all tours")
    void getAll() {
        try {
            // Arrange
            Mockito.when(databaseMock.select(anyString()))
                    .thenReturn(new ArrayList<>() {{
                        add(new HashMap<>() {{
                            put("tourId", 1);
                            put("tourName", "Tour 1");
                            put("tourDescription", "Description 1");
                            put("from", "Vienna");
                            put("to", "Linz");
                            put("transportType", "Type 1");
                            put("distance", new BigDecimal("1.0"));
                            put("estimatedTime", "Time 1");
                        }});
                        add(new HashMap<>() {{
                            put("tourId", 2);
                            put("tourName", "Tour 2");
                            put("tourDescription", "Description 2");
                            put("from", "Linz");
                            put("to", "Innsbruck");
                            put("transportType", "Type 2");
                            put("distance", new BigDecimal("1.0"));
                            put("estimatedTime", "Time 2");
                        }});
                    }});

            // Act
            List<Tour> tours = tourDao.getAll();

            // Assert
            Mockito.verify(databaseMock).select(eq("SELECT * FROM public.\"tours\";"));
            assertNotNull(tours);
            assertEquals(2, tours.size());
            assertEquals("Tour 2", tours.get(1).getTourName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Get a tour")
    void get(){
        try {
            // Arrange
            Mockito.when(databaseMock.select(anyString(), anyList()))
                    .thenReturn(new ArrayList<>() {{
                        add(new HashMap<>() {{
                            put("tourId", 1);
                            put("tourName", "Tour 1");
                            put("tourDescription", "Description 1");
                            put("from", "Vienna");
                            put("to", "Graz");
                            put("transportType", "Type 1");
                            put("distance", new BigDecimal("1.0"));
                            put("estimatedTime", "Time 1");
                        }});
                    }});

            // Act
            Optional<Tour> tour = tourDao.get(1);

            // Assert
            Mockito.verify(databaseMock).select(eq("SELECT * FROM public.\"tours\" WHERE \"tourId\" = CAST(? AS INTEGER);"), anyList());
            assertFalse(tour.isEmpty());
            assertEquals("Tour 1", tour.orElseThrow().getTourName());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Save Tour")
     void save(){
        try{
            // Arrange
            Mockito.when(databaseMock.insert(anyString(), anyList()))
                    .thenReturn(1);

            // Act
            int result = tourDao.save(MOCK_TOUR_ONE);

            // Assert
            Mockito.verify(databaseMock).insert(eq("INSERT INTO public.\"tours\" (\"tourName\", \"tourDescription\", \"from\", \"to\", \"transportType\", \"distance\", \"estimatedTime\") VALUES(?, ?, ?, ?, ?, CAST(? AS DECIMAL), ?);"), anyList());
            assertEquals(1, result);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Update Tour")
     void update(){
        try{
            // Arrange
            Mockito.when(databaseMock.update(anyString(), anyList()))
                    .thenReturn(true);

            // Act
            boolean result = tourDao.update(MOCK_TOUR_ONE);

            // Assert
            Mockito.verify(databaseMock).update(eq("UPDATE public.\"tours\" SET \"tourName\" = ?, \"tourDescription\" = ?, \"from\" = ?, \"to\" = ?, \"transportType\" = ?, \"distance\" = CAST(? AS DECIMAL), \"estimatedTime\" = ? WHERE \"tourId\" = CAST(? AS INTEGER);"), anyList());
            assertTrue(result);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Delete Tour")
     void delete(){
        try {
            // Arrange
            Mockito.when(databaseMock.delete(anyString(), anyList()))
                    .thenReturn(true);

            // Act
            boolean result = tourDao.delete(1);

            // Assert
            Mockito.verify(databaseMock).delete(eq("DELETE FROM public.\"tours\" WHERE \"tourId\" = CAST(? AS INTEGER);"), anyList());
            assertTrue(result);
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

}