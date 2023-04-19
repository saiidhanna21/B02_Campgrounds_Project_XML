package com.example.b02_campgrounds_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class signup extends AppCompatActivity {

    EditText firstName,lastName,username,email,password,confirmPassword;
    private final String url = "http://192.168.1.105/CampProject/signup.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firstName = (EditText) this.findViewById(R.id.firstName);
        lastName = (EditText) this.findViewById(R.id.lastName);
        username = (EditText) this.findViewById(R.id.username);
        email = (EditText) this.findViewById(R.id.email);
        password = (EditText) this.findViewById(R.id.password);
        confirmPassword = (EditText) this.findViewById(R.id.confirm_password);
    }
    public void login(View v){
        Intent intent = new Intent(signup.this,login.class);
        startActivity(intent);
    }
    public void signupButton(View v) {
        String password_text, confirmPassword_text, username_text, email_text, firstName_text, lasName_text;
        password_text = password.getText().toString();
        confirmPassword_text = confirmPassword.getText().toString();
        email_text = email.getText().toString();
        username_text = username.getText().toString();
        firstName_text = firstName.getText().toString();
        lasName_text = lastName.getText().toString();

        if (firstName_text.isEmpty() || lasName_text.isEmpty() || username_text.isEmpty() || email_text.isEmpty() || password_text.isEmpty()) {
            Toast toast = Toast.makeText(this, "Please fill all the field", Toast.LENGTH_LONG);
            toast.show();
        } else if (!password_text.equals(confirmPassword_text)) {
            Toast toast = Toast.makeText(this, "These passwords didn’t match. Try again", Toast.LENGTH_LONG);
            toast.show();
        } else {
            class DatabaseInsertionTask extends AsyncTask<String, Void, String> {

                @Override
                protected String doInBackground(String... strings) {
                    try {
                        URL url = new URL(strings[0]);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setDoOutput(true);
                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                        String data = "firstName=" + URLEncoder.encode(strings[1], "UTF-8") + "&lastName=" + URLEncoder.encode(strings[2], "UTF-8") + "&email=" + URLEncoder.encode(strings[3], "UTF-8") + "&username=" + URLEncoder.encode(strings[4], "UTF-8") + "&password=" + URLEncoder.encode(strings[5], "UTF-8");
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
                        }else if(response.equals("emailNotValid")){
                            Toast toast = Toast.makeText(getApplicationContext(), "Not a Valid Email", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        else if(response.equals("UsernameExists")){
                            Toast toast = Toast.makeText(getApplicationContext(), "Username already exists. Try again", Toast.LENGTH_LONG);
                            toast.show();
                        }else if(response.equals("EmailExists")){
                            Toast toast = Toast.makeText(getApplicationContext(), "Email already exists. Try again", Toast.LENGTH_LONG);
                            toast.show();
                        }else{
                            Intent intent = new Intent(signup.this, index.class);
                            intent.putExtra("username", username_text);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            DatabaseInsertionTask task = new DatabaseInsertionTask();
            task.execute(url, firstName_text, lasName_text, email_text, username_text, password_text);
        }
    }
    public void back(View v){
        super.onBackPressed();
    }


}