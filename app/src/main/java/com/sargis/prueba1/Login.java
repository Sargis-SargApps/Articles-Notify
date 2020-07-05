package com.sargis.prueba1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sargis.prueba1.model.Profile;
import com.sargis.prueba1.ui.login.LoginViewModel;

import java.util.Arrays;
import java.util.List;

public class Login extends AppCompatActivity {
    private int RC_SIGN_IN=100;
    private LoginViewModel loginViewModel;
    private  FirebaseUser user;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        loginViewModel=new ViewModelProvider(this).get(LoginViewModel.class);

        login();
    }

    public void login(){

        if(user!=null){
            FirebaseMessaging.getInstance().setAutoInitEnabled(true);
            loginViewModel.getProfile();
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
        }else{
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build());

            // Create and launch sign-in intent
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                user= FirebaseAuth.getInstance().getCurrentUser();
                if(response.isNewUser()) createNewUser();
                else login();

            } else {
                if(response==null){
                   Toast.makeText(getApplicationContext(),"cancelled",Toast.LENGTH_SHORT).show();
                    return;
                }else if(response.getError().getErrorCode()== ErrorCodes.NO_NETWORK){
                    Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_SHORT).show();
                }else if(response.getError().getErrorCode()==ErrorCodes.UNKNOWN_ERROR){
                    Toast.makeText(getApplicationContext(),"unknown error",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void createNewUser(){
        loginViewModel.createProfile(new Profile(user.getDisplayName(),user.getEmail(),null));
        login();
    }

}
