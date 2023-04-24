package com.example.b02_campgrounds_project;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class login extends AppCompatActivity {

    EditText username_email,password;
    private final String url = "http://192.168.1.105/CampProject/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username_email = (EditText) this.findViewById(R.id.username_email);
        password = (EditText) this.findViewById(R.id.password);
    }
    public void loginButton(View v){
        String username_email_text = username_email.getText().toString();
        String password_text = password.getText().toString();
        if(username_email_text.isEmpty() || password_text.isEmpty()){
            Toast toast = Toast.makeText(this,"Please fill all the field",Toast.LENGTH_LONG);
            toast.show();
        }else{
            class DatabaseInsertionTask extends AsyncTask<String, Void, String> {
                @Override
                protected String doInBackground(String... strings) {
                    try {
                        URL url = new URL(strings[0]);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setDoOutput(true);
                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                        String data = "username_OR_email=" + URLEncoder.encode(strings[1], "UTF-8") + "&password=" + URLEncoder.encode(strings[2], "UTF-8");
                        OutputStream os = conn.getOutputStream();
                        os.write(data.getBytes());
                        os.flush();
                        os.close();

                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String response = br.readLine();
                        br.close();
                        return response;
                    } catch (Exception e) {
                        return "Error: " + e.getMessage();
                    }
                }
                @Override
                protected void onPostExecute(String result) {
                    try {
                        JSONObject j = new JSONObject(result);
                        String response = j.getString("response");
                        if(response.equals("Error")){
                            Toast toast = Toast.makeText(getApplicationContext(), "Error, while connecting to the database", Toast.LENGTH_LONG);
                            toast.show();
                        }else if(response.equals("Failure")){
                            Toast toast = Toast.makeText(getApplicationContext(),"Username/email or Password incorrect! Try Again",Toast.LENGTH_LONG);
                            toast.show();
                        }else{
                            Intent intent = new Intent(login.this,getDbCamp.class);
                            intent.putExtra("username",j.getString("username"));
                            intent.putExtra("source","login");
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            DatabaseInsertionTask task = new DatabaseInsertionTask();
            task.execute(url, username_email_text, password_text);
        }
    }
    public void signup(View v){
        Intent intent = new Intent(login.this,signup.class);
        startActivity(intent);
    }
    public void back(View v){
        super.onBackPressed();
    }
}