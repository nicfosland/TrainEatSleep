package com.fosland.traineatsleep;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class CreateWorkoutFragment extends Fragment {
    LinearLayout exerciseContainer;
    HashMap<String, Object> workoutObject = new HashMap<>();
    HashMap<String, Object> dayObject = new HashMap<>();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_create_workout, container, false);
        // Inflate the layout for this fragment
        exerciseContainer = view.findViewById(R.id.exerciseContainer);
        workoutObject.put("creatorId", UserSingleton.getGoogleSignInAccount().getId());
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.newDayButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exerciseContainer.addView(makeNewDayCard(getView()));
            }
        });

        view.findViewById(R.id.saveWorkoutButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWorkout();
            }
        });
    }
    private void saveWorkout(){
        workoutObject.put("day", dayObject);
        Log.d("workoutObject", "hashmap so far: " + workoutObject.toString());

    }

    private void updateView() {

    }

    private ViewGroup.LayoutParams params(float weight) {
        ViewGroup.LayoutParams param = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.MATCH_PARENT,
                weight
        );
        return param;
    }

    private MaterialCardView makeNewDayCard(View view) {
        //Card that contains many layouts
        MaterialCardView newCard = new MaterialCardView(view.getContext());
        newCard.setRadius(50.0f);

        newCard.setBackgroundResource(R.color.red_700);

        //Main container within the cardview
        LinearLayout verticalLayout = new LinearLayout(newCard.getContext());
        verticalLayout.setOrientation(LinearLayout.VERTICAL);
        verticalLayout.setPadding(20, 20, 20, 20);

        //EditText layout container
        TextInputLayout textInputLayout = new TextInputLayout(new ContextThemeWrapper(verticalLayout.getContext(), R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox));
        textInputLayout.setHint("Day Name");
        textInputLayout.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red_700)));
        textInputLayout.setBoxStrokeColor(getResources().getColor(R.color.red_700));


        //EditText within TextInputLayout
        TextInputEditText textInputEditText = new TextInputEditText(textInputLayout.getContext());
        textInputLayout.addView(textInputEditText);
        dayObject.put("dayName", "");
        textInputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                dayObject.replace("dayName", textInputEditText.getText());
                Log.d("dayName", "onFocusChange: " + dayObject.get("dayName"));
            }
        });
        verticalLayout.addView(textInputLayout);

        //Add Exercise Button
        Button addExercise = new Button(verticalLayout.getContext());
        addExercise.setText("New Exercise");
        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("newExercise", "onClick: clicked.");
                verticalLayout.removeView(addExercise);
                dayObject.put("exercise", makeNewExerciseField(verticalLayout));
                verticalLayout.addView(addExercise);
            }
        });
        verticalLayout.addView(addExercise);
        newCard.addView(verticalLayout);


        return newCard;
    }

    private HashMap<String, Object> makeNewExerciseField(LinearLayout verticalLayout){
        //needed to store and recreate workouts in database
        HashMap<String, Object> exerciseObject = new HashMap<>();
        LinearLayout workoutStats = new LinearLayout(verticalLayout.getContext());
        workoutStats.setPadding(5, 5, 5, 5);
        workoutStats.setOrientation(LinearLayout.HORIZONTAL);
        workoutStats.setWeightSum(6f);

        EditText exerciseName = new EditText(workoutStats.getContext());
        exerciseName.setLayoutParams(params(3f));
        exerciseName.setHint("Name");
        workoutStats.addView(exerciseName);
        exerciseObject.put("Name", exerciseName.getText());

        EditText exerciseSets = new EditText(workoutStats.getContext());
        exerciseSets.setLayoutParams(params(1f));
        exerciseSets.setHint("Sets");
        workoutStats.addView(exerciseSets);
        exerciseObject.put("Sets", exerciseSets.getText());

        EditText exerciseReps = new EditText(workoutStats.getContext());
        exerciseReps.setLayoutParams(params(1f));
        exerciseReps.setHint("Reps");
        workoutStats.addView(exerciseReps);
        exerciseObject.put("Reps", exerciseReps.getText());

        EditText exerciseWeight = new EditText(workoutStats.getContext());
        exerciseWeight.setLayoutParams(params(1f));
        exerciseWeight.setHint("Weight");
        exerciseWeight.setEnabled(false);
        workoutStats.addView(exerciseWeight);
        exerciseObject.put("Weight", exerciseWeight.getText());

        verticalLayout.addView(workoutStats);
        return exerciseObject;
    }
}