package com.example.tourplanner.business.report;

import com.example.tourplanner.business.app.TourManagerImpl;
import com.example.tourplanner.dal.dao.TourDao;
import com.example.tourplanner.dal.intefaces.DalFactory;
import com.example.tourplanner.dal.intefaces.Database;
import com.example.tourplanner.dal.postgres.TourLogDaoImpl;
import com.example.tourplanner.models.Tour;
import com.example.tourplanner.models.TourLog;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportTest {

    private static final Tour MOCK_TOUR = new Tour(1, "Name 1", "Description 1", "From 1", "To 1", "Type 1", 0F, "Time 1");
    private static final Tour MOCK_TOUR_2 = new Tour(2, "Name 2", "Description 2", "From 2", "To 2", "Type 2", 0F, "Time 2");
    private static final TourLog MOCK_TOUR_LOG_1 = new TourLog(1, LocalDate.now(), "hard", 5f, 35, "Comment", MOCK_TOUR);
    private static final TourLog MOCK_TOUR_LOG_2 = new TourLog(2, LocalDate.now(), "hard", 5f, 35, "Comment", MOCK_TOUR);


    @Mock
    private Database databaseMock;

    @Mock
    private TourDao tourDaoMock;

    private TourLogDaoImpl tourLogDaoMock;

    private TourManagerImpl tourManager;

    private MockedStatic<DalFactory> dalFactoryMockedStatic;

    @BeforeEach
    void setUp() {
        tourManager = new TourManagerImpl();
        dalFactoryMockedStatic = Mockito.mockStatic(DalFactory.class);
        dalFactoryMockedStatic.when(DalFactory::getDatabase).thenReturn(databaseMock);
        dalFactoryMockedStatic.when(DalFactory::getTourDao).thenReturn(tourDaoMock);

        tourLogDaoMock = new TourLogDaoImpl();
    }

    @AfterEach
    void tearDown() {
        dalFactoryMockedStatic.close();
    }

    @Test
    @DisplayName("Creates Tour Report for single tour and checks if tour logs numerical values are correct")
    void tourReport() throws IOException, SQLException {
        List<TourLog> tourLogs = new ArrayList<>() {
            {
                add(MOCK_TOUR_LOG_1);
                add(MOCK_TOUR_LOG_2);
            }
        };

        lenient().when(tourDaoMock.get(anyLong())).thenReturn(Optional.of(MOCK_TOUR));
        when(tourDaoMock.getTourLogsOfTour(any())).thenReturn(tourLogs);

        // Act
        Report.tourReport(MOCK_TOUR);
        Assertions.assertNotNull(tourLogs.stream().mapToDouble(TourLog::getTourLogId));
        Assertions.assertNotNull(tourLogs.stream().mapToDouble(TourLog::getTotalTime).average());
        Assertions.assertNotNull(tourLogs.stream().mapToDouble(TourLog::getRating).average());
        Assertions.assertEquals(70, tourLogs.stream().mapToDouble(TourLog::getTotalTime).sum());
    }

}