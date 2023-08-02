package com.example.getinshape;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;

    private static final String TABLE_NAME = "USER_DIARY";

    public DBHelper(Context context) {
        super(context, "FoodData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(local_date_time INTEGER PRIMARY KEY, food_name TEXT, " +
                "serving_size_g REAL, calories REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user_diary");
    }


    //Insert data to table
    public Boolean insertUserData(long local_date_time, String food_name,
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
    public Boolean deleteUserData(long local_date_time) {
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE local_date_time = ?",
                new String[] {String.valueOf(local_date_time)});
        if (cursor.getCount() > 0) {
            long result = db.delete(TABLE_NAME, "local_date_time=?",
                    new String[] {String.valueOf(local_date_time)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


//    public Boolean deleteUserData(String local_date_time) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM user_diary WHERE local_date_time = ?",
//                new String[] {local_date_time});
//        if (cursor.getCount() > 0) {
//            long result = db.delete("user_diary", "local_date_time=?", new String[] {local_date_time});
//            if (result == -1) {
//                return false;
//            } else {
//                return true;
//            }
//        } else {
//            return false;
//        }
//    }

    //Get data from table
    public Cursor getUserData() {
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }

    //Get total calorie intake
    public Cursor getCalorieIntake() {
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(calories) FROM " + TABLE_NAME, null);
        return cursor;
    }

}
