package com.example.getinshape;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private TextView foodTextView, servingSizeTextView, calorieTextView,
            dateTextView;
    private EditText editText;
    private Button search_button, add_button;
    private ConstraintLayout mainPageLayout;

    private String query;
    private ArrayList<Food> foodList = new ArrayList<>();

    DBHelper db;


    String name;
    double serving_size_g;
    double calories;
    LocalDateTime localDateTimeNow;

    //Set up date format to be displayed
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);

        foodTextView = findViewById(R.id.food_textView);
        servingSizeTextView = findViewById(R.id.serving_size_textView);
        calorieTextView = findViewById(R.id.calorie_textView);
        dateTextView = findViewById(R.id.date_textView);

        editText = findViewById(R.id.food_editText);

        search_button = findViewById(R.id.food_button);
        add_button = findViewById(R.id.add_button);

        mainPageLayout = findViewById(R.id.main_page_layout);

        db = new DBHelper(this);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get input from user
                query = editText.getText().toString();
                fetchFood(query);
            }
        });
    }

    private void fetchFood(String query) {

        //URL provided by the API
        String url = "https://api.calorieninjas.com/v1/nutrition?query=" + query;

        //Request a String response from the provided URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //String manipulation
                            response = response.replace("{\"items\": [{", "");
                            response = response.replace("}]}", "");

                            //Create a food Array
                            String[] foodArray = new String[12];

                            //Split the string by commas
                            String elements[] = response.split(",");

                            //Iterate the elements and add the values to the array
                            for (int i = 0; i < elements.length; i++) {
                                //Split the data by colon to separate keys and values
                                String splitElements[] = elements[i].split(":");
                                String value = splitElements[1].trim();
                                foodArray[i] = value;
                            }

                            name = foodArray[0];
                            serving_size_g = Double.parseDouble(foodArray[2]);
                            calories = Double.parseDouble(foodArray[1]);
                            localDateTimeNow = LocalDateTime.now();

                            //Display data
                            foodTextView.setText(name);
                            servingSizeTextView.setText(String.valueOf(serving_size_g));
                            calorieTextView.setText(String.valueOf(calories));
                            dateTextView.setText(dateTimeFormatter.format(localDateTimeNow));
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Sorry, food not found!\nPlease try again.", Toast.LENGTH_LONG).show();
                        }

                        //Add food to diary
                        add_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //Insert the food searched into the database
                                Boolean checkInsertData = db.insertUserData(localDateTimeNow.toString(),
                                        name, serving_size_g, calories);
                                if (checkInsertData == true) {
                                    Toast.makeText(MainActivity.this, "New entry inserted!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "Sorry, entry not inserted.", Toast.LENGTH_SHORT).show();
                                }

                                //Open DiaryActivity
                                openDiaryActivity();





//                                //Add the food searched to the user diary
//                                addToDiary(name, serving_size_g, calories, localDateTimeNow);
                            }
                        });
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                foodTextView.setText("We got an error, please try again");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap header = new HashMap();
                header.put("X-Api-Key", "OaSfrBrLm0b+NODkh1e1Ug==hcj7Tnm2ZmJ5gDWN");
                return header;
            }
        };

        //Add the request to the Request Queue
        queue.add(stringRequest);

    }

    private void addToDiary(String name, double serving_size_g, double calories, LocalDateTime localDateTimeNow) {

        //Create a Food object instance
        Food food = new Food(localDateTimeNow, name, serving_size_g, calories);
        foodList.add(food);

        //Save food object using SharedPreferences
        saveData();

        //Open DiaryActivity
        openDiaryActivity();

        //Display success message
        Toast.makeText(MainActivity.this, "Food added to diary!", Toast.LENGTH_SHORT).show();

    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("food_file", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();



        editor.putString("food_name", name);
        editor.putString("food_serving", String.valueOf(serving_size_g));
        editor.putString("food_calories", String.valueOf(calories));
        editor.putString("food_date", String.valueOf(localDateTimeNow));
        editor.apply();



//        Gson gson = new Gson();
//        String json = gson.toJson(foodList);
//
//        editor.putString("food_list", json);
//        editor.apply();
    }

    public void openDiaryActivity() {
        Intent intent = new Intent(this, DiaryActivity.class);
        startActivity(intent);
    }


}