package com.fosland.traineatsleep;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

public class UseWorkoutFragment extends Fragment {
    Map<String, Object> workoutToLoad;
    Object actualWorkoutMap;
    TextView textView;
    String workoutName;

    public UseWorkoutFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_use_workout, container, false);
        assert getArguments() != null;
        workoutToLoad = (Map<String, Object>) getArguments().getSerializable("workoutToLoad");
        for (Map.Entry<String, Object> entry : workoutToLoad.entrySet()) {
            if (!entry.getKey().equals("id") && !entry.getKey().equals("workoutId")) {
                workoutName = entry.getKey();
                actualWorkoutMap = entry.getValue();
            }
        }
        textView = view.findViewById(R.id.placeholder);
        try {
            textView.setText(workoutName);
        } catch (NullPointerException ex) {}


        return view;
    }
}