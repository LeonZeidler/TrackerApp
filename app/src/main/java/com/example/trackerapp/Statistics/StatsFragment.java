package com.example.trackerapp.Statistics;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trackerapp.R;

import java.util.Timer;
import java.util.TimerTask;


public class StatsFragment extends Fragment {

  private  TextView distancefield;
  Statistics stats=new StatisticCollector();
  private  TextView caloriesfield;

  public static StatsFragment newInstance(int steps, double calories,double distance){
    StatsFragment statsFragment= new StatsFragment();
    Bundle arguments = new Bundle();
    arguments.putInt("steps",steps);
    arguments.putDouble("calories",calories);
    arguments.putDouble("distance",distance);
    statsFragment.setArguments(arguments);
    return statsFragment;
  }

  @Override
  public void onResume() {
    super.onResume();

  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    distancefield= view.findViewById(R.id.DistanceId);
    caloriesfield=view.findViewById(R.id.CaloriesId);
    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        String dist = String.format("%s Km",Double.valueOf(stats.distanceInKm()));
        String  calo=String.format("%s Kcal.",Double.valueOf(stats.caloriesBurned()));
        distancefield.setText(dist);
        caloriesfield.setText(calo);
      }
    };
    timer.scheduleAtFixedRate(timerTask, 0, 2000);
  }

  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats, container, false);

    }
}