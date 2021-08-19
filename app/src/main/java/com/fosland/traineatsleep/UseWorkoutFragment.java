package com.fosland.traineatsleep;

import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class UseWorkoutFragment extends Fragment {
    Map<String, Object> workoutToLoad;
    Object workoutMap;
    LinearLayout workoutContainer;
    String workoutName = "";

    public UseWorkoutFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_use_workout, container, false);
        workoutContainer = view.findViewById(R.id.workoutContainer);
        assert getArguments() != null;
        workoutToLoad = (Map<String, Object>) getArguments().getSerializable("workoutToLoad");
        //Load the current workout arguments from the WorkoutFragments fragment
        // into variables to use in this class.
        for (Map.Entry<String, Object> entry : workoutToLoad.entrySet()) {
            if (!entry.getKey().equals("id") && !entry.getKey().equals("workoutId")) {
                workoutName = entry.getKey();
                workoutMap = entry.getValue();
            }
        }
        //Set the action bar title to be the workout name.
        if (workoutName != "") {
            getActivity().setTitle(workoutName);
        }

//        workoutPlanContents = view.findViewById(R.id.workoutPlanContents);
//        if(workoutMap != null) {
//            workoutPlanContents.setText(workoutMap.toString());
//        }
        if(workoutMap != null){
            Map<String, Object> dayMap = new HashMap<>();
            dayMap = (Map<String, Object>) workoutMap;
            for (Map.Entry<String, Object> entry : dayMap.entrySet()) {
//                if (!entry.getKey().equals("id") && !entry.getKey().equals("workoutId")) {
                    makeDay(entry.getKey(), entry.getValue());
//                }
            }
        } else {
            Log.d("UseWorkoutFragment", "onCreateView: workoutMap is empty!");
        }

        return view;
    }

    private void makeDay(String dayName, Object dayMap){
        CardView dayCard = new CardView(workoutContainer.getContext());
        dayCard.setCardBackgroundColor(Color.RED);
        LinearLayout cardLayout = new LinearLayout(dayCard.getContext());
        cardLayout.setOrientation(LinearLayout.VERTICAL);
        cardLayout.setPadding(5,5,5,5);
        dayCard.addView(cardLayout);
        TextView nameTextView = new TextView(cardLayout.getContext());
        cardLayout.addView(nameTextView);

        //Set name to edit text just created
        nameTextView.setText(dayName);
        nameTextView.setTextSize(24f);

        //add card to workout container
        workoutContainer.addView(dayCard);

        if(dayMap != null){
            Map<String, Object> exerciseMap = new HashMap<>();
            exerciseMap = (Map<String, Object>) dayMap;
            for (Map.Entry<String, Object> entry : exerciseMap.entrySet()) {
                makeExercise(cardLayout, entry.getKey(), entry.getValue());
            }
        } else {
            Log.d("UseWorkoutFragment", "onCreateView: dayMap is empty!");
        }

    }

    private void makeExercise(LinearLayout cardLayout, String exerciseName, Object exerciseMap){
        LinearLayout exerciseLayout = new LinearLayout(cardLayout.getContext());
        exerciseLayout.setOrientation(LinearLayout.HORIZONTAL);
        cardLayout.addView(exerciseLayout);
        Log.d("Map", "makeExercise: " + exerciseMap);
        Map<String, Object> exercise = new HashMap<>();
        exercise = (Map<String, Object>) exerciseMap;

        EditText name = new EditText(exerciseLayout.getContext());
        name.setText(exerciseName);
        exerciseLayout.addView(name);

        EditText sets = new EditText(exerciseLayout.getContext());
        sets.setHint("Sets");
        exerciseLayout.addView(sets);

        EditText reps = new EditText(exerciseLayout.getContext());
        reps.setHint("Reps");
        exerciseLayout.addView(reps);

        EditText weight = new EditText(exerciseLayout.getContext());
        weight.setHint("Weight");
        exerciseLayout.addView(weight);

        for(Map.Entry<String, Object> entry: exercise.entrySet()){
            if(entry.getKey().equals("sets")){
                sets.setText(entry.getValue().toString());
            } else if (entry.getKey().equals("reps")){
                reps.setText(entry.getValue().toString());
            } else if(entry.getKey().equals("weight")){
                weight.setText(entry.getValue().toString());
            }
        }


    }

}