package com.example.lki94.what2eat;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class MainActivity extends AppCompatActivity {

    List<RestaurantModel> restaurants = new ArrayList<>();
    RecyclerView rvRestaurants;
    RestaurantAdapter adapter;

    private final String API_URL = "https://developers.zomato.com/api/v2.1/geocode?lat=38.026115&lon=-78.513447";
//    TextView restaurantName;
//    TextView cityName;
//    TextView restaurantRating;
//    TextView restaurantAddress;
    String latitude = "38.031050";
    String longitude = "-78.474160";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvRestaurants = (RecyclerView) findViewById(R.id.rvRestaurants);
//        restaurantName = (TextView) findViewById(R.id.restaurant_name);
//        cityName = (TextView) findViewById(R.id.city_field);
//        restaurantRating = (TextView) findViewById(R.id.restaurant_rating);
//        restaurantAddress = (TextView) findViewById(R.id.restaurant_address);

        final String url = "https://developers.zomato.com/api/v2.1/geocode?lat=" + latitude + "&lon=" + longitude;

        requestInfo(url);


    }

    private void requestInfo(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //final String url = "https://developers.zomato.com/api/v2.1/geocode?lat=38.031050&lon=-78.474160";

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
//            cityName.setText(response.getJSONObject("location").getString("title"));
//            restaurantName.setText(response.getJSONArray("nearby_restaurants").getJSONObject(0).getJSONObject("restaurant").getString("name"));

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
