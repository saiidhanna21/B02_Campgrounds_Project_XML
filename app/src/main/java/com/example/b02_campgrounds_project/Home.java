package com.example.b02_campgrounds_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class Home extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    public void login(View v){
        Intent intent = new Intent(Home.this,login.class);
        startActivity(intent);
    }
    public void signup(View v){
        Intent intent = new Intent(Home.this,signup.class);
        startActivity(intent);
    }
}