package com.example.getinshape;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class DiaryActivity extends AppCompatActivity {

    private TextView diaryFood, diaryServing, diaryCalories, diaryDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        diaryFood = findViewById(R.id.diary_food);
        diaryServing = findViewById(R.id.diary_serving);
        diaryCalories = findViewById(R.id.diary_calories);
        diaryDate = findViewById(R.id.diary_date);

        loadData();
    }

    public void loadData() {
        //Load data from shared preferences file

        SharedPreferences sharedPreferences = getSharedPreferences("food_file", Context.MODE_PRIVATE);

        String name = sharedPreferences.getString("food_name", "Name is not available!");
        String serving = sharedPreferences.getString("food_serving", "Serving is not available!");
        String calories = sharedPreferences.getString("food_calories", "Calories not available!");
        String date = sharedPreferences.getString("food_date", "Date not available!");

        diaryFood.setText(name);
        diaryServing.setText(serving);
        diaryCalories.setText(calories);
        diaryDate.setText(date);

//
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("food_list", "List is not available!");
//        ArrayList<Food> foodList = gson.fromJson(json, ArrayList.class);
//
//        diaryFood.setText(foodList.size());

    }

}