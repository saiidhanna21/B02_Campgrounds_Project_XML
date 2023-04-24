package com.example.b02_campgrounds_project;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Objects;

public class index extends AppCompatActivity {
    TextView welcome;
    ArrayList<String> getTitle, getDescription,getImages;
    ArrayList<Double> Pricer;
    ArrayList<Integer> getId;
    String priceValue,username,source="";
    GridLayout hori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTitle = new ArrayList<>();
        getDescription = new ArrayList<>();
        Pricer = new ArrayList<>();
        getId = new ArrayList<>();
        getImages = new ArrayList<>();
        setContentView(R.layout.activity_index);
        hori= findViewById(R.id.mother);
        welcome=findViewById(R.id.welcome);


        
        Bundle extra = getIntent().getBundleExtra("extra");
        getTitle = (ArrayList<String>) extra.getSerializable("getTitle");
        getDescription = (ArrayList<String>) extra.getSerializable("getDescription");
        Pricer = (ArrayList<Double>) extra.getSerializable("Pricer");
        getId = (ArrayList<Integer>) extra.getSerializable("getId");
        getImages = (ArrayList<String>) extra.getSerializable("getImages");
        username = extra.getString("username");

        if(extra.containsKey("source")&& extra.get("source").equals("login")){
            source="source";
            welcome.setText("Welcome back "+username);
        }else{
            welcome.setText("Welcome "+username);
        }

        ArrayList<View>arrayView=new ArrayList<>();
        ArrayList<TextView>arrayPerNight=new ArrayList<>();
        ArrayList<TextView>arrayTitle=new ArrayList<>();
        ArrayList<TextView>arrayPrice=new ArrayList<>();
        ArrayList<LinearLayout>arrayPriceNight=new ArrayList<>();
        ArrayList<LinearLayout>arrayPriceGo=new ArrayList<>();
        ArrayList<LinearLayout>arrayBody=new ArrayList<>();
        ArrayList<TextView>arrayDescription=new ArrayList<>();
        ArrayList<LinearLayout>arrayVerti=new ArrayList<>();
        ArrayList<ImageButton>arrayImage=new ArrayList<>();

        for (int i = 0; i < getTitle.size();i++) {

            LinearLayout verti = new LinearLayout(this);
            verti.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
            verti.setOrientation(LinearLayout.VERTICAL);
            FrameLayout.LayoutParams lp1 = (FrameLayout.LayoutParams) verti.getLayoutParams();
            lp1.setMargins(100, 25, 0, 25);
            verti.setLayoutParams(lp1);
            arrayVerti.add(verti);


            LinearLayout body = new LinearLayout(this);
            body.setLayoutParams(new LinearLayout.LayoutParams(675, 400));
            body.setBackgroundColor(Color.parseColor("#FFCA42"));
            body.setPadding(55, 20, 0, 0);
            body.setOrientation(LinearLayout.VERTICAL);
            arrayBody.add(body);




            ImageView v= new ImageView(this);

            String url = "http://192.168.1.105/CampProject/assets/"+getImages.get(i);

            Glide.with(this).load(url)
                    .placeholder(R.drawable.reloading)
                    .error(R.drawable.reloading)
                    .into(v);
            arrayView.add(v);


            TextView title = new TextView(this);
            title.setWidth(600);
            title.setHeight(98);
            title.setText(getTitle.get(i));
            title.setTextColor(Color.parseColor("#1F5460"));
            title.setTextAppearance(R.style.textStyle);
            title.setTextSize(25);
            arrayTitle.add(title);


            TextView description = new TextView(this);
            description.setWidth(570);
            description.setHeight(135);
            description.setText(getDescription.get(i));
            description.setTypeface(ResourcesCompat.getFont(this, R.font.sora));
            description.setAlpha((float) 0.8);
            description.setTextSize(18);
            arrayDescription.add(description);

            TextView perNight = new TextView(this);
            perNight.setWidth(200);
            perNight.setHeight(90);
            perNight.setText("/night");
            perNight.setAlpha((float) 0.8);
            perNight.setTextSize(20);
            arrayPerNight.add(perNight);

            TextView price = new TextView(this);
            price.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 90));
            priceValue=Pricer.get(i).toString();
            price.setText("$"+priceValue);
            price.setTextColor(Color.parseColor("#1F5460"));
            price.setTextSize(23);
            arrayPrice.add(price);

            LinearLayout priceNight = new LinearLayout(this);
            priceNight.setLayoutParams(new LinearLayout.LayoutParams(495, 165));
            priceNight.setOrientation(LinearLayout.HORIZONTAL);
            priceNight.addView(price);
            priceNight.addView(perNight);
            arrayPriceNight.add(priceNight);


            LinearLayout priceGo = new LinearLayout(this);
            priceGo.setLayoutParams(new LinearLayout.LayoutParams(675, 165));
            priceGo.setOrientation(LinearLayout.HORIZONTAL);
            priceGo.setPadding(0, 30, 0, 0);
            priceGo.addView(priceNight);
            arrayPriceGo.add(priceGo);

            ImageButton go = new ImageButton(this);
            go.setLayoutParams(new LinearLayout.LayoutParams(90, 90));
            go.setBackgroundResource(R.drawable.rectangle_bg_light_green_100_border_white_a700_19_radius_8);
            go.setImageResource(R.drawable.img_frame4);
            int finalI = i;
            go.setOnClickListener(view -> {
                Intent intent1 = new Intent(index.this, getImages.class);
                intent1.putExtra("CampId",getId.get(finalI));
                intent1.putExtra("username",username);
                if(!Objects.equals(source, "")){
                    intent1.putExtra("source","login");
                }
                startActivity(intent1);
            });
            priceGo.addView(go);
            arrayImage.add(go);

        }

        for (int i = 0; i < getTitle.size();i++) {

                arrayBody.get(i).addView(arrayTitle.get(i));
                arrayBody.get(i).addView(arrayDescription.get(i));
                arrayBody.get(i).addView(arrayPriceGo.get(i));
                arrayVerti.get(i).addView(arrayView.get(i));
                arrayVerti.get(i).addView(arrayBody.get(i));
                hori.addView(arrayVerti.get(i));
            }
        }
    }
//HEK bteshabe l id taba3 l camp li kbaste 3laya wel textview bas for tchoufi 3al cheche ka test
//        TextView test=findViewById(R.id.anything);
//        Bundle user=getIntent().getExtras();
//        if(user!=null) {
//        int id= (int) user.get("CampId");
//        test.setText("the id is: "+id);
//        }
