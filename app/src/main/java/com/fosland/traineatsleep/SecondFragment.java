package com.fosland.traineatsleep;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class SecondFragment extends Fragment {
    //TODO: setup the xml file so that we can make a new exercise field with a method
    //TODO: make a method that creates a whole new exercise field
    //TODO: create a class that can serve as an object of an entire exercise field and include all the componenets within
    //TODO: make a list that contains all the exercises created
    private List<CardView> exerciseList = new ArrayList<>();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        view.findViewById(R.id.addNewExerciseCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout exerciseContainer = view.findViewById(R.id.ExerciseContainer);
                exerciseContainer.addView(makeNewExerciseCard(view));
            }
        });
    }

    private CardView makeNewExerciseCard(View view) {
        CardView newCard = new CardView(view.getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        newCard.setLayoutParams(params);
        newCard.setBackgroundResource(R.color.light_blue);
        EditText text = new EditText(view.getContext());
        text.setText("Card #");
        newCard.addView(text);
        return newCard;
    }
}