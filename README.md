# cmnav

[![check](https://github.com/lukwol/cmnav/actions/workflows/check.yml/badge.svg)](https://github.com/lukwol/cmnav/actions/workflows/check.yml)
[![](https://jitpack.io/v/lukwol/cmnav.svg)](https://jitpack.io/#lukwol/cmnav)

Tiny library for easy navigation in [Compose Multiplatform](https://github.com/JetBrains/compose-jb/)
applications.

Provides:
 * Screens navigation - multiplatform
 * Windows navigation - desktop only
 * Optional `ViewModel` with work cancellation support - multiplatform

## Installation

Currently `cmnav` can be installed only via local Maven or [Jitpack](https://jitpack.io/) repositories.

Installation via Jitpack is recommended but provides only a few native targets, install to local maven in order to use more native targets.

### MavenLocal

Step 1. Clone or download `cmnav` repository

Step 2. Build and publish to local Maven repository by running: 

```shell
./gradlew publishToMavenLocal
```

Step 3. Add the local Maven repository to `build.gradle.kts` at the end of repositories:

```kotlin
allprojects {
    repositories {
        // ...
        mavenLocal()
    }
}
```

Step 4. Add the dependency:

```kotlin
dependencies {
    // Screens navigation - multiplatform
    implementation("io.github.lukwol:cmnav-screens:0.1.0")
    // Screens navigation with ViewModel support - multiplatform
    implementation("io.github.lukwol:cmnav-screens-vm:0.1.0")
    // Windows navigation - jvm only, for desktop application
    implementation("io.github.lukwol:cmnav-windows:0.1.0")
}
```

## Usage

Check out [examples](https://github.com/lukwol/cmnav/tree/main/examples/) for actual usage in the app.

### Build screens navigation

Basic screens navigation:

```kotlin
ScreensNavigation(
    startRoute = AppRoutes.FirstScreenRoute,
) {
    screen(AppRoutes.FirstScreenRoute) {
        FirstScreen()
    }
    
    screen(AppRoutes.SecondScreenRoute) { args: String? ->
        SecondScreen(args)
    }
}
```

Screens navigation with `ViewModel`:

```kotlin
ScreensNavigation(
    startRoute = AppRoutes.FirstScreenRoute
) {
    screen(
        route = AppRoutes.FirstScreenRoute,
        viewModelFactory = { 
            FirstScreenViewModel() 
        }
    ) { viewModel ->
        FirstScreen(viewModel)
    }
    
    screen(
        route = AppRoutes.SecondScreenRoute,
        viewModelWithArgs = { args: SomeArgs? -> 
            SecondScreenViewModel(args) 
        }
    ) { viewModel ->
        SecondScreen(viewModel)
    }
}
```

### Build windows navigation if needed (desktop)

```kotlin
WindowsNavigation(
    startRoute = AppRoutes.FirstWindowRoute
) {
    window(
        route = AppRoutes.FirstWindowRoute,
        title = "First Window"
    ) {
        ScreensNavigation(
            startRoute = AppRoutes.FirstScreenRoute
        ) {
            // ...
        }
    }

    window(
        route = AppRoutes.SecondWindowRoute,
        title = "Second Window"
    ) {
        ScreensNavigation(
            startRoute = AppRoutes.SecondScreenRoute
        ) {
            // ...
        }
    }
}
```

### Navigate to screen

```kotlin
// Obtain LocalScreensController in your view
val screensController = LocalScreensController.current

// Push screen
screensController.push(AppRoutes.SecondScreenRoute)

// Optionally pass arguments if needed
screensController.push(AppRoutes.SecondScreenRoute, SomeArguments)

// Pop screen to navigate back
screensController.pop()

// Optionally pass route, up to which screens should be dismissed
screensController.pop(AppRoutes.FirstScreenRoute)
```

### Navigate to window (desktop)

```kotlin
// Obtain LocalWindowController in your view
val windowsController = LocalWindowController.current

// Open window
windowsController.open(AppRoutes.SecondWindowRoute)

// Optionally pass arguments if needed
windowsController.open(AppRoutes.SecondWindowRoute, SomeArguments)

// Close window
windowsController.close(AppRoutes.SecondWindowRoute)
```

## Documentation

API Reference is available at https://lukwol.github.io/cmnav/ 

## Licensing

Project is available under [MIT](https://github.com/lukwol/cmnav/blob/main/LICENSE) License.
