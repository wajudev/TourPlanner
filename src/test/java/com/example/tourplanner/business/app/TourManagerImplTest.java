package com.example.tourplanner.business.app;

import com.example.tourplanner.dal.dao.TourDao;
import com.example.tourplanner.dal.dao.TourLogDao;
import com.example.tourplanner.dal.intefaces.DalFactory;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TourManagerImplTest {
    private static final Tour MOCK_TOUR = new Tour(1, "Name 1 ", "Description 1", "Vienna", "Linz", "Car", 0F, "Time 1");
    private static final Tour MOCK_TOUR_2 = new Tour(2, "Name 2 ", "Description 2", "Valencia", "Madrid", "Car", 0F, "Time 2");
    private static final TourLog MOCK_TOUR_LOG_1 = new TourLog(1, LocalDate.now(), "hard", 5f, 35, "Comment", MOCK_TOUR);
    private static final TourLog MOCK_TOUR_LOG_2 = new TourLog(2, LocalDate.now(), "hard", 5f, 35, "Comment", MOCK_TOUR);

    @Mock
    private TourDao tourDaoMock;
    @Mock
    private TourLogDao tourLogDaoMock;

    private TourManagerImpl tourManager;

    private MockedStatic<DalFactory> dalFactoryMockedStatic;

    @BeforeEach
    void setUp() {
        tourManager = new TourManagerImpl();
        dalFactoryMockedStatic = Mockito.mockStatic(DalFactory.class);
        dalFactoryMockedStatic.when(DalFactory::getTourDao).thenReturn(tourDaoMock);
        dalFactoryMockedStatic.when(DalFactory::getTourLogDao).thenReturn(tourLogDaoMock);
    }

    @AfterEach
    void tearDown() {
        dalFactoryMockedStatic.close();
    }

    @Test
    @DisplayName("Get tours")
    public void getTours() {
        try {
            // Arrange
            List<Tour> tours = new ArrayList<>() {
                {
                    add(MOCK_TOUR);
                    add(MOCK_TOUR_2);
                }
            };

            when(tourDaoMock.getAll()).thenReturn(tours);

            // Act
            List<Tour> result = tourManager.getTours();

            // Assert
            assertNotNull(result);
            assertEquals(tours, result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Get tours from an empty tour list")
    public void getTours_Empty() {
        try {
            // Arrange
            List<Tour> tours = new ArrayList<>();

            when(tourDaoMock.getAll()).thenReturn(tours);

            // Act
            List<Tour> result = tourManager.getTours();

            // Assert
            assertNotNull(result);
            assertEquals(tours, result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Get tour with tour id")
    public void getTour(){
        try {
            // Arrange
            when(tourDaoMock.get(anyLong())).thenReturn(Optional.of(MOCK_TOUR));

            // Act
            Optional<Tour> result = Optional.ofNullable(tourManager.getTour(1));

            // Assert
            assertFalse(result.isEmpty());
            assertEquals(Optional.of(MOCK_TOUR), result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Save a tour")
    public void saveTour() {
        try {
            // Arrange
            when(tourDaoMock.save(any(Tour.class)))
                    .thenReturn(1);

            // Act
            int result = tourManager.saveTour(MOCK_TOUR_2);

            // Assert
            assertEquals(1, result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Delete a tour")
    public void deleteTour() {
        try {
            // Arrange
            when(tourDaoMock.delete(anyInt()))
                    .thenReturn(true);

            // Act
            boolean result = tourManager.deleteTour(1);

            // Assert
            assertTrue(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Update a tour")
    public void updateTour() {
        try {
            // Arrange
            when(tourDaoMock.update(any(Tour.class)))
                    .thenReturn(true);

            // Act
            boolean result = tourManager.updateTour(MOCK_TOUR);

            // Assert
            assertTrue(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Get tour logs")
    public void getTourLogs() {
        try {
            // Arrange
            List<TourLog> tourLogList = new ArrayList<>() {
                {
                    add(MOCK_TOUR_LOG_1);
                    add(MOCK_TOUR_LOG_2);
                }
            };

            when(tourLogDaoMock.getAll())
                    .thenReturn(tourLogList);
            lenient().when(tourDaoMock.get(anyLong())).thenReturn(Optional.of(MOCK_TOUR));

            // Act
            List<TourLog> result = tourManager.getTourLogs();

            // Assert
            assertNotNull(result);
            assertEquals(tourLogList, result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Get all tour logs for tour")
    public void getTourLogsOfTour() {
        try {
            // Arrange
            List<TourLog> tourLogList = new ArrayList<>() {
                {
                    add(MOCK_TOUR_LOG_1);
                    add(MOCK_TOUR_LOG_2);
                }
            };
            lenient().when(tourDaoMock.get(anyLong())).thenReturn(Optional.of(MOCK_TOUR));
            when(tourDaoMock.getTourLogsOfTour(any())).thenReturn(tourLogList);

            // Act
            List<TourLog> result = tourManager.getTourLogsOfTour(MOCK_TOUR);

            // Assert
            assertFalse(result.isEmpty());
            assertEquals(tourLogList, result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Tour contains string, case insensitive")
    public void tourContains_caseInsensitive() {
        // Arrange
        Tour tour = new Tour(1, "Tour Name", "Description", "Vienna", "Linz", "Car", 10F, "123");
        String searchTerm = "Linz";

        when(tourManager.getTourLogsOfTour(tour)).thenReturn(
                new ArrayList<>() {{
                    add(new TourLog(1, LocalDate.now(), "hard", 5f, 35, "Comment", tour));
                    add(new TourLog(1, LocalDate.now(), "hard", 5f, 35, "Comment", tour));
                }}
        );

        // Act
        boolean result = tourManager.tourContains(tour, searchTerm, false);

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Tour log of tour contains string, case sensitive")
    public void tourContains_caseSensitive() {
        // Arrange
        Tour tour = new Tour(1, "Tour Name", "Description", "Vienna", "Linz", "Car", 10F, "123");
        String searchTerm = "comment";

        when(tourManager.getTourLogsOfTour(tour)).thenReturn(
                new ArrayList<>() {{
                    add(new TourLog(1, LocalDate.now(), "hard", 5f, 35, "Comment", tour));
                    add(new TourLog(1, LocalDate.now(), "hard", 5f, 35, "Comment", tour));
                }}
        );

        // Act
        boolean result = tourManager.tourContains(tour, searchTerm, true);

        // Assert
        assertFalse(result);
    }

}