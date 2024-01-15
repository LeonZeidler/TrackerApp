package com.example.trackerapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;

import androidx.test.espresso.ViewAssertion;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;

import com.example.trackerapp.GUI.MapDrawer;
import com.example.trackerapp.Statistics.Storage;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MapDrawerTest {
    @Rule
    public ActivityTestRule<MapDrawer> activityTestRule = new ActivityTestRule<MapDrawer>(MapDrawer.class);
    @Test
    public void distanceDisplayedText() {
        Storage.distance=100;

        onView(withId(R.id.DistanceId)).check((ViewAssertion) withText("0.100 Km"));

    }
    @Test
    public void caloriesDisplayedText(){
        Storage.steps=100;

        onView(withId(R.id.CaloriesId)).check((ViewAssertion) withText("5.0 Kcal."));
    }
    @Test
    public void resetButtonTest(){
        Storage.distance=100;
        onView(withId(R.id.ResetButtonId)).perform(click());
        onView(withId(R.id.DistanceId)).check((ViewAssertion) withText("0.0 Km"));
        onView(withId(R.id.CaloriesId)).check((ViewAssertion) withText("0.0 Kcal."));
    }
}