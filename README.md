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
- TourManager imports class from the DAL which are instantiated depending on which concrete DALFactory implementation is chosen.  
- EventManager
- MapQuest
- Report
- ConfigurationManager

#### Data Access Layer
Controls data access.

#### Implemented design pattern

1)Observer 
 - is a behavioral design pattern that lets you define a subscription mechanism to notify multiple objects about any 
   events that happen to the object they’re observing


2)Singelton
* to instantiate the Businesslayer objects using theFactory. The factory returns a singleton. 
   This prevents that more than one instance/object of the same class is created.


### Unique Feature
* If you click on "Export", then a new window will open. Here you can choose from the left list, which tours you want to export. You just need to double click on ist and then the tour will be
moved to the right list.
* To improve the performance, we implemented a loading GIF until the imageView loaded the static map
* We also implemented a graph, which shows us the rating from different tours

### UX, Library decision
 * UX: Each view has its own controller
 * Libraries:
    1. json: Used in ``StaticMapQuest`` to create JSONObjects from a json response
    2. lombok: easier to create getter, setter and constructors
    3. postgreSQL: to creeate a database
    4. Jacksoncore: To Import and Export tours, we prefere using the ``ObjectMapper`` instead of JSONObjects, because all tours have the same structure, so its easiere and clearer
    5. 

### Tracked time
* Tom Mihnjak: 65h
* Olanrewaju Ajiuba: 

### Link to GIT
https://github.com/wajudev/TourPlanner



