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
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

//    Adapter adapter;

    private TextView diaryFood, diaryServing, diaryCalories, diaryDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

//        diaryFood = findViewById(R.id.diary_food);
//        diaryServing = findViewById(R.id.diary_serving);
//        diaryCalories = findViewById(R.id.diary_calories);
//        diaryDate = findViewById(R.id.diary_date);

        db = new DBHelper(this);
        food_name = new ArrayList<>();
        serving_size = new ArrayList<>();
        calories = new ArrayList<>();
        timestamp = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);
        adapter = new RecyclerAdapter(this, food_name, serving_size, calories, timestamp);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadData();
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




//        StringBuffer buffer = new StringBuffer();
//        while(result.moveToNext()) {
//            buffer.append("localDateTimeNow : " + result.getString(0)+"\n");
//            buffer.append("food : " + result.getString(1)+"\n");
//            buffer.append("serving : " + result.getString(2)+"\n");
//            buffer.append("calories : " + result.getString(3)+"\n\n");
//        }
//
//        diaryFood.setText(buffer.toString());

//        AlertDialog.Builder builder = new AlertDialog.Builder(DiaryActivity.this);
//        builder.setCancelable(true);
//        builder.setTitle("User entries");
//        builder.setMessage(buffer.toString());
//        builder.show();










//UNUSED CODE
//        //Load data from shared preferences file
//
//        SharedPreferences sharedPreferences = getSharedPreferences("food_file", Context.MODE_PRIVATE);
//
//        String name = sharedPreferences.getString("food_name", "Name is not available!");
//        String serving = sharedPreferences.getString("food_serving", "Serving is not available!");
//        String calories = sharedPreferences.getString("food_calories", "Calories not available!");
//        String date = sharedPreferences.getString("food_date", "Date not available!");
//
//        diaryFood.setText(name);
//        diaryServing.setText(serving);
//        diaryCalories.setText(calories);
//        diaryDate.setText(date);






//UNUSED CODE
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("food_list", "List is not available!");
////        ArrayList<Food> foodList = gson.fromJson(json, ArrayList.class);
//
//        System.out.println("********************************");
//        System.out.println(json);
//        System.out.println("********************************");
//
//        diaryFood.setText(json);

    }

}