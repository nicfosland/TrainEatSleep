package com.fosland.traineatsleep;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class FirstFragment extends Fragment {
    View view;
    TextView welcomeMessage;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first, container, false);

        welcomeMessage = view.findViewById(R.id.textview_first);

        //example adding a user to the database.
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first, middle, and last name
        Map<String, Object> user = new HashMap<>();
        if (UserSingleton.getGoogleSignInAccount() != null) {
            user.put("name", UserSingleton.getGoogleSignInAccount().getDisplayName());
            user.put("email", UserSingleton.getGoogleSignInAccount().getEmail());
            user.put("id", UserSingleton.getGoogleSignInAccount().getId());

            welcomeMessage.setText("Welcome, " + UserSingleton.getGoogleSignInAccount().getDisplayName());

        }

        Log.d("FirstFragment", "onCreateView: " + db.collection("users"));

// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("addUser", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("addUser", "Error adding document" + e, e);
                    }
                });
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("checkCollection", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("checkCollection", "Error getting documents.", task.getException());
                        }
                    }
                });
        // Inflate the layout for this fragment
        return view;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        view.findViewById(R.id.button_workouts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_WorkoutsFragment);
            }
        });
    }
}