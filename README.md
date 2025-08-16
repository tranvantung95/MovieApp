# MovieApp - Kotlin Multiplatform Mobile

A modern movie discovery application built with Kotlin Multiplatform Mobile (KMM) showcasing clean architecture principles and modular design. The app demonstrates best practices for cross-platform development with shared business logic.

## Features

- **Trending Movies**: Browse popular movies with detailed information
- **Movie Search**: Find movies by title with real-time search
- **Movie Details**: View comprehensive movie information including cast and crew
- **Offline Support**: Cached data for seamless offline browsing

## Architecture

MovieApp follows the [official architecture guidance](https://developer.android.com/topic/architecture) and implements clean architecture principles with a multi-module approach for better scalability, testability, and team collaboration.

### Module Structure

```
┌─────────────────────────────────────────────────────────────┐
│                    shared                                   │
│                (KMM Entry Point)                            │
│  • Orchestrates all modules                                │
│  • Exposes APIs to platform apps                           │
│                      │                                      │
│                 depends on                                  │
│                      ▼                                      │
┌─────────────────────────────────────────────────────────────┐
│                   Module Layer                              │
│                                                             │
│  ┌─────────────────┐              ┌─────────────────────┐   │
│  │      core       │              │      feature        │   │
│  │                 │              │                     │   │
│  │ • domain        │              │ • movie:domain      │   │
│  │ • data          │              │ • movie:data        │   │
│  │ • network       │              │                     │   │
│  │ • database      │              │                     │   │
│  └─────────────────┘              └─────────────────────┘   │
│           ▲                                    │             │
│           └────────────────────────────────────┘             │
│                        depends on                            │
└─────────────────────────────────────────────────────────────┘
```

## Project Structure

```
MovieApp/
├── shared/                         # KMM shared module
├── core/                          # Foundation modules
│   ├── domain/                    # Base domain types
│   ├── data/                      # Data utilities and caching
│   ├── network/                   # Network configuration
│   └── database/                  # Database setup
└── feature/                       # Business logic modules
    └── movie/
        ├── domain/                # Movie business logic
        └── data/                  # Movie data implementation
```

## Modularization

The app has been fully modularized following these principles:

- **Separation of concerns**: Each module has a clear, focused responsibility
- **Loose coupling**: Modules depend on abstractions, not implementations
- **High cohesion**: Related functionality is grouped together
- **Dependency direction**: Dependencies flow toward the domain layer

### Module Types

- **`shared`**: KMM entry point that orchestrates functionality for platform apps
- **`core:*`**: Foundation modules providing shared utilities (domain, data, network, database)
- **`feature:*`**: Business logic modules containing domain and data layers

### Dependencies

```
shared → feature modules
feature:movie:data → feature:movie:domain + core modules  
feature:movie:domain → core:domain
```

## Tech Stack

- **[Kotlin Multiplatform Mobile](https://kotlinlang.org/docs/multiplatform-mobile-getting-started.html)** - Cross-platform development
- **[Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)** - Asynchronous programming
- **[Ktor](https://ktor.io/)** - HTTP client for API requests
- **[Room](https://developer.android.com/training/data-storage/room)** - Local database
- **[Koin](https://insert-koin.io/)** - Dependency injection

## Getting Started

### Prerequisites
- Android Studio Ladybug or newer
- Kotlin 2.2.0+
- Gradle 8.5+

### Configuration

1. **API Key Setup**: Add your TMDB API key to `local.properties`:
   ```properties
   API_KEY="Your API KEY"
   ```

### Building

1. Clone the repository
2. Open the project in Android Studio
3. Build the shared module to generate KMM libraries:

   **For Android (Maven Local)**:
   ```bash
   ./gradlew shared:publishToMavenLocal
   ```

   **For iOS (XCFramework)**:
   ```bash
   ./gradlew :shared:buildXCFramework --configuration-cache
   ```

   iOS framework will be available at: `shared/build/XCFrameworks/`

4. Platform apps can then depend on the shared module

The shared module uses static demo data for immediate building and exploration.


## Benefits of This Architecture

- **Scalability**: Easy to add new features as independent modules
- **Parallel Development**: Teams can work on different modules simultaneously
- **Code Reuse**: Shared business logic across platforms
- **Build Performance**: Only changed modules need rebuilding
- **Testing**: Each layer can be tested independently

