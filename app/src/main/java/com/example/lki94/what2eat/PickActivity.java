package com.example.lki94.what2eat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PickActivity extends AppCompatActivity {

    ArrayList<String> categories = new ArrayList<String>();
    Map<String, String> map = new HashMap<String, String>();
    int next = 2;

    String latitude;
    String longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick);

        Intent intent = getIntent();
        latitude = intent.getStringExtra("LATITUDE");
        longitude = intent.getStringExtra("LONGITUDE");
        final String url = "https://developers.zomato.com/api/v2.1/geocode?lat=" + latitude + "&lon=" + longitude;

        categories.add("African");
        categories.add("Burger");
        categories.add("Chinese");
        categories.add("Deli");
        categories.add("Fast Food");
        categories.add("Indian");
        categories.add("Italian");
        categories.add("Japanese");
        categories.add("Korean");
        categories.add("Mediterranean");
        categories.add("Mexican");
        categories.add("Middle Eastern");
        categories.add("Pizza");
        categories.add("Salad");
        categories.add("Sandwich");
        categories.add("Seafood");
        categories.add("Steak");
        categories.add("Sushi");
        categories.add("Thai");
        categories.add("Vegetarian");
        categories.add("Vietnamese");

        map.put("African", "152");
        map.put("Burger", "168");
        map.put("Chinese", "25");
        map.put("Deli", "192");
        map.put("Fast Food", "40");
        map.put("Indian", "148");
        map.put("Italian", "55");
        map.put("Japanese", "60");
        map.put("Korean", "67");
        map.put("Mediterranean", "70");
        map.put("Mexican", "73");
        map.put("Middle Eastern", "137");
        map.put("Pizza", "82");
        map.put("Salad", "998");
        map.put("Sandwich", "304");
        map.put("Seafood", "83");
        map.put("Steak", "141");
        map.put("Sushi", "177");
        map.put("Thai", "95");
        map.put("Vegetarian", "308");
        map.put("Vietnamese", "99");

        Collections.shuffle(categories);

        Button ButtonA = (Button)findViewById(R.id.buttonA);
        ButtonA.setText(categories.get(0));

        ButtonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(next == categories.size()) {
                    Intent intent = new Intent(v.getContext(), RestaurantList.class); //RestaurantList
                    intent.putExtra("LATITUDE", latitude);
                    intent.putExtra("LONGITUDE", longitude);
                    Button ButtonA = (Button) findViewById(R.id.buttonA);
                    intent.putExtra("CUISINE", map.get(ButtonA.getText()));

                    startActivityForResult(intent, 2);
                } else {
                    Button ButtonB = (Button) findViewById(R.id.buttonB);
                    ButtonB.setText(categories.get(next));
                    next += 1;
                }
            }
        });

        Button ButtonB = (Button)findViewById(R.id.buttonB);
        ButtonB.setText(categories.get(1));

        ButtonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(next == categories.size()) {
                    Intent intent = new Intent(v.getContext(), RestaurantList.class); //RestaurantList
                    intent.putExtra("LATITUDE", latitude);
                    intent.putExtra("LONGITUDE", longitude);
                    Button ButtonB = (Button) findViewById(R.id.buttonB);
                    intent.putExtra("CUISINE", map.get(ButtonB.getText()));

                    startActivityForResult(intent, 2);
                } else {
                    Button ButtonA = (Button) findViewById(R.id.buttonA);
                    ButtonA.setText(categories.get(next));
                    next += 1;
                }
            }
        });
    }
}
