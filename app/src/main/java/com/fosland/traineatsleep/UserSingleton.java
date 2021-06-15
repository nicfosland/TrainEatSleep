package com.fosland.traineatsleep;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.firestore.auth.User;

public class UserSingleton {
    private static GoogleSignInClient mGoogleSignInClient;
    private static GoogleSignInAccount mGoogleSignInAccount;
    private static UserSingleton single_instance = null;

    private UserSingleton(GoogleSignInClient googleSignInClient){
        this.mGoogleSignInClient = googleSignInClient;
    }

    public static UserSingleton getInstance(){
        return single_instance;
    }

    public static GoogleSignInClient getGoogleSignInClient(){
        return mGoogleSignInClient;
    }

    public static GoogleSignInAccount getGoogleSignInAccount() {
        return mGoogleSignInAccount;
    }

    public static void setGoogleSignInAccount(GoogleSignInAccount mGoogleSignInAccount) {
        UserSingleton.mGoogleSignInAccount = mGoogleSignInAccount;
    }

    public static UserSingleton createInstance(GoogleSignInClient googleSignInClient){
        if(single_instance == null){
            single_instance = new UserSingleton(googleSignInClient);
        }
        return single_instance;
    }




}
