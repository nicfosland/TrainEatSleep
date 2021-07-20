package com.fosland.traineatsleep;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class CreateWorkoutFragment extends Fragment {
    LinearLayout exerciseContainer;
    HashMap<String, Object> workoutDoc = new HashMap<>();
    HashMap<String, Object> workout = new HashMap<>();
    HashMap<Object, String> previousNames = new HashMap<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_create_workout, container, false);
        // Inflate the layout for this fragment


        exerciseContainer = view.findViewById(R.id.exerciseContainer);
        exerciseContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        workoutDoc.put("id", UserSingleton.getGoogleSignInAccount().getId());
        workoutDoc.put("workoutId", UUID.randomUUID().toString());
        String planNameIdentifier = UUID.randomUUID().toString();
        TextInputEditText workoutPlanName = view.findViewById(R.id.workoutPlanEditText);
        workoutPlanName.setImeOptions(EditorInfo.IME_ACTION_DONE);
        workoutPlanName.setSingleLine();
//        if (workoutPlanName.getText().toString() != "") {
        workoutPlanName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!workoutPlanName.getText().toString().isEmpty()) {
                    Log.d("checking named day", "onFocusChange: " + previousNames.get(v.getContext()));

                    if (previousNames.containsKey(planNameIdentifier)) {
                        workoutDoc.remove(previousNames.get(planNameIdentifier));
                        workoutDoc.put(workoutPlanName.getText().toString(), workout);
                        previousNames.put(planNameIdentifier, workoutPlanName.getText().toString());
                    } else {
                        workoutDoc.put(workoutPlanName.getText().toString(), workout);
                        previousNames.put(planNameIdentifier, workoutPlanName.getText().toString());
                    }
                }
            }
        });
//        }
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


