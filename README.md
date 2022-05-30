# TourPlanner
An application based on the GUI frameworks Java / JavaFX.
The user creates (bike-, hike-, running- or vacation-) tours in advance and manages the logs 
and statistical data of accomplished tours. The tours and tour logs are stored in PostgreSQL database.
Tour information are collected from MapQuest API and displayed accordingly in the GUI.

### Git Link
[https://github.com/wajudev/TourPlanner.git](https://github.com/wajudev/TourPlanner.git)

### App Architecture / Pattern
The application is divided into 3 layers:
1. MVVM Pattern Layer
2. Business Layer
3. Data Access Layer (DAL)

#### MVVM Pattern Layer
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

