package com.prologic.loginandsignup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserInfoActivity extends AppCompatActivity {

    private TextView tvUserName;
    private TextView tvEmail;
    private TextView tvLevel;
    private TextView tvFaculty;
    private TextView tvYear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvEmail= (TextView) findViewById(R.id.tvEmail);
        tvLevel= (TextView) findViewById(R.id.tvLevel);
        tvFaculty = (TextView) findViewById(R.id.tvFaculty);
        tvYear = (TextView) findViewById(R.id.tvYear);



    }
}
