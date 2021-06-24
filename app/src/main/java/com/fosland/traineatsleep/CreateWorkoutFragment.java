package com.fosland.traineatsleep;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class CreateWorkoutFragment extends Fragment {
    LinearLayout exerciseContainer;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_create_workout, container, false);
        // Inflate the layout for this fragment
        exerciseContainer = view.findViewById(R.id.exerciseContainer);
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
    }

    private void updateView(){

    }

    private ViewGroup.LayoutParams params(float weight){
        ViewGroup.LayoutParams param = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                weight
        );
        return param;
    }

    private MaterialCardView makeNewDayCard(View view){

        //Card that contains many layouts
        MaterialCardView newCard = new MaterialCardView(view.getContext());
        newCard.setRadius(50.0f);

        newCard.setBackgroundResource(R.color.red_700);

        //Main container within the cardview
        LinearLayout verticalLayout = new LinearLayout(newCard.getContext());
        verticalLayout.setOrientation(LinearLayout.VERTICAL);
        verticalLayout.setPadding(20,20,20,20);

        //horizontal container within the vertical main container
        LinearLayout workoutStatsLayout = new LinearLayout(verticalLayout.getContext(),);
        workoutStatsLayout.setPadding(5,5,5,5);
        workoutStatsLayout.setOrientation(LinearLayout.HORIZONTAL);
        workoutStatsLayout.setWeightSum(2f);

        Button testButton = new Button(verticalLayout.getContext());
        testButton.setText("Test");
        workoutStatsLayout.addView(testButton, params(1.5f));

        //EditText layout container
        TextInputLayout textInputLayout = new TextInputLayout(new ContextThemeWrapper(verticalLayout.getContext(),R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox));
        textInputLayout.setHint("Day Name");
        textInputLayout.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red_700)));
        textInputLayout.setBoxStrokeColor(getResources().getColor(R.color.red_700));

        //EditText within TextInputLayout
        TextInputEditText textInputEditText = new TextInputEditText(textInputLayout.getContext());
        textInputLayout.addView(textInputEditText);
        workoutStatsLayout.addView(textInputLayout, params(0.5f));
        verticalLayout.addView(workoutStatsLayout);

        //Add Exercise Button
        Button addExercise = new Button(verticalLayout.getContext());
        addExercise.setText("New Exercise");
        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("newExercise", "onClick: clicked.");
            }
        });
        verticalLayout.addView(addExercise);
        newCard.addView(verticalLayout);


        return newCard;
    }

    private CardView makeNewExerciseCard(View view) {
        CardView newCard = new CardView(view.getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        newCard.setLayoutParams(params);
        newCard.setBackgroundResource(R.color.red_700);
        EditText text = new EditText(view.getContext());
        text.setText("Card #");
        newCard.addView(text);
        return newCard;
    }
}