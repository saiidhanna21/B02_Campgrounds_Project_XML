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

public class getImages extends AppCompatActivity {
    private final String url = "http://192.168.1.105/CampProject/getImages.php";
    private int campId;
    private String username,source="";
    private JSONArray images;
    private ArrayList<String> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_images);

        Bundle extra =getIntent().getExtras();
        campId = extra.getInt("CampId");
        username = extra.getString("username");
        if(extra.containsKey("source")&& extra.get("source").equals("login"))
            source="source";

        result = new ArrayList<String>();

        getImages();
    }

    public void getImages(){
        String sendId = "?id="+campId;
        class dbclass extends AsyncTask<String,Void,String> {
            protected void onPostExecute(String data){
                try{

                    JSONObject jo = new JSONObject(data);
                    images = jo.getJSONArray("images");
                    for(int i=0 ; i<images.length();i++){

                        String answer = images.getString(i);
                        result.add(answer);

                    }

                    Intent intent = new Intent(getImages.this , showCamp.class);
                    intent.putExtra("CampId" , campId);
                    intent.putExtra("username"  , username);
                    intent.putExtra("images" , result);
                    if(!Objects.equals(source, "")){
                        intent.putExtra("source","login");
                    }
                    startActivity(intent);





                }catch (Exception e){
                    e.getMessage();
                }
            }
            @Override
            protected String doInBackground(String... strings) {
                try{
                    URL url1 = new URL(strings[0]);
                    HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer data = new StringBuffer();
                    String line;

                    while((line=br.readLine()) != null){
                        data.append(line+"\n");
                    }
                    br.close();
                    return data.toString();
                }catch(Exception e){
                    return e.getMessage();
                }
            }


        }
        dbclass obj = new dbclass();
        obj.execute(url+sendId);


    }
}