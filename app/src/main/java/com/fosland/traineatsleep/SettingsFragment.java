package com.fosland.traineatsleep;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class SettingsFragment extends Fragment {
    View view;
    TextView loginMessage;
    Button signOutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        loginMessage = view.findViewById(R.id.loginMessage2);
        loginMessage.setText(UserSingleton.getGoogleSignInAccount().getDisplayName());

        signOutButton = view.findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSingleton.getGoogleSignInClient().signOut();
                UserSingleton.setGoogleSignInAccount(null);
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}