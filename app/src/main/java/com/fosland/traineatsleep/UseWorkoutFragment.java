package com.fosland.traineatsleep;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

public class UseWorkoutFragment extends Fragment {
    Map<String, Object> workoutToLoad;
    Object actualWorkoutMap;
    TextView workoutPlanContents;
    String workoutName = "";

    public UseWorkoutFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_use_workout, container, false);
        assert getArguments() != null;
        workoutToLoad = (Map<String, Object>) getArguments().getSerializable("workoutToLoad");
        //Load the current workout arguments from the WorkoutFragments fragment
        // into variables to use in this class.
        for (Map.Entry<String, Object> entry : workoutToLoad.entrySet()) {
            if (!entry.getKey().equals("id") && !entry.getKey().equals("workoutId")) {
                workoutName = entry.getKey();
                actualWorkoutMap = entry.getValue();
            }
        }
        //Set the action bar title to be the workout name.
        if (workoutName != "") {
            getActivity().setTitle(workoutName);
        }

        workoutPlanContents = view.findViewById(R.id.workoutPlanContents);
        if(actualWorkoutMap != null) {
            workoutPlanContents.setText(actualWorkoutMap.toString());
        }


        return view;
    }

    private void makeWorkoutLayout(){

    }
}