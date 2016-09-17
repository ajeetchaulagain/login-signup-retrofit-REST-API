package com.prologic.loginandsignup;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUserName;
    private EditText editTextPassword;
    private Button loginButton;
    private TextView tvSignUp;


    public static final String ROOT_URL = "http://192.168.10.8/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);


        loginButton = (Button) findViewById(R.id.loginButton);

        tvSignUp = (TextView) findViewById(R.id.tvSignUp);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    loginUser();
            }
        });


        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                LoginActivity.this.startActivity(signUpIntent);
            }
        });

    }

    private void loginUser() {
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        LoginAPI api = adapter.create(LoginAPI.class);

        //Defining the method insertuser of our interface
        api.loginUser(

                //Passing the values by getting it from editTexts

                editTextUserName.getText().toString(),
                editTextPassword.getText().toString(),


                //Creating an anonymous callback
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        //On success we will read the server's output using bufferedreader
                        //Creating a bufferedreader object
                        BufferedReader reader = null;

                        //An string to store output from the server
                        String output = "";

                        try {
                            //Initializing buffered reader
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                            //Reading the output in the string
                            output = reader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Displaying the output as a toast
//                        Toast.makeText(LoginActivity.this, output, Toast.LENGTH_LONG).show();
                        Log.d("Check", output);

                        if (output.equals("login successfull")) {
                            Toast.makeText(LoginActivity.this, output, Toast.LENGTH_LONG).show();
                            Intent userInfoIntent = new Intent(LoginActivity.this, UserInfoActivity.class);

                            userInfoIntent.putExtra(getString(R.string.key_username), (Parcelable) editTextUserName);
                            LoginActivity.this.startActivity(userInfoIntent);
                            Log.d("Check2:", output);
                        }

                        else{
                            Toast.makeText(LoginActivity.this, output, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}
