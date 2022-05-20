package com.example.tourplanner.dal.postgres;

import com.example.tourplanner.dal.dao.TourDao;
import com.example.tourplanner.dal.intefaces.DalFactory;
import com.example.tourplanner.dal.intefaces.Database;
import com.example.tourplanner.models.Tour;
import com.example.tourplanner.models.TourLog;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TourLogDaoImplTest {

    private static final Tour MOCK_TOUR = new Tour(1, "Name 1", "Description 1", "From 1", "To 1", "Type 1", 0F, "Time 1");
    private static final Tour MOCK_TOUR_2 = new Tour(2, "Name 2", "Description 2", "From 2", "To 2", "Type 2", 0F, "Time 2");
    private static final TourLog MOCK_TOUR_LOG = new TourLog(1, LocalDate.now(), "hard", "5", "Time", "Comment", MOCK_TOUR);

    @Mock
    private Database databaseMock;

    @Mock
    private TourDao tourDaoMock;

    private TourLogDaoImpl tourLogDao;

    private MockedStatic<DalFactory> dalFactoryMockedStatic;

    @BeforeEach
    void setUp() {
        dalFactoryMockedStatic = Mockito.mockStatic(DalFactory.class);
        dalFactoryMockedStatic.when(DalFactory::getDatabase).thenReturn(databaseMock);
        dalFactoryMockedStatic.when(DalFactory::getTourDao).thenReturn(tourDaoMock);

        tourLogDao = new TourLogDaoImpl();
    }

    @AfterEach
    void tearDown() {
        dalFactoryMockedStatic.close();
    }

    @Test
    @DisplayName("Get Tour Log")
    void get() {
        // Arrange
        try {
            when(tourDaoMock.get(anyLong()))
                    .thenReturn(Optional.of(MOCK_TOUR));
            when(databaseMock.select(anyString(), anyList()))
                    .thenReturn(new ArrayList<>(){{
                        add(new HashMap<>(){{
                            put("tourlog_id", 1);
                            put("date", "01/01/2022");
                            put("difficulty", "difficult");
                            put("rating", "10");
                            put("totalTime", "123");
                            put("comment", "Comment");
                            put("tour_id", 1);
                        }});
                    }});

            // Act
            Optional<TourLog> tourLog = tourLogDao.get(1);

            // Assert
            Mockito.verify(databaseMock).select(eq("SELECT * FROM public.\"tour_logs\" WHERE \"tourlog_id\" = CAST(? AS INTEGER);"), anyList());
            assertFalse(tourLog.isEmpty());
            assertNotNull(tourLog.orElseThrow().getDate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Get all Tour Logs")
    void getAll() {
        // Arrange
        try {
            when(tourDaoMock.get(anyLong()))
                    .thenReturn(Optional.of(MOCK_TOUR));
            when(tourDaoMock.get(anyLong()))
                    .thenReturn(Optional.of(MOCK_TOUR_2));
            when(databaseMock.select(anyString()))
                    .thenReturn(new ArrayList<>(){{
                        add(new HashMap<>(){{
                            put("tourlog_id", 1);
                            put("date", "01/01/2022");
                            put("difficulty", "difficult");
                            put("rating", "10");
                            put("totalTime", "123");
                            put("comment", "Comment");
                            put("tour_id", 1);
                        }});
                        add(new HashMap<>(){{
                            put("tourlog_id", 2);
                            put("date", "02/02/2022");
                            put("difficulty", "easy");
                            put("rating", "9");
                            put("totalTime", "113");
                            put("comment", "Comment");
                            put("tour_id", 2);
                        }});
                    }});

            // Act
            List<TourLog> tourLogs = tourLogDao.getAll();

            // Assert
            Mockito.verify(databaseMock).select(eq("SELECT * FROM public.\"tour_logs\";"));
            assertNotNull(tourLogs);
            assertEquals(2, tourLogs.size());
            assertEquals("10", tourLogs.get(0).getRating());
            assertEquals("9", tourLogs.get(1).getRating());
            assertEquals(2, tourLogs.get(1).getTour().getTourId());
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Test
    @DisplayName("Save Tour Log")
    void save() {
        try {
            // Arrange
            when(databaseMock.insert(anyString(), anyList()))
                    .thenReturn(1);

            // Act
            int result = tourLogDao.save(MOCK_TOUR_LOG);

            // Assert
            Mockito.verify(databaseMock).insert(eq("INSERT INTO public.\"tour_logs\" (\"date\", \"difficulty\", \"rating\", \"totalTime\", \"comment\", \"tour_id\") VALUES(?, ?, ?, ?, ?, CAST(? AS INTEGER));"), anyList());
            assertEquals(1, result);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Update Tour Log")
    void update() {
        try {
            // Arrange
            when(databaseMock.update(anyString(), anyList()))
                    .thenReturn(true);

            // Act
            boolean result = tourLogDao.update(MOCK_TOUR_LOG);

            // Assert
            Mockito.verify(databaseMock).update(eq("UPDATE public.\"tour_logs\" SET \"date\" = ?, \"difficulty\" = ?, \"rating\" = ?, \"totalTime\" = ?, \"comment\" = ?, \"tour_id\" = CAST(? AS INTEGER) WHERE \"tourlog_id\" = CAST(? AS INTEGER);"), anyList());
            assertTrue(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Delete Tour Log")
    void delete() {
        try {
            // Arrange
            when(databaseMock.delete(anyString(), anyList()))
                    .thenReturn(true);

            // Act
            boolean result = tourLogDao.delete(1);

            // Assert
            Mockito.verify(databaseMock).delete(eq("DELETE FROM public.\"tour_logs\" WHERE \"tourlog_id\" = CAST(? AS INTEGER);"), anyList());
            assertTrue(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}