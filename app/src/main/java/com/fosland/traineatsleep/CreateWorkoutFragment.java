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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreateWorkoutFragment extends Fragment {
    LinearLayout exerciseContainer;
    HashMap<String, Object> workoutObject = new HashMap<>();
    ArrayList<HashMap<String, Object>> daysToAddToWorkout = new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_create_workout, container, false);
        // Inflate the layout for this fragment
        exerciseContainer = view.findViewById(R.id.exerciseContainer);
        workoutObject.put("id", UserSingleton.getGoogleSignInAccount().getId());
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

    private void saveWorkout() {
        for(HashMap<String, Object> day: daysToAddToWorkout){
            workoutObject.put(day.get("dayId").toString(), day);
            Log.d("DayAdded", "saveWorkout: added day with id " + day.get("dayId"));
        }
        db.collection("workout-programs").add(workoutObject);
    }

    private ViewGroup.LayoutParams params(float weight) {
        ViewGroup.LayoutParams param = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                weight
        );
        return param;
    }

    private MaterialCardView makeNewDayCard(View view) {
        //Card that contains many layouts
        HashMap<String, Object> dayObject = new HashMap<String, Object>();
        dayObject.put("dayId", UUID.randomUUID());
        MaterialCardView newCard = new MaterialCardView(view.getContext());
        newCard.setRadius(50.0f);

        newCard.setBackgroundResource(R.color.red_700);

        //Main container within the cardview
        LinearLayout verticalLayout = new LinearLayout(newCard.getContext());
        verticalLayout.setOrientation(LinearLayout.VERTICAL);
        verticalLayout.setPadding(20, 20, 20, 20);

        //EditText layout container
        TextInputLayout textInputLayout = new TextInputLayout(new ContextThemeWrapper(verticalLayout.getContext(), R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox));
        textInputLayout.setHint("Day Name" + dayObject.get("dayId").toString());
        textInputLayout.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red_700)));
        textInputLayout.setBoxStrokeColor(getResources().getColor(R.color.red_700));


        //EditText within TextInputLayout
        TextInputEditText textInputEditText = new TextInputEditText(textInputLayout.getContext());
        textInputLayout.addView(textInputEditText);
        textInputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

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
                HashMap<String, Object> newExerciseField =
                        makeNewExerciseField(dayObject.get("dayId").toString(), verticalLayout);
                dayObject.put(newExerciseField.get("Name").toString(), newExerciseField);
                verticalLayout.addView(addExercise);
            }
        });
        verticalLayout.addView(addExercise);
        newCard.addView(verticalLayout);
        daysToAddToWorkout.add(dayObject);
        return newCard;
    }

    private HashMap<String, Object> makeNewExerciseField(String dayId, LinearLayout verticalLayout) {
        //needed to store and recreate workouts in database
        HashMap<String, Object> exerciseObject = new HashMap<>();
        exerciseObject.put("dayId", dayId);
        LinearLayout workoutStats = new LinearLayout(verticalLayout.getContext());
        workoutStats.setPadding(5, 5, 5, 5);
        workoutStats.setOrientation(LinearLayout.HORIZONTAL);
        workoutStats.setWeightSum(6f);

        EditText exerciseName = new EditText(workoutStats.getContext());
        exerciseName.setLayoutParams(params(3f));
        exerciseName.setHint("Name");
        exerciseName.setTextColor(getResources().getColor(R.color.white));
        exerciseName.setHintTextColor(getResources().getColor(R.color.white));
        workoutStats.addView(exerciseName);
        exerciseObject.put("Name", exerciseName.getText());
        exerciseName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                exerciseObject.put("Name", exerciseName.getText());
                Log.d("exerciseName", "onFocusChange: " + exerciseObject.get("Name").toString());
            }
        });

        EditText exerciseSets = new EditText(workoutStats.getContext());
        exerciseSets.setLayoutParams(params(1f));
        exerciseSets.setHint("Sets");
        exerciseSets.setTextColor(getResources().getColor(R.color.white));
        exerciseSets.setHintTextColor(getResources().getColor(R.color.white));
        workoutStats.addView(exerciseSets);
        exerciseObject.put("Sets", exerciseSets.getText());
        exerciseSets.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                exerciseObject.put("Sets", exerciseName.getText());
                Log.d("exerciseName", "onFocusChange: " + exerciseObject.get("Sets").toString());
            }
        });

        EditText exerciseReps = new EditText(workoutStats.getContext());
        exerciseReps.setLayoutParams(params(1f));
        exerciseReps.setHint("Reps");
        exerciseReps.setTextColor(getResources().getColor(R.color.white));
        exerciseReps.setHintTextColor(getResources().getColor(R.color.white));
        workoutStats.addView(exerciseReps);
        exerciseObject.put("Reps", exerciseReps.getText());
        exerciseReps.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                exerciseObject.put("Reps", exerciseName.getText());
                Log.d("exerciseName", "onFocusChange: " + exerciseObject.get("Reps").toString());
            }
        });

        EditText exerciseWeight = new EditText(workoutStats.getContext());
        exerciseWeight.setLayoutParams(params(1f));
        exerciseWeight.setHint("Weight");
        exerciseWeight.setEnabled(false);
        exerciseWeight.setTextColor(getResources().getColor(R.color.white));
        exerciseWeight.setHintTextColor(getResources().getColor(R.color.white));
        workoutStats.addView(exerciseWeight);
        exerciseObject.put("Weight", exerciseWeight.getText());
        exerciseWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                exerciseObject.put("Weight", exerciseName.getText());
                Log.d("exerciseName", "onFocusChange: " + exerciseObject.get("Weight").toString());
            }
        });

        verticalLayout.addView(workoutStats);

        return exerciseObject;
    }
}