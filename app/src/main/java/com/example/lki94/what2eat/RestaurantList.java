package com.example.lki94.what2eat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;

public class RestaurantList extends AppCompatActivity {

    List<RestaurantModel> restaurants = new ArrayList<>();
    RecyclerView rvRestaurants;
    RestaurantAdapter adapter;

    String latitude;
    String longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        rvRestaurants = (RecyclerView) findViewById(R.id.rvRestaurants);

        Intent intent = getIntent();
        latitude = intent.getStringExtra("LATITUDE");
        longitude = intent.getStringExtra("LONGITUDE");
        final String url = "https://developers.zomato.com/api/v2.1/geocode?lat=" + latitude + "&lon=" + longitude;

        requestInfo(url);

        Button button = findViewById(R.id.pick_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SearchActivity.class);
                intent.putExtra("LATITUDE", latitude);
                intent.putExtra("LONGITUDE", longitude);

                ArrayList<RestaurantModel> restaurants2 = new ArrayList<RestaurantModel> (restaurants);
                intent.putParcelableArrayListExtra("RESTAURANTS", restaurants2);
                startActivityForResult(intent, 3);
            }
        });
    }

    private void requestInfo(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        renderInfo(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR", "error => " + error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user-key", "b4475404ace6ee40ed324f3c44f45302");
                params.put("Accept", "application/json");

                return params;
            }
        };
        requestQueue.add(postRequest);
    }

    private void renderInfo(JSONObject response) {
        try {
            JSONArray parentArray = response.getJSONArray("nearby_restaurants");
            for(int i = 0; i < parentArray.length(); i++) {
                JSONObject finalObject = parentArray.getJSONObject(i);
                RestaurantModel restaurantModel = new RestaurantModel();

                restaurantModel.setName(finalObject.getJSONObject("restaurant").getString("name"));
                restaurantModel.setAddress(finalObject.getJSONObject("restaurant").getJSONObject("location").getString("address"));
                restaurantModel.setCuisines(finalObject.getJSONObject("restaurant").getString("cuisines"));
                restaurantModel.setAverageCost(finalObject.getJSONObject("restaurant").getInt("average_cost_for_two"));
                restaurantModel.setUserRating(finalObject.getJSONObject("restaurant").getJSONObject("user_rating").getDouble("aggregate_rating"));
                restaurantModel.setImage(finalObject.getJSONObject("restaurant").getString("photos_url"));

                restaurants.add(restaurantModel);
            }

            adapter = new RestaurantAdapter(this, restaurants);
            rvRestaurants.setAdapter(adapter);
            rvRestaurants.setLayoutManager(new LinearLayoutManager(this));

        } catch (Exception e){
            Log.e("What2Eat", "Fields not found in the JSON data");
        }
    }
}