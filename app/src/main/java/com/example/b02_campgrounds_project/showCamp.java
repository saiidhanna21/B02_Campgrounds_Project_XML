package com.example.b02_campgrounds_project;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class showCamp extends AppCompatActivity {
    private TextView campRating , campTitle , campPrice , campLocation , campOwner , campDescription;
    private int campId , userId;
    private String username,source="";

    private LinearLayout displayImages;
    private ArrayList<String> images ;

    private final String url = "http:/192.168.1.105/CampProject/ShowCampground.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_camp);

        campRating = (TextView) findViewById(R.id.campRating);
        campTitle = (TextView) findViewById(R.id.campTitle);
        campPrice = (TextView) findViewById(R.id.campPrice);
        campLocation = (TextView) findViewById(R.id.campLocation);
        campOwner = (TextView) findViewById(R.id.campOwner);
        campDescription = (TextView) findViewById(R.id.campDescription);
        displayImages =(LinearLayout) findViewById(R.id.displayImages);


        Bundle extra =getIntent().getExtras();
        campId = extra.getInt("CampId");
        username = extra.getString("username");
        images = (ArrayList<String>)extra.getSerializable("images");
        if(extra.containsKey("source")&& extra.get("source").equals("login"))
            source="source";

        for(int i=0; i<images.size();i++){
            ImageView v= new ImageView(this);
            v.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            // or ViewGroup.LayoutParams.WRAP_CONTENT
                            1080,
                            // or ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.MATCH_PARENT ) );
            v.setScaleType(ImageView.ScaleType.FIT_XY);

            String url = "http://192.168.1.105/CampProject/assets/"+images.get(i);

            Glide.with(this).load(url)
                    .placeholder(R.drawable.reloading)
                    .error(R.drawable.reloading)
                    .into(v);
            displayImages.addView(v);
        }



        getCampground();




    }

    public void getCampground(){
        String sendId = "?id="+campId+"&username="+username;
        class dbclass extends AsyncTask<String,Void,String>{
            protected void onPostExecute(String data){
                try{

                    JSONObject jo = new JSONObject(data);
                    String title = jo.getString("title");
                    campTitle.setText(title);
                    double price = jo.getDouble("price");
                    String price1 = String.valueOf(price);
                    campPrice.setText(price1+"$");
                    String description = jo.getString("description");
                    campDescription.setText(description);
                    String location = jo.getString("location");
                    campLocation.setText(location);
                    double rating = jo.getDouble("rating");
                    String rating1 = String.valueOf(rating);
                    campRating.setText(rating1);
                    String owner = jo.getString("ownerUsername");
                    campOwner.setText(owner);

                    //get user id
                    userId = jo.getInt("userId");



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

    public void book(View v){
        Intent intent = new Intent(showCamp.this , BookCamp.class);
        intent.putExtra("CampId" , campId);
        intent.putExtra("username"  , username);
        intent.putExtra("UserId",userId);
        if(!Objects.equals(source, "")){
            intent.putExtra("source","login");
        }
        startActivity(intent);
    }

    public void back(View v){
        Intent intent = new Intent(showCamp.this , getDbCamp.class);
        intent.putExtra("username"  , username);
        if(!Objects.equals(source, "")){
            intent.putExtra("source","login");
        }
        startActivity(intent);
    }
}