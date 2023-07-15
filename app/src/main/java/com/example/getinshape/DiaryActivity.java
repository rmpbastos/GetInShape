package com.example.getinshape;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class DiaryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> food_name, serving_size, calories, timestamp;
    DBHelper db;

    DBHelperUser dbUser;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;


//    private TextView diaryFood, diaryServing, diaryCalories, diaryDate;
    private TextView calorie_targetTV, calories_eaten_todayTV, calories_remainingTV;

    private String calorie_target_str, calories_eaten_str;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        db = new DBHelper(this);
        dbUser = new DBHelperUser(this);
        food_name = new ArrayList<>();
        serving_size = new ArrayList<>();
        calories = new ArrayList<>();
        timestamp = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);
        adapter = new RecyclerAdapter(this, food_name, serving_size, calories, timestamp);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        calories_eaten_todayTV = findViewById(R.id.calories_eaten_todayTV);
        calorie_targetTV = findViewById(R.id.calorie_targetTV);
        calories_remainingTV = findViewById(R.id.calories_remainingTV);

        loadData();

        loadCalorieIntake();

        loadRecommendedIntake();

        double calories_remaining = Double.parseDouble(calorie_target_str) - Double.parseDouble(calories_eaten_str);
        String calories_remaining_str = String.format("%.2f", calories_remaining);
        calories_remainingTV.setText(calories_remaining_str);
    }

    public void loadData() {

        //Retrieve the data from the database
        Cursor result = db.getUserData();
        if(result.getCount() == 0) {
            Toast.makeText(DiaryActivity.this, "No entry found", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            while(result.moveToNext()) {
                timestamp.add(result.getString(0));
                food_name.add(result.getString(1));
                serving_size.add(result.getString(2));
                calories.add(result.getString(3));
            }
        }
    }

    private void loadCalorieIntake() {
        //Retrieve the data from the database
        Cursor result = db.getCalorieIntake();
        if(result.getCount() == 0) {
            Toast.makeText(DiaryActivity.this, "No entry found", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            while(result.moveToNext()) {
                calories_eaten_todayTV.setText(result.getString(0));
                calories_eaten_str = result.getString(0);
            }
        }
    }

    private void  loadRecommendedIntake() {
        //Retrieve the data from the database
        Cursor result = dbUser.getUserRecommendedIntake();
        if(result.getCount() == 0) {
            Toast.makeText(DiaryActivity.this, "No entry found", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            while(result.moveToNext()) {
                calorie_targetTV.setText(result.getString(0));
                calorie_target_str = result.getString(0);
            }
        }
    }


}