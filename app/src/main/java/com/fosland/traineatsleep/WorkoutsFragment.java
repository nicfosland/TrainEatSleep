package com.fosland.traineatsleep;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.gridlayout.widget.GridLayout;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
            }
        });

        return view;
    }

    private Button makeWorkoutButton(QueryDocumentSnapshot documentSnapshot) {
        Button newButton = new Button(gridLayout.getContext());
        newButton.setWidth(gridLayout.getWidth() / 3);
        newButton.setHeight(gridLayout.getWidth() / 3);
        newButton.setHighlightColor(Color.RED);
        return newButton;
    }
}