//    private void buildWorkout() {
//        HashMap<String, Object> workout = new HashMap<>();
//        HashMap<String, Object> day = new HashMap<>();
//        HashMap<String, Object> exercise = new HashMap<>();
//        workoutDoc.put("Push Pull Legs", workout);
//
//        workout.put("Day 1", day);
//        day.put("Bench Press", exercise);
//        exercise.put("sets", 4);
//        exercise.put("reps", 6);
//
//        day.put("Pullups", exercise);
//
//        db.collection("workout-programs").add(workoutDoc);
//    }

    private void saveWorkout() {
        Log.d("workout hashmap so far", "saveWorkout: " + workoutDoc);
        try {
            db.collection("workout-programs").add(workoutDoc);
        } catch (IllegalArgumentException ex) {
            Log.d("IllegalArgumentException", "saveWorkout: " + ex);
        }
    }

    @org.jetbrains.annotations.NotNull
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
        HashMap<String, Object> day = new HashMap<>();
        String dayNameIdentifier = UUID.randomUUID().toString();
        MaterialCardView newCard = new MaterialCardView(view.getContext());
        newCard.setRadius(50.0f);
        newCard.setBackgroundResource(R.color.red_700);

        //Main container within the cardview
        LinearLayout verticalLayout = new LinearLayout(newCard.getContext());
        verticalLayout.setOrientation(LinearLayout.VERTICAL);
        verticalLayout.setPadding(20, 20, 20, 20);

        //EditText layout container
        TextInputLayout textInputLayout =
                new TextInputLayout(new ContextThemeWrapper(verticalLayout.getContext(),
                        R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox));
        textInputLayout.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red_700)));
        textInputLayout.setBoxStrokeColor(getResources().getColor(R.color.red_700));


        //EditText within TextInputLayout
        TextInputEditText dayName = new TextInputEditText(textInputLayout.getContext());

        dayName.setSingleLine();
        dayName.setImeOptions(EditorInfo.IME_ACTION_DONE);
        textInputLayout.addView(dayName);
        dayName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!dayName.getText().toString().isEmpty()) {
//                    workout.put(dayName.getText().toString(), day);
                    if (previousNames.containsKey(dayNameIdentifier)) {
                        workout.remove(previousNames.get(dayNameIdentifier));
                        workout.put(dayName.getText().toString(), day);
                        previousNames.put(dayNameIdentifier, dayName.getText().toString());
                    } else {
                        workout.put(dayName.getText().toString(), day);
                        previousNames.put(dayNameIdentifier, dayName.getText().toString());
                    }
                }
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
                makeNewExerciseField(verticalLayout, day);
                verticalLayout.addView(addExercise);
            }
        });
        verticalLayout.addView(addExercise);
        newCard.addView(verticalLayout);
        return newCard;
    }

    private void makeNewExerciseField(LinearLayout verticalLayout, HashMap<String, Object> dayForExercise) {
        //needed to store and recreate workouts in database
        HashMap<String, Object> exercise = new HashMap<>();
        String exerciseNameIdentifer = UUID.randomUUID().toString();

        LinearLayout workoutStats = new LinearLayout(verticalLayout.getContext());
        workoutStats.setPadding(5, 5, 5, 5);
        workoutStats.setOrientation(LinearLayout.HORIZONTAL);
        workoutStats.setWeightSum(6f);

        EditText exerciseName = new EditText(workoutStats.getContext());
        exerciseName.setSingleLine();
        exerciseName.setImeOptions(EditorInfo.IME_ACTION_DONE);
        exerciseName.setLayoutParams(params(3f));
        exerciseName.setHint("Name");
        exerciseName.setTextColor(getResources().getColor(R.color.white));
        exerciseName.setHintTextColor(getResources().getColor(R.color.white));
        workoutStats.addView(exerciseName);
        exerciseName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d("exerciseName", "onFocusChange: ");
                if (!exerciseName.getText().toString().isEmpty()) {
//                    dayForExercise.put(exerciseName.getText().toString(), exercise);
                    if (previousNames.containsKey(exerciseNameIdentifer)) {
                        dayForExercise.remove(previousNames.get(exerciseNameIdentifer));
                        dayForExercise.put(exerciseName.getText().toString(), exercise);
                        previousNames.put(exerciseNameIdentifer, exerciseName.getText().toString());
                    } else {
                        dayForExercise.put(exerciseName.getText().toString(), exercise);
                        previousNames.put(exerciseNameIdentifer, exerciseName.getText().toString());
                    }

                }
            }
        });

        EditText exerciseSets = new EditText(workoutStats.getContext());
        exerciseSets.setSingleLine();
        exerciseSets.setImeOptions(EditorInfo.IME_ACTION_DONE);
        exerciseSets.setLayoutParams(params(1f));
        exerciseSets.setHint("Sets");
        exerciseSets.setTextColor(getResources().getColor(R.color.white));
        exerciseSets.setHintTextColor(getResources().getColor(R.color.white));
        workoutStats.addView(exerciseSets);
        exerciseSets.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                exercise.put("sets", exerciseSets.getText().toString());
                if (exerciseSets.getText().toString().isEmpty()) {
                    exercise.put("sets", "Not Set");
                } else {
                    exercise.put("sets", exerciseSets.getText().toString());
                }
            }
        });

        EditText exerciseReps = new EditText(workoutStats.getContext());
        exerciseReps.setSingleLine();
        exerciseReps.setImeOptions(EditorInfo.IME_ACTION_DONE);
        exerciseReps.setLayoutParams(params(1f));
        exerciseReps.setHint("Reps");
        exerciseReps.setTextColor(getResources().getColor(R.color.white));
        exerciseReps.setHintTextColor(getResources().getColor(R.color.white));
        workoutStats.addView(exerciseReps);
        exerciseReps.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (exerciseReps.getText().toString().isEmpty()) {
                    exercise.put("reps", "Not Set");
                } else {
                    exercise.put("reps", exerciseReps.getText().toString());
                }
            }
        });

        EditText exerciseWeight = new EditText(workoutStats.getContext());
        exerciseWeight.setLayoutParams(params(1f));
        exerciseWeight.setHint("Weight");
        exerciseWeight.setEnabled(false);
        exerciseWeight.setTextColor(getResources().getColor(R.color.white));
        exerciseWeight.setHintTextColor(getResources().getColor(R.color.white));
        workoutStats.addView(exerciseWeight);
        exerciseWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                exercise.put("weight", 0);
            }
        });

        verticalLayout.addView(workoutStats);
    }
}