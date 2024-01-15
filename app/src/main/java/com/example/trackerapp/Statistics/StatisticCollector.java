package com.example.trackerapp.Statistics;


import static com.example.trackerapp.Statistics.Storage.steps;
import static com.example.trackerapp.Statistics.Storage.distance;
import static com.example.trackerapp.Statistics.Storage.locations;


public class StatisticCollector  implements Statistics {




    @Override
    public double distanceInKm() {
        return Math.round((Storage.distance/1000)*1000)/1000.0;

    }



    @Override
    public double caloriesBurned() {
        return Math.round((Storage.steps * 0.05)*1000)/1000.0;
    }

    @Override
    public void reset() {
        distance=0.0;
        locations.clear();
        steps = 0;

    }


}
