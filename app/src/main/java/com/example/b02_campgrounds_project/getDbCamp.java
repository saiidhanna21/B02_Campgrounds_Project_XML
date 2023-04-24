package com.example.b02_campgrounds_project;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class getDbCamp extends AppCompatActivity {

    private final String url = "http://192.168.1.105/CampProject/index.php";
    ArrayList<String> getTitle, getDescription,getImages;
    ArrayList<Double> Pricer;
    ArrayList<Integer> getId;
    String getPrice;
    String username,source="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getTitle = new ArrayList<>();
        getId = new ArrayList<>();
        getDescription = new ArrayList<>();
        Pricer = new ArrayList<>();
        getImages=new ArrayList<>();

        Bundle user=getIntent().getExtras();
        if(user!=null) {
        username= (String) user.get("username");
        if(user.containsKey("source")){
            source="source";
        }}
        fetchdata();
    }

    public void fetchdata() {
        class dbManager extends AsyncTask<String, Void, String> {
            protected void onPostExecute(String data) {
                try {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = new JSONObject();
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        getId.add(jo.getInt("id"));
                        getTitle.add(jo.getString("title"));
                        getPrice = String.valueOf(jo.getDouble("price"));
                        Pricer.add(Double.valueOf(getPrice));
                        getDescription.add(jo.getString("description"));
                        getImages.add(jo.getString("images"));
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
                Bundle extra = new Bundle();
                extra.putSerializable("getTitle", getTitle);
                extra.putSerializable("Pricer", Pricer);
                extra.putSerializable("getId", getId);
                extra.putSerializable("getDescription", getDescription);
                extra.putSerializable("getImages", getImages);
                extra.putString("username",username);
                if(!Objects.equals(source, "")){
                    extra.putString("source","login");
                }
                Intent intent = new Intent(getBaseContext(), index.class);
                intent.putExtra("extra", extra);
                startActivity(intent);

            }

            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL url1 = new URL(strings[0]);

                    HttpURLConnection conn = (HttpURLConnection) url1.openConnection();

                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuffer data = new StringBuffer();
                    String line;

                    while ((line = br.readLine()) != null) {

                        data.append(line + "\n");
                    }
                    br.close();
                    return data.toString();
                } catch (Exception e) {

                    return e.getMessage();
                }
            }
        }
        dbManager obj = new dbManager();
        obj.execute(url);
    }
}
