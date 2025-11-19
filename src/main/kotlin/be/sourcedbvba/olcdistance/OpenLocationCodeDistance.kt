package be.sourcedbvba.olcdistance

import com.google.openlocationcode.OpenLocationCode
import kotlin.math.*

/**
 * Calculate the distance between two Open Location Code (Plus Code) addresses
 * using Google's official Open Location Code library and the Haversine formula.
 */
object OpenLocationCodeDistance {

    private const val EARTH_RADIUS_KM = 6371.0

    /**
     * Calculate distance between two coordinates using Haversine formula
     * @return distance in kilometers
     */
    private fun haversineDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {
        val lat1Rad = Math.toRadians(lat1)
        val lat2Rad = Math.toRadians(lat2)
        val deltaLat = Math.toRadians(lat2 - lat1)
        val deltaLng = Math.toRadians(lng2 - lng1)

        val a = sin(deltaLat / 2).pow(2) +
                cos(lat1Rad) * cos(lat2Rad) *
                sin(deltaLng / 2).pow(2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return EARTH_RADIUS_KM * c
    }

    /**
     * Calculate distance between two Open Location Codes using Google's library
     * @param olc1 First Open Location Code (e.g., "8FW4V75V+8F")
     * @param olc2 Second Open Location Code (e.g., "8FW4V75V+8G")
     * @return distance in kilometers
     */
    fun calculateDistanceInKilometers(olc1: OpenLocationCode, olc2: OpenLocationCode): Double {
        // Get the center coordinates of each code area
        val area1 = olc1.decode()
        val area2 = olc2.decode()

        val lat1 = area1.centerLatitude
        val lng1 = area1.centerLongitude
        val lat2 = area2.centerLatitude
        val lng2 = area2.centerLongitude

        return haversineDistance(lat1, lng1, lat2, lng2)
    }

    /**
     * Calculate distance in miles
     */
    fun calculateDistanceInMiles(olc1: OpenLocationCode, olc2: OpenLocationCode): Double =
        calculateDistanceInKilometers(olc1, olc2) * 0.621371

    /**
     * Calculate distance in meters
     */
    fun calculateDistanceInMeters(olc1: OpenLocationCode, olc2: OpenLocationCode): Double =
        calculateDistanceInKilometers(olc1, olc2) * 1000
}

/**
 * Calculate distance between this Open Location Code and another in kilometers
 */
fun OpenLocationCode.calculateDistanceInKilometersTo(other: OpenLocationCode): Double =
    OpenLocationCodeDistance.calculateDistanceInKilometers(this, other)

/**
 * Calculate distance between this Open Location Code and another in meters
 */
fun OpenLocationCode.calculateDistanceInMetersTo(other: OpenLocationCode): Double =
    OpenLocationCodeDistance.calculateDistanceInMeters(this, other)

/**
 * Calculate distance between this Open Location Code and another in miles
 */
fun OpenLocationCode.calculateDistanceInMilesTo(other: OpenLocationCode): Double =
    OpenLocationCodeDistance.calculateDistanceInMiles(this, other)