package com.colinfrerichs.crossref;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class RegisterActivity extends AppCompatActivity {

    private EditText mPasswordRegister;
    private EditText mConfirmPasswordRegister;
    private Button mSignUpButton;

    private static final String SHARED_PREF_FILENAME = "SECRETS";
    private static final String id = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Password fields and Sign up Button
        mPasswordRegister = (EditText) findViewById(R.id.createPasswordText);
        mConfirmPasswordRegister = (EditText) findViewById(R.id.confirmPasswordText);
        mSignUpButton = (Button) findViewById(R.id.registerButton);

        //Checks password fields and registers user
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = mPasswordRegister.getText().toString();
                String confirmPassword = mConfirmPasswordRegister.getText().toString();

                password = password.trim();
                confirmPassword = confirmPassword.trim();

                //Empty field or mismatching passwords
                if ((confirmPassword.isEmpty() || password.isEmpty()) && (!password.equals(confirmPassword))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage(R.string.sign_up_error_message);
                    builder.setTitle(R.string.sign_up_error_title);
                    builder.setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    //create the new user
                    final ParseUser newUser = new ParseUser();
                    newUser.setPassword(password);
                    newUser.setUsername("Word");
                    final String finalPassword = password;
                    newUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                //success
                                ParseUser.logInInBackground(newUser.getUsername(), finalPassword);
                                ParseUser currentUser = ParseUser.getCurrentUser();

                                currentUser.setUsername(newUser.getObjectId());
                                currentUser.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e != null) {
                                            //Success
                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            SaveUserData(newUser.getObjectId());
                                            newUser.setUsername(newUser.getObjectId());
                                            newUser.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {

                                                }
                                            });
                                            ParseUser.logOut();
                                            startActivity(intent);
                                        } else {
                                            //no success
                                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                            builder.setMessage(R.string.update_user_error);
                                            builder.setTitle(R.string.sign_up_error_title);
                                            builder.setPositiveButton(android.R.string.ok, null);
                                            AlertDialog dialog = builder.create();
                                            dialog.show();
                                        }
                                    }
                                });


                            } else {
                                //no success
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage(R.string.sign_up_error_parse);
                                builder.setTitle(R.string.sign_up_error_title);
                                builder.setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });
                }
            }
        });
    }

    //Saves the randomly generated username to the users Preferences folder
    private void SaveUserData(String user) {
        SharedPreferences mPref = getSharedPreferences(SHARED_PREF_FILENAME, MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mPref.edit();
        mEditor.clear();

        mEditor.putString(id, user);
        mEditor.commit();
    }

}