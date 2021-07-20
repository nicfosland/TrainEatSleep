package com.fosland.traineatsleep;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.gridlayout.widget.GridLayout;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class WorkoutsFragment extends Fragment {
    View view;
    GridLayout gridLayout;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_workouts, container, false);
        gridLayout = view.findViewById(R.id.gridLayout);
        db.collection("workout-programs")
                .whereEqualTo("id", UserSingleton.getGoogleSignInAccount().getId())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    gridLayout.addView(makeWorkoutButton(document));
                }
                gridLayout.addView(newWorkoutButton());
            }
        });

        return view;
    }

    private Button newWorkoutButton() {
        Button newButton = new Button(gridLayout.getContext());
        newButton.setWidth(gridLayout.getWidth() / 3);
        newButton.setHeight(gridLayout.getWidth() / 3);
        newButton.setText("+");
        newButton.setHighlightColor(Color.RED);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(WorkoutsFragment.this)
                        .navigate(R.id.action_WorkoutsFragment_to_CreateWorkoutFragment);
            }
        });
        return newButton;
    }

    private Button makeWorkoutButton(QueryDocumentSnapshot documentSnapshot) {
        Button newButton = new Button(gridLayout.getContext());
        String workoutName = "";
//        UseWorkoutFragment useWorkoutFragment = new UseWorkoutFragment(documentSnapshot);
        Map<String, Object> workoutProgramMap = documentSnapshot.getData();
        try{
            for(Map.Entry<String, Object> workoutProgramEntries : workoutProgramMap.entrySet()){
                Log.d("EntrySet", "makeWorkoutButton: " + workoutProgramEntries.getKey());
                if(!workoutProgramEntries.getKey().equals("id") ){
                    if (!workoutProgramEntries.getKey().equals("workoutId")){
                        workoutName = workoutProgramEntries.getKey();
                        break;
                    }
                }
            }
        } catch (NullPointerException ex){

        }
        if (!workoutName.equals("")){
            newButton.setText(workoutName);
        }
        newButton.setWidth(gridLayout.getWidth() / 3);
        newButton.setHeight(gridLayout.getWidth() / 3);
        newButton.setHighlightColor(Color.RED);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putSerializable("workoutToLoad", (Serializable) workoutProgramMap);
                NavHostFragment.findNavController(WorkoutsFragment.this)
                        .navigate(R.id.action_WorkoutsFragment_to_useWorkoutFragment, data);
            }
        });
        return newButton;
    }

}