# Walmart Challenge XML - Android Coding Challenge

## Overview
This project is part of an Android coding challenge, where the objective is to build an app that fetches product data 
from a remote API and stores it locally using Room. The app is built following **Clean Architecture** with **MVVM** pattern, 
**Hilt** for dependency injection, and **Coroutines** for asynchronous operations. The project also includes comprehensive unit tests using **MockK** and **JUnit**.

## Features
- Fetch product data from a REST API using **Retrofit**.
- Store fetched product data in a local database using **Room**.
- Display product data in a **RecyclerView** using **ViewBinding** and **DataBinding**.
- Handle loading states with a custom **CircularProgressView**.
- Provide pull-to-refresh functionality using **SwipeRefreshLayout**.
- Implemented unit tests for Repository.

## Technologies Used
- **Kotlin**: The main programming language used for development.
- **Clean Architecture**: The app is structured to maintain separation of concerns, with layers such as data, domain, and presentation.
- **MVVM Architecture**: To maintain a clear separation between the UI and business logic.
- **Hilt**: For Dependency Injection (DI).
- **Retrofit**: To handle API calls.
- **Room**: As a local database for caching product data.
- **Coroutines**: For asynchronous programming and background operations.
- **StateFlow**: To manage and observe state changes within the ViewModel.
- **RecyclerView**: For displaying the list of products.
- **DataBinding**: To bind UI components to data in a lifecycle-conscious way.
- **ViewBinding**: For efficient binding of views.
- **SwipeRefreshLayout**: For implementing pull-to-refresh functionality.
- **MockK**: To mock dependencies during unit testing.
- **JUnit**: To run unit tests.

## Architecture
This project follows **Clean Architecture**, with distinct layers:
- **Data Layer**: Handles the fetching of data from the API and local database. Contains DTOs (Data Transfer Objects), Repositories, Room entities, and DAOs.
- **Domain Layer**: Contains business logic. Defines repository interfaces and domain models.
- **Presentation Layer**: Contains the UI components, ViewModels, and Adapters for the `RecyclerView`.

## Project Structure
```bash 
walmartchallengexml
├── core
│   ├── di
│   │   ├── DatabaseModule.kt       // Hilt module for Room database
│   │   ├── NetworkModule.kt        // Hilt module for Retrofit
│   │   └── RepositoryModule.kt     // Hilt module for Repository binding
│   └── util
│       └── ResponseHandler.kt      // Utility to handle success and error responses
├── data
│   ├── mapper
│   │   └── Mapper.kt               // Mapper to convert DTOs to domain models
│   ├── model
│   │   ├── ProductsDTO.kt          // Data Transfer Object for product API responses
│   │   └── ProductsResponse.kt     // API response object containing the product list
│   ├── repository
│   │   └── RepositoryImpl.kt       // Repository implementation handling API and database interactions
│   ├── room
│   │   ├── dao
│   │   │   └── ProductDao.kt       // DAO for accessing product data in Room
│   │   ├── db
│   │   │   └── ProductDatabase.kt  // Room database configuration
│   │   └── entity
│   │       └── ProductEntity.kt    // Room entity representing a product in the database
│   └── service
│       └── ApiService.kt           // Retrofit service for API calls
├── domain
│   ├── model
│   │   └── Product.kt              // Domain model representing a product
│   └── repository
│       └── IRepository.kt          // Repository interface
├── presentation
│   ├── util
│   │   └── CircularProgressViewCustom.kt // Custom view for loading indicator
│   ├── MainActivity.kt             // Main activity that displays the product list
│   ├── MainViewModel.kt            // ViewModel managing product data and states
│   └── ProductAdapter.kt           // Adapter for RecyclerView to display products
```

## Libraries Used

1. **Hilt (Dependency Injection)**
    - [Hilt Documentation](https://developer.android.com/training/dependency-injection/hilt-android)
    - Purpose: Used for dependency injection to provide objects where needed in the app.

2. **Retrofit (Networking)**
    - [Retrofit Documentation](https://square.github.io/retrofit/)
    - Purpose: Makes network requests and handles API calls with ease.

3. **OkHttp (HTTP Client)**
    - [OkHttp Documentation](https://square.github.io/okhttp/)
    - Purpose: An efficient HTTP client used by Retrofit for making network requests.

4. **Gson (JSON Parsing)**
    - [Gson Documentation](https://github.com/google/gson)
    - Purpose: A JSON library for converting Java/Kotlin Objects into their JSON representation and vice versa.

5. **Room (Local Database)**
    - [Room Documentation](https://developer.android.com/training/data-storage/room)
    - Purpose: Provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.

6. **Kotlin Coroutines**
    - [Kotlin Coroutines Documentation](https://kotlinlang.org/docs/coroutines-overview.html)
    - Purpose: Supports asynchronous programming, simplifies managing background tasks, and works well with Room and Retrofit.

7. **Lifecycle ViewModel KTX**
    - [Lifecycle ViewModel KTX Documentation](https://developer.android.com/kotlin/ktx)
    - Purpose: Adds Kotlin extensions for ViewModel to make it more concise and easy to use.

8. **Activity KTX**
    - [Activity KTX Documentation](https://developer.android.com/kotlin/ktx)
    - Purpose: Provides Kotlin extensions that make writing Android code more concise and idiomatic.

9. **SwipeRefreshLayout**
    - [SwipeRefreshLayout Documentation](https://developer.android.com/reference/androidx/swiperefreshlayout/widget/SwipeRefreshLayout)
    - Purpose: Provides the user with the ability to refresh the content of a view via a swipe gesture.

10. **MockK (Unit Testing)**
    - [MockK Documentation](https://mockk.io/)
    - Purpose: A powerful mocking library for Kotlin used for writing unit tests.

11. **JUnit4 (Testing Framework)**
    - [JUnit4 Documentation](https://junit.org/junit4/)
    - Purpose: A widely-used testing framework for Java and Kotlin that allows running unit tests.

12. **Kotlinx Coroutines Test**
    - [Kotlin Coroutines Test Documentation](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/)
    - Purpose: A library for testing Kotlin Coroutines, allowing control over time and coroutine execution.

13. **Android Arch Core Testing**
    - [Android Arch Core Testing Documentation](https://developer.android.com/topic/libraries/architecture/arch)
    - Purpose: Provides helpers to test LiveData, ViewModels, and other Architecture Components.

## Design Patterns Used

1. **MVVM (Model-View-ViewModel)**
    - Ensures separation of concerns by dividing the application into three layers: Model (business logic), View (UI), and ViewModel (connects UI and data).
    - Utilized in `MainViewModel` to manage the interaction between UI and data sources.

2. **Repository Pattern**
    - Provides a clean API to the data layer. The `Repository` is responsible for deciding whether to fetch data from the network (Retrofit) or local database (Room).
    - Implemented in `RepositoryImpl`.

3. **Dependency Injection (DI)**
    - Used to inject dependencies such as Retrofit, Room Database, and Repositories without having to instantiate them manually.
    - Handled by **Hilt** throughout the project.

4. **DAO Pattern (Data Access Object)**
    - Separates database logic into DAOs that define the methods to interact with the database (Room).
    - Implemented in `ProductDao`.

5. **Observer Pattern**
    - Used with `StateFlow` and `LiveData` in `MainViewModel` to notify the UI of data changes.
    - Also used in testing with `Android Arch Core Testing`.

6. **Builder Pattern**
    - Used in **OkHttp** for building HTTP requests in a flexible way.

7. **Adapter Pattern**
    - Used in **Retrofit** and **Gson** for adapting data from the API into Kotlin objects and vice versa.
