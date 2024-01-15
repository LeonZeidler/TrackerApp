package com.example.trackerapp;

import org.junit.Test;


import static org.junit.Assert.*;

import com.example.trackerapp.Statistics.StatisticCollector;
import com.example.trackerapp.Statistics.Statistics;
import com.example.trackerapp.Statistics.Storage;
import com.google.android.gms.maps.model.LatLng;


public class StatisticCollectorTest {

    @Test
    public void calculateKMTest() {
        Storage.distance=1000;
        Statistics statistics= new StatisticCollector();
        double distanceKM= statistics.distanceInKm();
        assertEquals(1.0, distanceKM ,0);
    }
    @Test
    public void calculateKMTest1234555() {
        Storage.distance=12345.55;
        Statistics statistics= new StatisticCollector();
        double distanceKM= statistics.distanceInKm();
        assertEquals(12.346, distanceKM ,0);
    }
    @Test
    public void calculateCaloriesTest(){
        Storage.steps=100;
        Statistics statistics=new StatisticCollector();
        double caloriesBurned= statistics.caloriesBurned();
        assertEquals(5.0,caloriesBurned, 0);
    }
    @Test
    public void calculateCaloriesTest459(){
        Storage.steps=459;
        Statistics statistics=new StatisticCollector();
        double caloriesBurned= statistics.caloriesBurned();
        assertEquals(22.95,caloriesBurned, 0);
    }

    @Test
    public void resetTestDistance(){
        Storage.distance=1000;
        Statistics statistics= new StatisticCollector();
        statistics.reset();
        assertEquals(0,Storage.distance,0);
    }@Test
    public void resetTestSteps(){

        Storage.steps=100;
        Statistics statistics= new StatisticCollector();
        statistics.reset();
        assertEquals(0,Storage.steps,0);
    }@Test
    public void resetTestLocations(){
        LatLng latLng1= new LatLng(52,35);
        LatLng latLng2= new LatLng(35,52);
        Storage.locations.add(latLng1);
        Storage.locations.add(latLng2);
        Statistics statistics= new StatisticCollector();
        statistics.reset();
        assertTrue(Storage.locations.isEmpty());
    }
}