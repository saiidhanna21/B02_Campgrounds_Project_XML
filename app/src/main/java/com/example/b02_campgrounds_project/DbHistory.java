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

public class DbHistory extends AppCompatActivity {
    private String username , source ="", userId,getMyPrice;
    private JSONArray json_cancelFLag, json_bookingId, json_image , json_title , json_startdate , json_enddata , json_nbpersons , json_totalprice;
    public ArrayList<String> booking_id ,image , title , start_date , end_date , nb_persons , total_price,getMyTitle, getMyDescription,getMyImages;
    public ArrayList<Integer> cancelFlag,getMyId;

    private JSONArray json_MyId, json_MyImage , json_MyTitle , json_Myprice,json_MyDesc;
    public ArrayList<Double> MyPricer;
    private final String url = "http://192.168.1.105/CampProject/bookingHistory.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_history);


        Bundle extra = getIntent().getExtras();
        username = extra.getString("username");
        if (extra.containsKey("source") && extra.get("source").equals("login"))
            source = "source";

        booking_id = new ArrayList<String>();
        cancelFlag = new ArrayList<Integer>();
        image = new ArrayList<String>();
        title = new ArrayList<String>();
        start_date = new ArrayList<String>();
        end_date = new ArrayList<String>();
        nb_persons = new ArrayList<String>();
        total_price = new ArrayList<String>();
        getMyTitle = new ArrayList<>();
        getMyId = new ArrayList<>();
        getMyDescription = new ArrayList<>();
        MyPricer = new ArrayList<>();
        getMyImages=new ArrayList<>();

        String sendUsername = "?username=" + username;
        class dbclass extends AsyncTask<String, Void, String> {
            protected void onPostExecute(String data) {
                try {


                    JSONObject jo = new JSONObject(data);
                    json_bookingId = jo.getJSONArray("booking_id");
                    json_image = jo.getJSONArray("image");
                    json_title = jo.getJSONArray("title");
                    json_startdate = jo.getJSONArray("start_date");
                    json_enddata = jo.getJSONArray("end_date");
                    json_nbpersons = jo.getJSONArray("numberOfPersons");
                    json_totalprice = jo.getJSONArray("total_price");
                    json_cancelFLag = jo.getJSONArray("cancelFLag");
                    userId = jo.getString("user_id");

                    //MY CAMPGROUNDS
                    json_MyId = jo.getJSONArray("MyCampId");
                    json_MyImage=jo.getJSONArray("MyCampImage");
                    json_MyDesc=jo.getJSONArray("MyCampDesc");
                    json_MyTitle=jo.getJSONArray("MyCampTitle");
                    json_Myprice=jo.getJSONArray("MyCampPrice");

                    for (int i = 0; i < json_title.length(); i++) {
                        cancelFlag.add(json_cancelFLag.getInt(i));
                        booking_id.add(json_bookingId.getString(i));
                        title.add(json_title.getString(i));
                        image.add(json_image.getString(i));
                        start_date.add(json_startdate.getString(i));
                        end_date.add(json_enddata.getString(i));
                        nb_persons.add(json_nbpersons.getString(i));
                        total_price.add(json_totalprice.getString(i));
                    }
                    //MY CAMPGROUNDS
                    for (int i = 0; i < json_MyTitle.length(); i++) {
                        getMyId.add(json_MyId.getInt(i));
                        getMyTitle.add(json_MyTitle.getString(i));
                        MyPricer.add(json_Myprice.getDouble(i));
                        getMyDescription.add(json_MyDesc.getString(i));
                        getMyImages.add(json_MyImage.getString(i));
                    }

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("booking_id", booking_id);
                    bundle.putSerializable("image", image);
                    bundle.putSerializable("title", title);
                    bundle.putSerializable("start_date", start_date);
                    bundle.putSerializable("end_date", end_date);
                    bundle.putSerializable("nb_persons", nb_persons);
                    bundle.putSerializable("total_price", total_price);
                    bundle.putSerializable("cancelFlag", cancelFlag);

                    //MY CAMPGROUNDS
                    bundle.putSerializable("getMyTitle", getMyTitle);
                    bundle.putSerializable("MyPricer", MyPricer);
                    bundle.putSerializable("getMyId", getMyId);
                    bundle.putSerializable("getMyDescription", getMyDescription);
                    bundle.putSerializable("getMyImages", getMyImages);

                    bundle.putString("username", username);
                    bundle.putString("user_id", userId);
                    if (!Objects.equals(source, "")) {
                        bundle.putString("source", "login");
                    }

                    Intent intent = new Intent(DbHistory.this, Profile.class);
                    intent.putExtra("bundle", bundle);
                    startActivity(intent);


                } catch (Exception e) {
                    e.getMessage();
                }
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
        dbclass obj = new dbclass();
        obj.execute(url + sendUsername);
    }

}