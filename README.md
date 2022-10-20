# MovieBrowser

This is a sample application, which uses the [OMDb API](http://www.omdbapi.com/) to get movie data.

The app follow the **MVVM software architectural pattern** and uses the below libraries.

## Preview
<img width="80%" src="https://user-images.githubusercontent.com/35184909/196878661-5abe46c1-5c2c-4189-9484-59e06c9f3851.PNG"/>

## Project Structure
```
.
└── MovieBroswer/
    ├── app/
    │   ├── core/
    │   │   ├── base
    │   │   └── extension
    │   ├── di
    │   ├── view
    │   └── viewModel
    ├── data/
    │   ├── remote/
    │   │   ├── api
    │   │   ├── model
    │   │   └── source/
    │   │       └── dao
    │   └── <repository implementation>
    └── domain/
        ├── model
        ├── repository
        └── usecase 
```

## Libraries
* Kotlin Coroutines
* Kotlin Flow
* Dagger Hilt
* Retrofit
* Gson
* Navigation Component
* DataBinding
* LifeCycle
* Room
* Glide


