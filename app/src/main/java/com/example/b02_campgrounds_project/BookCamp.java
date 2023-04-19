package com.example.b02_campgrounds_project;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class BookCamp extends Activity {
    Spinner startDate, endDate;
    EditText numberOfPersons,phoneNumber;
    Double price,totalPrice;
    TextView price_text;
    ArrayList<String> dates = new ArrayList<String>();


    private final String url = "http://192.168.1.105/CampProject/booking.php";
    private final String url2 = "http://192.168.1.105/CampProject/book.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_camp);
        startDate = (Spinner) this.findViewById(R.id.startDate);
        endDate = (Spinner) this.findViewById(R.id.endDate);
        numberOfPersons = (EditText) this.findViewById(R.id.numberOfPersons);
        phoneNumber = (EditText) this.findViewById(R.id.phoneNumber);
        price_text = (TextView) this.findViewById(R.id.price);

        class dbManager extends AsyncTask<String,Void,String> {

            protected void onPostExecute(String data){
                try{
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = new JSONObject();
                    for(int i=0 ; i<ja.length();i++){
                        jo = ja.getJSONObject(i);
                        String date = jo.getString("date");
                        dates.add(date);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, dates);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    startDate.setAdapter(adapter);
                    endDate.setAdapter(adapter);
                    JSONObject jo1 = new JSONObject();
                    jo1 = ja.getJSONObject(0);
                    price = Double.valueOf(jo1.getString("price"));

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
        dbManager obj = new dbManager();
        obj.execute(url);

        startDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                date_calculator();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // code to be executed when no spinner item is selected
            }
        });

        endDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                date_calculator();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // code to be executed when no spinner item is selected
            }
        });

        numberOfPersons.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // code to be executed before text is changed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // code to be executed when text is changed
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // code to be executed after text is changed
                date_calculator();
            }
        });
    }

    public void book(View v) throws ParseException {
        String startDate_text,endDate_text,numberOfPersons_text,phoneNumber_text;
        startDate_text = startDate.getSelectedItem().toString();
        endDate_text = endDate.getSelectedItem().toString();
        numberOfPersons_text = numberOfPersons.getText().toString();
        phoneNumber_text = phoneNumber.getText().toString();

        Integer daysDiffValue = -1;

        if(numberOfPersons_text.isEmpty() || phoneNumber_text.isEmpty()) {
            Toast toast = Toast.makeText(this, "Please fill all the field", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        daysDiffValue = date_calculator();
        if(daysDiffValue<=0){
            Toast toast = Toast.makeText(this,"End date should be greater than Start Date",Toast.LENGTH_LONG);
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
                        String data = "start_date=" + URLEncoder.encode(strings[1], "UTF-8")
                                + "&end_date=" + URLEncoder.encode(strings[2], "UTF-8")
                                + "&numberOfPersons=" + URLEncoder.encode(strings[3], "UTF-8")
                                + "&phoneNumber=" + URLEncoder.encode(strings[4], "UTF-8")
                                + "&totalPrice=" + URLEncoder.encode(strings[5], "UTF-8")
                                + "&campground_id=" + URLEncoder.encode(strings[6], "UTF-8")
                                + "&daysDiff=" + URLEncoder.encode(strings[7], "UTF-8");
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
                        }else if(response.equals("notAvailable")){
                            Toast toast = Toast.makeText(getApplicationContext(),"There is "+j.getString("available_camps") +" Camps available in "+j.getString("date"),Toast.LENGTH_LONG);
                            toast.show();
                        }else{
                            Intent intent = new Intent(BookCamp.this,index.class);
                            //intent.putExtra("username",j.getString("username"));
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            DatabaseInsertionTask task = new DatabaseInsertionTask();
            task.execute(url2, startDate_text,endDate_text,numberOfPersons_text,phoneNumber_text, String.valueOf(totalPrice), String.valueOf(69),String.valueOf(daysDiffValue));
        }
    }

    public void back_cancel(View v){
        super.onBackPressed();
    }

    public Integer date_calculator(){
        Integer daysDiff=-1;
        DateTimeFormatter formatter = null;

        if(!numberOfPersons.getText().toString().isEmpty()){

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date1 = LocalDate.parse(startDate.getSelectedItem().toString(), formatter);
                LocalDate date2 = LocalDate.parse(endDate.getSelectedItem().toString(), formatter);
                long daysDiff_long = ChronoUnit.DAYS.between(date1, date2);
                daysDiff = Integer.valueOf(String.valueOf(daysDiff_long)) + 1;
            }
            if(daysDiff>0){
                totalPrice = price*daysDiff*Integer.valueOf(numberOfPersons.getText().toString());
                price_text.setText("$"+totalPrice);
            }
        }else{
            price_text.setText("Total Price");
        }
        return daysDiff;
    }
}