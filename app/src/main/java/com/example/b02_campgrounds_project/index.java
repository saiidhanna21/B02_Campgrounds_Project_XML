package com.example.b02_campgrounds_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class index extends AppCompatActivity {

    TextView welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        welcome = (TextView) this.findViewById(R.id.welcome);
        Intent intent = getIntent();
        String message = intent.getStringExtra("username");
        if(intent.hasExtra("source") && intent.getStringExtra("source").equals("login")){
            welcome.setText("Welcome back "+message);
        }else{
            welcome.setText("Welcome "+message);
        }
    }
    public void book(View v){
        Intent intent = new Intent(index.this,BookCamp.class);
        startActivity(intent);
    }
}