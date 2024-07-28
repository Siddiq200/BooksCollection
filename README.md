# BooksCollection Android App

Welcome to BooksCollection, an Android application that allows users to browse a collection of books. This app demonstrates modern Android development practices including MVVM architecture, Hilt for dependency injection, Clean Architecture principles, and pagination using Paging 3 library.

## Overview

BooksCollection fetches book data from a remote API (https://gutendex.com/), allows users to search books, mark favorites(locally), and view detailed book information. The app utilizes Room for local data storage, Retrofit for network requests, and integrates with a paging library for efficient data loading.

## Setup Instructions

To run the BooksCollection app on your local machine, follow these steps:

1. **Clone the repository:**
   git clone https://github.com/Siddiq200/BooksCollection.git
2. **Open in Android Studio:**
- Open Android Studio.
- Choose "Open an existing Android Studio project."
- Navigate to the cloned project directory and select it.

3. **Run the app:**
- Connect an Android device or start an emulator.
- Click on the "Run" button in Android Studio to build and run the app.

## Architecture and Design Decisions

### MVVM Architecture
The app follows the MVVM (Model-View-ViewModel) architecture pattern:
- **Model**: Represents the data and business logic.
- **View**: Responsible for displaying data to users.
- **ViewModel**: Acts as an intermediary between the Model and View, handling UI-related logic and maintaining state.

### Dependency Injection (Hilt)
Hilt is used for dependency injection:
- **@AndroidEntryPoint**: Annotates activities, fragments, or views to enable injection of Android framework classes.

### Clean Architecture Principles
These layers are added for Clean Architecture principles:
- **Presentation Layer**: Contains UI components and ViewModel classes.
- **Domain Layer**: Defines models, use cases and business logic.
- **Data Layer**: Implements repositories for data handling, interfacing with APIs and databases.

## Additional Configurations and Libraries

- **Room**: Used for local database storage, mapping entities to database tables and back.
- **Retrofit**: Retrofit is used for handling network requests and interfacing with the remote API.
- **Paging 3**: Implements pagination for efficient data loading in RecyclerViews.
- **Gson**: Gson is used for JSON serialization and deserialization.
- **Coroutines**: Coroutines are used for asynchronous programming and managing background tasks.
- **Mockito**: Used for mocking dependencies in unit tests.

## design decisions

- **Coroutines**: Used for local data loading, RxJava was not compatible with Paging 3 Library, I have added RxJava in Repository. but did not connect with UI.
- **Offline Data**: On API calls, We store data in db, and show on different screen in case of no internet. I could not add Local Source in main search page with Paging due to time constraint.
- **Test**: Unit tests of Repository, Use cases and a viewModel are added. There are room for more Unit tests and UI tests that could not be added due to time constraint.
- **Additional Features**: Implemented additional features such as pagination, pull-to-refresh, and basic Android test using Espresso is added. 
  
## Contact

Please give feedback to:
- Email: siddiq200@gmail.com

**Thank You**

