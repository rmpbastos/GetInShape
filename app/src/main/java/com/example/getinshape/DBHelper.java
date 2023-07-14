package com.example.getinshape;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "FoodData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table user_diary(local_date_time TEXT primary key, food_name TEXT, " +
                "serving_size_g FLOAT, calories FLOAT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists user_diary");
    }


    //Insert data to table
    public Boolean insertUserData(String local_date_time, String food_name,
                                  double serving_size_g, double calories) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("local_date_time", local_date_time);
        contentValues.put("food_name", food_name);
        contentValues.put("serving_size_g", serving_size_g);
        contentValues.put("calories", calories);
        long result = db.insert("user_diary", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //Delete data from table
    public Boolean deleteUserData(String local_date_time) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from user_diary where local_date_time = ?",
                new String[] {local_date_time});
        if (cursor.getCount() > 0) {
            long result = db.delete("user_diary", "local_date_time=?", new String[] {local_date_time});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    //Get data from table
    public Cursor getUserData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from user_diary", null);
        return cursor;
    }

    //Get total calorie intake
    public Cursor getCalorieIntake() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select sum(calories) from user_diary", null);
        return cursor;
    }

}
