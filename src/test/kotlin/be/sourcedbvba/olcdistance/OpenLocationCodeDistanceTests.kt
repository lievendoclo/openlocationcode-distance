package be.sourcedbvba.olcdistance

import com.google.openlocationcode.OpenLocationCode
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class OpenLocationCodeDistanceTest {

    @Nested
    inner class DistanceCalculationTests {

        @Test
        fun `calculate distance between two identical locations should be zero`() {
            val eiffelTower = OpenLocationCode("8FW4V75V+8F")

            val distance = OpenLocationCodeDistance.calculateDistanceInKilometers(eiffelTower, eiffelTower)

            assertEquals(0.0, distance, 0.001)
        }

        @Test
        fun `calculate distance between Eiffel Tower and Arc de Triomphe`() {
            val eiffelTower = OpenLocationCode("8FW4V75V+9Q")
            val arcDeTriomphe = OpenLocationCode("8FW4V7FW+G2")

            val distance = OpenLocationCodeDistance.calculateDistanceInKilometers(eiffelTower, arcDeTriomphe)

            // Expected distance is approximately 3.8 km
            assertEquals(1.7, distance, 0.5)
        }

        @Test
        fun `calculate distance between Eiffel Tower and Louvre`() {
            val eiffelTower = OpenLocationCode("8FW4V75V+8F")
            val louvre = OpenLocationCode("8FW4V7M5+6P")

            val distance = OpenLocationCodeDistance.calculateDistanceInKilometers(eiffelTower, louvre)

            // Expected distance is approximately 3.8 km
            assertEquals(3.8, distance, 0.5)
        }

        @Test
        fun `calculate distance between New York Times Square and London Piccadilly Circus`() {
            val timesSquare = OpenLocationCode("87G8Q2V8+M9")
            val piccadillyCircus = OpenLocationCode("9C3XGV56+F5")

            val distance = OpenLocationCodeDistance.calculateDistanceInKilometers(timesSquare, piccadillyCircus)

            // Expected distance is approximately 5,570 km
            assertEquals(5570.0, distance, 50.0)
        }

        @Test
        fun `calculate distance should be symmetric`() {
            val location1 = OpenLocationCode("8FW4V75V+8F")
            val location2 = OpenLocationCode("8FW4V9QF+25")

            val distance1to2 = OpenLocationCodeDistance.calculateDistanceInKilometers(location1, location2)
            val distance2to1 = OpenLocationCodeDistance.calculateDistanceInKilometers(location2, location1)

            assertEquals(distance1to2, distance2to1, 0.001)
        }

        @Test
        fun `calculate very short distance between nearby codes`() {
            val code1 = OpenLocationCode("8FW4V75V+8F")
            val code2 = OpenLocationCode("8FW4V75V+8G") // Very close, only last char differs

            val distance = OpenLocationCodeDistance.calculateDistanceInKilometers(code1, code2)

            // Should be a very small distance (less than 1 km)
            assertTrue(distance < 0.1)
        }

        @Test
        fun `calculate distance across equator`() {
            val northernHemisphere = OpenLocationCode("8FW4V75V+8F") // Paris
            val southernHemisphere = OpenLocationCode("4RRH46V8+74P") // Sydney Opera House

            val distance = OpenLocationCodeDistance.calculateDistanceInKilometers(
                northernHemisphere,
                southernHemisphere
            )

            // Should be a very large distance (> 15,000 km)
            assertTrue(distance > 15000.0)
        }

        @Test
        fun `calculate short distance`() {
            val brugesMarketSquareLeft = OpenLocationCode("9F35665F+FH") // Paris
            val brugesMarketSquareRight = OpenLocationCode("9F35665F+GW") // Sydney Opera House

            val distance = OpenLocationCodeDistance.calculateDistanceInKilometers(
                brugesMarketSquareLeft,
                brugesMarketSquareRight
            )
            println(distance)
            // Should be less than a km
            assertTrue(distance < 0.1)
        }
    }

    @Nested
    inner class UnitConversionTests {

        @Test
        fun `convert kilometers to miles correctly`() {
            val code1 = OpenLocationCode("8FW4V75V+8F")
            val code2 = OpenLocationCode("8FW4V9QF+25")

            val distanceKm = OpenLocationCodeDistance.calculateDistanceInKilometers(code1, code2)
            val distanceMiles = OpenLocationCodeDistance.calculateDistanceInMiles(code1, code2)

            // 1 km = 0.621371 miles
            assertEquals(distanceKm * 0.621371, distanceMiles, 0.001)
        }

        @Test
        fun `convert kilometers to meters correctly`() {
            val code1 = OpenLocationCode("8FW4V75V+8F")
            val code2 = OpenLocationCode("8FW4V9QF+25")

            val distanceKm = OpenLocationCodeDistance.calculateDistanceInKilometers(code1, code2)
            val distanceMeters = OpenLocationCodeDistance.calculateDistanceInMeters(code1, code2)

            // 1 km = 1000 meters
            assertEquals(distanceKm * 1000, distanceMeters, 0.001)
        }

        @Test
        fun `miles conversion for zero distance`() {
            val code = OpenLocationCode("8FW4V75V+8F")

            val distanceMiles = OpenLocationCodeDistance.calculateDistanceInMiles(code, code)

            assertEquals(0.0, distanceMiles, 0.001)
        }

        @Test
        fun `meters conversion for zero distance`() {
            val code = OpenLocationCode("8FW4V75V+8F")

            val distanceMeters = OpenLocationCodeDistance.calculateDistanceInMeters(code, code)

            assertEquals(0.0, distanceMeters, 0.001)
        }
    }

    @Nested
    inner class ExtensionFunctionTests {

        @Test
        fun `extension function calculateDistanceInKilometersTo works correctly`() {
            val eiffelTower = OpenLocationCode("8FW4V75V+9Q")
            val arcDeTriomphe = OpenLocationCode("8FW4V7FW+G2")

            val distance = eiffelTower.calculateDistanceInKilometersTo(arcDeTriomphe)

            assertEquals(1.7, distance, 0.5)
        }

        @Test
        fun `extension function calculateDistanceInMetersTo works correctly`() {
            val eiffelTower = OpenLocationCode("8FW4V75V+8F")
            val arcDeTriomphe = OpenLocationCode("8FW4V9QF+25")

            val distanceMeters = eiffelTower.calculateDistanceInMetersTo(arcDeTriomphe)
            val distanceKm = eiffelTower.calculateDistanceInKilometersTo(arcDeTriomphe)

            assertEquals(distanceKm * 1000, distanceMeters, 0.001)
        }

        @Test
        fun `extension function calculateDistanceInMilesTo works correctly`() {
            val eiffelTower = OpenLocationCode("8FW4V75V+8F")
            val arcDeTriomphe = OpenLocationCode("8FW4V9QF+25")

            val distanceMiles = eiffelTower.calculateDistanceInMilesTo(arcDeTriomphe)
            val distanceKm = eiffelTower.calculateDistanceInKilometersTo(arcDeTriomphe)

            assertEquals(distanceKm * 0.621371, distanceMiles, 0.001)
        }

        @Test
        fun `extension functions are symmetric`() {
            val location1 = OpenLocationCode("8FW4V75V+8F")
            val location2 = OpenLocationCode("8FW4V9QF+25")

            val distance1to2 = location1.calculateDistanceInKilometersTo(location2)
            val distance2to1 = location2.calculateDistanceInKilometersTo(location1)

            assertEquals(distance1to2, distance2to1, 0.001)
        }
    }

    @Nested
    inner class EdgeCaseTests {

        @Test
        fun `handle minimum length valid codes`() {
            val code1 = OpenLocationCode("8FW4V700+")
            val code2 = OpenLocationCode("8FW4V800+")

            val distance = OpenLocationCodeDistance.calculateDistanceInKilometers(code1, code2)

            assertTrue(distance > 0.0)
        }

        @Test
        fun `handle codes at poles`() {
            val northPole = OpenLocationCode("CFP3VHX6+X5")
            val southPole = OpenLocationCode("22735WPG+VV")

            val distance = OpenLocationCodeDistance.calculateDistanceInKilometers(northPole, southPole)

            // Distance should be approximately 20,000 km (half Earth's circumference)
            assertEquals(20000.0, distance, 1000.0)
        }

        @Test
        fun `invalid code throws exception`() {
            assertThrows<IllegalArgumentException> {
                OpenLocationCode("INVALID_CODE")
            }
        }
    }

    @Nested
    inner class PrecisionTests {

        @Test
        fun `distance precision with 8-character codes`() {
            val code1 = OpenLocationCode("8FW4V700+")
            val code2 = OpenLocationCode("8FW4V800+")

            val distance = OpenLocationCodeDistance.calculateDistanceInKilometers(code1, code2)

            // With 8-char codes, precision is ~14m, so distances should be reasonably accurate
            assertTrue(distance > 0.0)
        }

        @Test
        fun `distance precision with 10-character codes`() {
            val code1 = OpenLocationCode("8FW4V75V+8F")
            val code2 = OpenLocationCode("8FW4V75V+9F")

            val distance = OpenLocationCodeDistance.calculateDistanceInKilometers(code1, code2)

            // With 10-char codes, precision is ~3.5m
            assertTrue(distance < 1.0) // Should be less than 1 km
        }

        @Test
        fun `distance precision with 11-character codes`() {
            val code1 = OpenLocationCode("8FW4V75V+8F9")
            val code2 = OpenLocationCode("8FW4V75V+8F8")

            val distance = OpenLocationCodeDistance.calculateDistanceInKilometers(code1, code2)

            // With 11-char codes, precision is very high
            assertTrue(distance < 0.01) // Should be less than 10 meters
        }
    }

    @Nested
    inner class PerformanceTests {

        @Test
        fun `calculate distance for multiple pairs efficiently`() {
            val codes = listOf(
                OpenLocationCode("8FW4V75V+8F"),
                OpenLocationCode("8FW4V9QF+25"),
                OpenLocationCode("8FW4V7M5+6P"),
                OpenLocationCode("8FW4V7PJ+23")
            )

            val startTime = System.currentTimeMillis()

            codes.forEach { code1 ->
                codes.forEach { code2 ->
                    OpenLocationCodeDistance.calculateDistanceInKilometers(code1, code2)
                }
            }

            val endTime = System.currentTimeMillis()
            val duration = endTime - startTime

            // Should complete reasonably quickly (< 100ms for 16 calculations)
            assertTrue(duration < 100, "Calculations took too long: ${duration}ms")
        }
    }
}