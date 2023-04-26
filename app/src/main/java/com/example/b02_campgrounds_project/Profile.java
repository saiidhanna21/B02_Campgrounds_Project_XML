package com.example.b02_campgrounds_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class Profile extends AppCompatActivity {
    private ArrayList<String> getBookingId ,getImage , getTitle , getStartDate , getEndDate , getNbPersons , getTotalPrice;
    private ArrayList<Integer> getCancelFlag;
    private String userId , source, username;
    private GridLayout mother1,mother2;
    ArrayList<String> getMyTitle, getMyDescription,getMyImages;
    ArrayList<Double> MyPricer;
    ArrayList<Integer> getMyId;
    String MypriceValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Initialize arrays
        getBookingId = new ArrayList<>();
        getImage = new ArrayList<>();
        getTitle = new ArrayList<>();
        getStartDate = new ArrayList<>();
        getEndDate = new ArrayList<>();
        getNbPersons = new ArrayList<>();
        getTotalPrice = new ArrayList<>();
        getCancelFlag=new ArrayList<>();

        //MY CAMPGROUNDS
        getMyTitle = new ArrayList<>();
        getMyDescription = new ArrayList<>();
        MyPricer = new ArrayList<>();
        getMyId = new ArrayList<>();
        getMyImages = new ArrayList<>();

        //initialize mother1
        mother1 = (GridLayout) findViewById(R.id.mother1);
        mother2 = (GridLayout) findViewById(R.id.mother2);



        //get bookings information
        Bundle bundle = getIntent().getBundleExtra("bundle");
        getBookingId = (ArrayList<String>) bundle.getSerializable("booking_id");
        getImage = (ArrayList<String>) bundle.getSerializable("image");
        getTitle = (ArrayList<String>) bundle.getSerializable("title");
        getStartDate = (ArrayList<String>) bundle.getSerializable("start_date");
        getEndDate = (ArrayList<String>) bundle.getSerializable("end_date");
        getNbPersons = (ArrayList<String>) bundle.getSerializable("nb_persons");
        getTotalPrice = (ArrayList<String>) bundle.getSerializable("total_price");
        getCancelFlag = (ArrayList<Integer>) bundle.getSerializable("cancelFlag");

        //MY CAMPGROUNDS
        getMyTitle = (ArrayList<String>) bundle.getSerializable("getMyTitle");
        getMyDescription = (ArrayList<String>) bundle.getSerializable("getMyDescription");
        MyPricer = (ArrayList<Double>) bundle.getSerializable("MyPricer");
        getMyId = (ArrayList<Integer>) bundle.getSerializable("getMyId");
        getMyImages = (ArrayList<String>) bundle.getSerializable("getMyImages");

        userId = bundle.getString("user_id");
        username = bundle.getString("username");

        if(bundle.containsKey("source")&& bundle.get("source").equals("login"))
            source="source";




        //initialize views
        ArrayList<LinearLayout>arrayVertical=new ArrayList<>();
        ArrayList<LinearLayout>arrayBody=new ArrayList<>();
        ArrayList<View>arrayView=new ArrayList<>();
        ArrayList<TextView>arrayTitle=new ArrayList<>();
        ArrayList<TextView>arrayDuration=new ArrayList<>();
        ArrayList<TextView>arrayNbPersons=new ArrayList<>();
        ArrayList<LinearLayout>arrayLastLine = new ArrayList<>();
        ArrayList<TextView>arrayTotalPrice=new ArrayList<>();
        ArrayList<Button>arrayCancel = new ArrayList<>();

        //MY CAMPGROUNDS
        ArrayList<View> MyarrayView = new ArrayList<>();
        ArrayList<TextView> MyarrayPerNight = new ArrayList<>();
        ArrayList<TextView> MyarrayTitle = new ArrayList<>();
        ArrayList<TextView> MyarrayPrice = new ArrayList<>();
        ArrayList<LinearLayout> MyarrayPriceNight = new ArrayList<>();
        ArrayList<LinearLayout> MyarrayPriceGo = new ArrayList<>();
        ArrayList<LinearLayout> MyarrayBody = new ArrayList<>();
        ArrayList<TextView> MyarrayDescription = new ArrayList<>();
        ArrayList<LinearLayout> MyarrayVerti = new ArrayList<>();
        ArrayList<ImageButton> MyarrayImage = new ArrayList<>();


        if(getTitle.size()==0){
            TextView message = new TextView(this);
            message.setText("No Bookings Available!");
            message.setTypeface(ResourcesCompat.getFont(this, R.font.sora));
            message.setAlpha((float) 0.8);
            message.setTextSize(30);
            mother1.addView(message);
        }
        if(getMyTitle.size()==0){
            TextView message2 = new TextView(this);
            message2.setText("No Campgrounds Available!");
            message2.setTypeface(ResourcesCompat.getFont(this, R.font.sora));
            message2.setAlpha((float) 0.8);
            message2.setTextSize(25);
            mother2.addView(message2);
        }

        for (int i = 0; i < getMyTitle.size();i++) {
            LinearLayout verti = new LinearLayout(this);
            verti.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, 1000));
            verti.setOrientation(LinearLayout.VERTICAL);
            FrameLayout.LayoutParams lp1 = (FrameLayout.LayoutParams) verti.getLayoutParams();
            lp1.setMargins(10, 0, 0, 25);
            verti.setLayoutParams(lp1);
            MyarrayVerti.add(verti);

            LinearLayout body = new LinearLayout(this);
            body.setLayoutParams(new LinearLayout.LayoutParams(700, 500));
            body.setBackgroundColor(Color.parseColor("#FFCA42"));
            body.setPadding(55, 20, 0, 0);
            body.setOrientation(LinearLayout.VERTICAL);
            MyarrayBody.add(body);

            ImageView v= new ImageView(this);
            v.setMinimumHeight(400);
            v.setMaxHeight(400);
            v.setAdjustViewBounds(true);
            v.setScaleType(ImageView.ScaleType.CENTER_CROP);
            String url = "http://192.168.1.105/FYP_Project/assets/"+getMyImages.get(i);
            Glide.with(this).load(url)
                    .placeholder(R.drawable.reloading)
                    .error(R.drawable.reloading)
                    .into(v);
            MyarrayView.add(v);

            TextView title = new TextView(this);
            title.setWidth(600);
            title.setHeight(110);
            title.setText(getMyTitle.get(i));
            title.setTextColor(Color.parseColor("#1F5460"));
            title.setTextAppearance(R.style.textStyle);
            title.setTextSize(26);
            MyarrayTitle.add(title);

            TextView description = new TextView(this);
            description.setWidth(400);
            description.setHeight(200);
            description.setText(getMyDescription.get(i));
            description.setTypeface(ResourcesCompat.getFont(this, R.font.sora));
            description.setAlpha((float) 0.8);
            description.setTextSize(18);
            MyarrayDescription.add(description);

            TextView perNight = new TextView(this);
            perNight.setWidth(200);
            perNight.setHeight(70);
            perNight.setText("/night");
            perNight.setAlpha((float) 0.8);
            perNight.setTextSize(18);
            MyarrayPerNight.add(perNight);

            TextView price = new TextView(this);
            price.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 90));
            MypriceValue = MyPricer.get(i).toString();
            price.setText("$" + MypriceValue);
            price.setTextColor(Color.parseColor("#1F5460"));
            price.setTextSize(23);
            MyarrayPrice.add(price);

            LinearLayout priceNight = new LinearLayout(this);
            priceNight.setLayoutParams(new LinearLayout.LayoutParams(400, 110));
            priceNight.setOrientation(LinearLayout.HORIZONTAL);
            priceNight.addView(price);
            priceNight.addView(perNight);
            MyarrayPriceNight.add(priceNight);


            LinearLayout priceGo = new LinearLayout(this);
            priceGo.setLayoutParams(new LinearLayout.LayoutParams(550, 150));
            priceGo.setOrientation(LinearLayout.HORIZONTAL);
            priceGo.setPadding(0, 30, 0, 0);
            priceGo.addView(priceNight);
            MyarrayPriceGo.add(priceGo);

            ImageButton go = new ImageButton(this);
            go.setLayoutParams(new LinearLayout.LayoutParams(130, 130));
            go.setBackgroundResource(R.drawable.rectangle_bg_light_green_100_border_white_a700_19_radius_8);
            go.setImageResource(R.drawable.img_frame4);
            int finalI = i;
            go.setOnClickListener(view -> {
                Intent intent1 = new Intent(Profile.this, getImages.class);
                intent1.putExtra("CampId", getMyId.get(finalI));
                intent1.putExtra("username", username);
                intent1.putExtra("profile", "profile");
                if (!Objects.equals(source, "")) {
                    intent1.putExtra("source", "login");
                }
                startActivity(intent1);
            });
            priceGo.addView(go);
            MyarrayImage.add(go);

        }
        for (int i = 0; i < getMyTitle.size(); i++) {

            MyarrayBody.get(i).addView(MyarrayTitle.get(i));
            MyarrayBody.get(i).addView(MyarrayDescription.get(i));
            MyarrayBody.get(i).addView(MyarrayPriceGo.get(i));
            MyarrayVerti.get(i).addView(MyarrayView.get(i));
            MyarrayVerti.get(i).addView(MyarrayBody.get(i));
            mother2.addView(MyarrayVerti.get(i));
        }
        //Done My Campgrounds
        for (int i = 0; i < getTitle.size();i++) {

            //parent
            LinearLayout verti = new LinearLayout(this);
            verti.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, 1000));
            verti.setOrientation(LinearLayout.VERTICAL);
            FrameLayout.LayoutParams lp1 = (FrameLayout.LayoutParams) verti.getLayoutParams();
            lp1.setMargins(10, 0, 0, 25);
            verti.setLayoutParams(lp1);
            arrayVertical.add(verti);


           // body
            LinearLayout body = new LinearLayout(this);
            body.setLayoutParams(new LinearLayout.LayoutParams(700, 500));
            body.setBackgroundColor(Color.parseColor("#FFCA42"));
            body.setPadding(55, 20, 0, 0);
            body.setOrientation(LinearLayout.VERTICAL);
            arrayBody.add(body);

            //display image
            ImageView v= new ImageView(this);
            v.setMinimumHeight(400);
            v.setMaxHeight(400);
            v.setAdjustViewBounds(true);
            v.setScaleType(ImageView.ScaleType.CENTER_CROP);

            String url = "http://192.168.1.105/FYP_Project/assets/"+getImage.get(i);

            Glide.with(this).load(url)
                    .placeholder(R.drawable.reloading)
                    .error(R.drawable.reloading)
                    .into(v);
            arrayView.add(v);

            //title
            TextView title = new TextView(this);
            title.setWidth(600);
            title.setHeight(98);
            title.setText(getTitle.get(i));
            title.setTextColor(Color.parseColor("#1F5460"));
            title.setTextAppearance(R.style.textStyle);
            title.setTextSize(25);
            arrayTitle.add(title);

            //duration
            TextView duration = new TextView(this);
            duration.setWidth(570);
            duration.setHeight(135);
            duration.setText("From "+getStartDate.get(i)+" Till "+getEndDate.get(i));
            duration.setTypeface(ResourcesCompat.getFont(this, R.font.sora));
            duration.setAlpha((float) 0.8);
            duration.setTextSize(18);
            arrayDuration.add(duration);

            //nbPersons
            TextView nbPersons = new TextView(this);
            nbPersons.setWidth(570);
            nbPersons.setHeight(70);
            nbPersons.setText(getNbPersons.get(i)+" person");
            nbPersons.setTypeface(ResourcesCompat.getFont(this, R.font.sora));
            nbPersons.setAlpha((float) 0.8);
            nbPersons.setTextSize(18);
            arrayNbPersons.add(nbPersons);


            //create layout for price and cancel button
            LinearLayout priceButton = new LinearLayout(this);
            priceButton.setLayoutParams(new LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
            priceButton.setOrientation(LinearLayout.HORIZONTAL);
            priceButton.setPadding(0, 20, 0, 10);
            arrayLastLine.add(priceButton);

            //total price
            TextView price = new TextView(this);
            price.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 90));
            price.setText("$"+getTotalPrice.get(i));
            price.setTextColor(Color.parseColor("#1F5460"));
            price.setTextSize(23);
            arrayTotalPrice.add(price);

            //cancel button
                Button go = new Button(this);
                go.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, 95));
                FrameLayout.LayoutParams lp2 = (FrameLayout.LayoutParams) go.getLayoutParams();
                lp2.setMargins(190, 0, 0, 10);
                go.setLayoutParams(lp2);
                go.setText("Cancel");
                go.setTextColor(Color.parseColor("#FFFFFFFF"));
                if(getCancelFlag.get(i)==1){
                    go.setText("canceled");
                    go.setBackgroundResource(R.drawable.grey_bg);
                    go.setOnClickListener(view -> {
                        Toast toast = Toast.makeText(getApplicationContext(), "This Book is already canceled!", Toast.LENGTH_LONG);
                        toast.show();
                    });
                }else{
                        go.setBackgroundResource(R.drawable.red_bg);

                        int finalI = i;
                        go.setOnClickListener(view -> {

                            new AlertDialog.Builder(this).setTitle("Cancelation").setMessage("Are you sure do you want to cancel this booking?")
                                    .setPositiveButton("No",new DialogInterface.OnClickListener(){

                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    })
                                    .setNegativeButton("Yes", new DialogInterface.OnClickListener(){
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast toast = Toast.makeText(getApplicationContext(), "You successfully canceled this campground", Toast.LENGTH_LONG);
                                            toast.show();
                                            String url1 = "http://192.168.1.105/CampProject/cancelBooking.php";
                                            String sendId = "?bookingId="+getBookingId.get(finalI);

                                            class dbclass extends AsyncTask<String,Void,String> {
                                                protected void onPostExecute(String data) {
                                                    try {
                                                        Intent intent = new Intent(Profile.this, DbHistory.class);
                                                        intent.putExtra("username", username);
                                                        if (!Objects.equals(source, "")) {
                                                            intent.putExtra("source", "login");
                                                        }
                                                        startActivity(intent);


                                                    } catch (Exception e) {
                                                        e.getMessage();
                                                    }
                                                }

                                                @Override
                                                protected String doInBackground(String... strings) {
                                                    try{
                                                        URL url2 = new URL(strings[0]);
                                                        HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
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
                                            obj.execute(url1+sendId);


                                        }
                                    }).show();
                        });
                    }
                    arrayCancel.add(go);

                }

        for (int i = 0; i < getTitle.size();i++) {
            arrayBody.get(i).addView(arrayTitle.get(i));
            arrayBody.get(i).addView(arrayDuration.get(i));
            arrayBody.get(i).addView(arrayNbPersons.get(i));
            arrayLastLine.get(i).addView(arrayTotalPrice.get(i));
            arrayLastLine.get(i).addView(arrayCancel.get(i));
            arrayBody.get(i).addView(arrayLastLine.get(i));
            arrayVertical.get(i).addView(arrayView.get(i));
            arrayVertical.get(i).addView(arrayBody.get(i));
            mother1.addView(arrayVertical.get(i));

        }
    }

    public void logout(View v){
        Intent intent = new Intent(Profile.this , Home.class);
        startActivity(intent);
    }
    public void back(View v){
            Intent intent = new Intent(Profile.this , getDbCamp.class);
            intent.putExtra("username"  , username);
            if(!Objects.equals(source, "")){
                intent.putExtra("source","login");
            }
            startActivity(intent);
        }
    }
