package com.example.trackerapp.Statistics;


/**
 * Interface for Statistic collector
 **/
public interface Statistics {
    /**
     * Method to return Distance traveled
     *
     * @return total distance traveled in KM
     **/
    double distanceInKm();

    /**
     * Method to return total calories burned
     *
     * @return total calories burned
     **/
    double caloriesBurned();

    /**
     * Method to reset all stats to 0
     **/
    void reset();
}
