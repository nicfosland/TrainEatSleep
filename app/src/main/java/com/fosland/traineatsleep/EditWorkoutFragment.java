package com.fosland.traineatsleep;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditWorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditWorkoutFragment extends Fragment {

    public EditWorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_workout, container, false);



        return view;
    }
}