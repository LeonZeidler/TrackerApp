package com.example.trackerapp.Steps;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import static com.example.trackerapp.Statistics.Storage.steps;

import com.example.trackerapp.R;
import com.example.trackerapp.Statistics.Storage;

import java.util.TimerTask;


public class StepsFragment extends Fragment implements SensorEventListener {
    private TextView stepsfield;
    private SensorManager sensorManager;
    boolean running;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stepsfield = view.findViewById(R.id.StepCountId);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

    }

    @Override
    public void onResume() {
        super.onResume();
        running=true;
        Sensor sSensor= sensorManager.getDefaultSensor((Sensor.TYPE_STEP_COUNTER));
        if(sSensor!=null){
            sensorManager.registerListener(this,sSensor,SensorManager.SENSOR_DELAY_UI);
        }else{
            stepsfield.setText("n.a.");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        running= false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_steps, container, false);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        steps = steps+1;
        stepsfield.setText(String.valueOf(steps));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}