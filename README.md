# TourPlanner
An application based on the GUI frameworks Java / JavaFX.
The user creates (bike-, hike-, running- or vacation-) tours in advance and manages the logs 
and statistical data of accomplished tours. The tours and tour logs are stored in PostgreSQL database.
Tour information are collected from MapQuest API and displayed accordingly in the GUI.

### Git Link
[https://github.com/wajudev/TourPlanner.git](https://github.com/wajudev/TourPlanner.git)

### App Architecture / Pattern
The application is divided into 3 layers:
1. MVVM Pattern Layer (View Layer)
2. Business Layer
3. Data Access Layer (DAL)

#### MVVM Pattern / View Layer
The application is uses the model-view-viewModel (MVVM) pattern, meaning the graphical user interface (GUI/ the View) is seperated from the 
development of the business logic (The Model).
The ViewModel of the MVVM is responsible for the conversion of data objects from the model and also the handling of the view display logic.
- The View 
  * Contains the FXML File with the GUI Structure and layout.
  - Controller
    * Creates bindings between the view elements and viewModel properties.
    * Controls the actions of the views
    * Instantiates the viewModel.
    * Initializes properties of viewModel accordingly.
- The viewModel
  * Validates GUI input fields
  * Conversion of model properties 
  * Bridge between the Business layer and the view.

#### Business Layer
- TourManager - imports class from the DAL which are instantiated depending on which concrete DALFactory implementation is chosen.  
- EventManager - it manages multiple Event Listeners, and its corresponding events.
- MapQuest - an external API, that helps with the retrieval of route information 
- Report - Helps with creation of a few stats from the computed values.
- ConfigurationManager - it manages the configuration details(DB, API Key etc.) for the whole app.

#### Data Access Layer
Controls data access. 
- DALFactory creates instances of the DAO members to be used by the TourManager.
- Database Class - establishes connection to the database and creates PreparedStatements for database queries.
- GenericDao - Generic Interface to deal with the abstract data types, to avoid redundant codes.
- TourDao - extends GenericDao , implemented by TourDaoImpl contains SQL string queries, to manipulate the database.
- TourLogDao - extends GenericDao , implemented by TourLogDaoImpl contains SQL string queries, to manipulate the database.

### Use Cases
See [documents/useCaseDiagramm.png](documents/useCaseDiagramm.png)

### Implemented design pattern
1) Observer pattern
   - is a behavioral design pattern that lets you define a subscription mechanism to notify multiple objects about any 
     events that happen to the object they’re observing. See EventManager.

2) Singleton
* to instantiate the Business-layer objects using the Factory. The factory returns a singleton. 
   This prevents that more than one instance/object of the same class is created.


### Unique Feature
* If you click on "Export", then a new window will open. Here you can choose from the left list, which tours you want to export. You just need to double-click on it and then the tour will be
   moved to the right list.
* To improve the performance, we implemented a loading GIF until the imageView loaded the static map.
* We also implemented a graph and a pie-chart, which visualizes the data(Rating and Time Spent of tours) generated from the computed input. 

### UX, Library decision
 * UX: Each view has its own controller
 * Libraries:
    1. JSON: Used in ``StaticMapQuest`` to create JSONObjects from a json response.
    2. Lombok: easier to create getter, setter and constructors.
    3. PostgreSQL: to create a database.
    4. Jackson-core: To Import and Export tours, we prefer using the ``ObjectMapper`` instead of JSONObjects, because all tours have the same structure, so its easier and clearer.
    5. Itext 7 core: To create the report.
    6. Mockito: Allows for mocking of objects.
    7. Javadoc: Allows for easy documentation.
    8. Log4j: Logging application library for recoding app activity.
    9. JUnit: For unit-testing.
    10. SLF4J: Abstraction of all logging frameworks.

### Unittest Decision
We decided to only test the DAL and TourManager because other tests would involve us testing the API. 
For example, the Report class would be impossible to test, if we do not test the api and testing the API is not considered unit-testing. 
We also decided not test the GUI, because due to our time constraint, it was just easier to carry out the test manually 


### Tracked time per person
* Tom Mihnjak: 75h
* Olanrewaju Ajibua: 85h

### Lessons Learned
We both struggled at first with to deal with the MVVM Pattern, as we have been accustomed to MVC Pattern. 
We learned how important decoupling is, we think it is good knowledge to have in our repertoire.
We also learned the basics of JavaFX and the many unique features, this framework has to offer.


### Link to GIT
https://github.com/wajudev/TourPlanner



