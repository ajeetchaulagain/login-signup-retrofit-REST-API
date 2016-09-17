package com.prologic.loginandsignup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUserName;
    private EditText editTextPassword;
    private EditText editTextLevel;
    private EditText editTextFaculty;
    private EditText editTextEmail;
    private EditText editTextYear;
    private Button signUp;

    private static final String ROOT_URL = "http://192.168.10.8/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextLevel = (EditText) findViewById(R.id.editTextLevel);
        editTextFaculty = (EditText) findViewById(R.id.editTextFaculty);
        editTextYear = (EditText) findViewById(R.id.editTextYear);


        signUp = (Button) findViewById(R.id.signUp);

        signUp.setOnClickListener(this);

    }


    private void insertUser() {
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        SignUpAPI api = adapter.create(SignUpAPI.class);

        //Defining the method insertuser of our interface
        api.insertUser(

                //Passing the values by getting it from editTexts

                editTextUserName.getText().toString(),
                editTextEmail.getText().toString(),
                editTextLevel.getText().toString(),
                editTextFaculty.getText().toString(),
                editTextYear.getText().toString(),
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
//                        Toast.makeText(SignUpActivity.this, output, Toast.LENGTH_LONG).show();
                        Log.d("Check", output);

                        if (output.equals("successfully registered")) {
                            Toast.makeText(SignUpActivity.this, output, Toast.LENGTH_LONG).show();
                            Intent loginIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                            SignUpActivity.this.startActivity(loginIntent);
                        }
                        else{
                            Toast.makeText(SignUpActivity.this, output, Toast.LENGTH_LONG).show();
                         }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(SignUpActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );

    }


    private void checkEmpty(EditText editText) {
        String editTextValue = editText.getText().toString();
        if (editTextValue.isEmpty()) {
            editText.setError("Required");
        } else {
            editText.setError(null);
        }
    }

    private final static void isValidEmail(EditText editText) {
        String editTextValue = editText.getText().toString();
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(editTextValue).matches()) {
            editText.setError(null);
        } else {
            editText.setError("Email is invalid");
        }
    }

    @Override
    public void onClick(View view) {

        checkEmpty(editTextUserName);
        checkEmpty(editTextEmail);
        checkEmpty(editTextFaculty);
        checkEmpty(editTextLevel);
        checkEmpty(editTextYear);
        checkEmpty(editTextPassword);
        isValidEmail(editTextEmail);
        insertUser();
    }
}
