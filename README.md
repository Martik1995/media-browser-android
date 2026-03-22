## Note

This README was structured with the help of AI tools. All architectural decisions and explanations reflect my own understanding.

## Overview

This is a small Android application that displays a list of media items and allows the user to open a details screen for each item. The project was built as part of a technical assignment using Kotlin, XML, MVVM, and a public REST API.
The application includes media list loading, pull-to-refresh, loading/error/empty states, and local search as the chosen extra feature.

## Features

- Fetch media items from a public REST API
- Display media items in a vertically scrollable list
- Show image, title, and secondary information for each item
- Open a details screen on item click
- Handle loading, error, retry, and empty states
- Support pull-to-refresh
- Load details screen data by item id
- Local search over the loaded media list

## Architecture Decisions

The assignment required MVVM, but I implemented the presentation layer using a single UI state exposed through StateFlow. I prefer this approach because it makes state management more explicit and predictable: loading, content, empty, and error states are all represented in one place instead of being split across multiple independent variables.

I also added use cases, repository abstraction, and a separate data layer to keep the project aligned with clean architecture principles. While this level of separation is not strictly necessary for such a small application, it improves maintainability and makes future changes easier. In a production-like scenario, this structure helps when adding new features, changing data sources, or introducing unit tests.

A simpler solution would have been to use plain MVVM with direct calls from the ViewModel to the data source. However, I chose a middle ground between a minimal implementation and over-engineering: enough structure to keep the code scalable and readable without adding unnecessary complexity.

## API Choice

I selected the public Studio Ghibli API for this assignment. One of the reasons for choosing it was that many public movie APIs are either unstable, require authentication, or do not provide a convenient structure for a small test project. This API was simple to integrate, publicly accessible, and provided the core fields needed for both the list and details screens.

At the same time, I tried to keep the application structure flexible enough so that the media source can be replaced with minimal changes. Although the current implementation uses a movie API, the same architecture can be adapted to books, TV shows, or other media types with only small changes in the data layer and mappings.

## Details Screen Decision

I decided to load the details screen data separately by item id instead of passing the full object from the list screen.

The main reason is that the list response did not contain enough information for the details screen. In practice, this is a common scenario: list items are usually lightweight, while the details screen often requires a richer object with more fields.

If both screens used exactly the same data model, passing the selected item directly between fragments would also have been a valid option. However, in this case, fetching the details separately made the screen more independent and better matched the API structure.

## Tradeoffs

The selected API did not support pagination, so I chose search as the extra feature instead. Search is currently implemented locally on the already loaded list, which keeps the solution simple and fits the scope of the assignment well.

I also kept the search logic flexible enough so that it can later be adapted to server-side search if the data source is replaced with an API that supports search queries.

## Possible Improvements

- Add unit tests for ViewModels, use cases, and repository layer
- Introduce in-memory or persistent caching
- Support pagination if the selected API or a future API provides it
- Move local search to server-side search when supported by the backend
- Improve error handling with more specific user-facing messages

## How to Run

1. Clone the repository
2. Open the project in Android Studio
3. Wait for Gradle sync to complete
4. Run the application on an emulator or a real Android device

Requirements:
- Android Studio
- Android SDK
- Internet connection for loading media data

No API key or additional setup is required.
