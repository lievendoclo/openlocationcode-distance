[![Java CI with Gradle](https://github.com/lievendoclo/openlocationcode-distance/actions/workflows/gradle.yml/badge.svg)](https://github.com/lievendoclo/openlocationcode-distance/actions/workflows/gradle.yml)

# Open Location Code Distance Calculator (Kotlin)

Calculate the distance between two Open Location Code (Plus Code) addresses using Google's official library, written in idiomatic Kotlin.

## Dependencies

This project uses Google's Open Location Code library:
- **Maven:** `com.google.openlocationcode:openlocationcode:1.0.4`
- **Gradle:** `implementation("com.google.openlocationcode:openlocationcode:1.0.4")`
- **Kotlin:** `2.1.20+`

## Setup

### Using Gradle (Recommended for Kotlin)
```bash
gradle build
gradle run
```

### Using Maven
```bash
mvn clean compile
```

## Usage

### Basic Distance Calculation

```kotlin
fun main() {
    val code1 = "8FW4V75V+8F"  // Eiffel Tower
    val code2 = "8FW4V9QF+25"  // Arc de Triomphe
    
    // Using object methods
    val distanceKm = OpenLocationCodeDistance.calculateDistanceInKilometers(code1, code2)
    println("Distance: ${"%.2f".format(distanceKm)} km")
    
    // Calculate in other units
    val distanceMiles = OpenLocationCodeDistance.calculateDistanceInMiles(code1, code2)
    val distanceMeters = OpenLocationCodeDistance.calculateDistanceInMeters(code1, code2)
}
```

### Idiomatic Kotlin with Extension Functions

```kotlin
fun main() {
    val eiffelTower = "8FW4V75V+8F"
    val arcDeTriomphe = "8FW4V9QF+25"
    
    // Use extension function for cleaner syntax
    val distance = eiffelTower.calculateDistanceInKilometersTo(arcDeTriomphe)
    println("Distance: ${"%.2f".format(distance)} km")
    
    // Get coordinates easily
    val coords = eiffelTower.toCenterCoordinates()
    println("Coordinates: ${coords}")  // Coordinates(latitude=48.858370, longitude=2.294481)
    
    // Decode to area
    val area = eiffelTower.decodeOLC()
    println("Lat: ${area.centerLatitude}, Lng: ${area.centerLongitude}")
}
```

### Working with Multiple Locations

```kotlin
fun main() {
    val locations = mapOf(
        "Eiffel Tower" to "8FW4V75V+8F",
        "Louvre Museum" to "8FW4V7M5+6P",
        "Notre-Dame" to "8FW4V7PJ+23"
    )
    
    // Calculate all distances using extension function
    locations.forEach { (name1, code1) ->
        locations.forEach { (name2, code2) ->
            if (name1 < name2) {
                val dist = code1.calculateDistanceInKilometersTo(code2)
                println("$name1 to $name2: ${"%.2f".format(dist)} km")
            }
        }
    }
}
```

## API

### Object Methods

| Method                                        | Description | Return Type |
|-----------------------------------------------|-------------|-------------|
| `calculateDistanceInKilometers(code1, code2)` | Distance in kilometers | `Double` |
| `calculateDistanceInMiles(code1, code2)`      | Distance in miles | `Double` |
| `calculateDistanceInMeters(code1, code2)`     | Distance in meters | `Double` |

### Extension Functions (Kotlin-specific)

| Extension                                                     | Description                        | Return Type |
|---------------------------------------------------------------|------------------------------------|-------------|
| `OpenLocationCode.calculateDistanceInKilometersTo(otherCode)` | Distance to another code in km     | `Double` |
| `OpenLocationCode.calculateDistanceInMetersTo(otherCode)`     | Distance to another code in meters | `Double` |
| `OpenLocationCode.calculateDistanceInMilesTo(otherCode)`      | Distance to another code in miles  | `Double` |

## Kotlin Features Used

- **Object declaration** for singleton utility
- **Extension functions** for cleaner API
- **Null safety** built-in
- **Type inference** for concise code

## What is Open Location Code?

Open Location Code (Plus Code) is an open-source geocoding system that provides short codes to identify locations. Instead of street addresses, you can use codes like `8FW4V75V+8F`.

- **Format:** 8 characters + separator + 2+ characters (e.g., `8FW4V75V+8F`)
- **Precision:** Longer codes = more precise locations
- **Global:** Works anywhere in the world

Learn more: https://plus.codes/

## How Distance is Calculated

The distance calculation uses the **Haversine formula**, which calculates the great-circle distance between two points on a sphere (Earth). This gives accurate distances accounting for the Earth's curvature.

## License

This library is licensed under Apache 2.0.
