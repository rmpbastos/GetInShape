package com.example.getinshape;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperUser extends SQLiteOpenHelper {

    public DBHelperUser(Context context) {
        super(context, "UserData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table user_details(id INTEGER PRIMARY KEY AUTOINCREMENT, user_name TEXT, " +
                "user_age INTEGER, user_gender TEXT, user_height INTEGER, user_weight REAL, " +
                "user_activity_level TEXT, user_objective TEXT, recommended_calorie_intake REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists user_details");
    }

    public Boolean insertUserDetails(String name, int age, String gender, int height, double weight,
                                  String activity_level, String objective, double recommended_calorie_intake) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_name", name);
        contentValues.put("user_age", age);
        contentValues.put("user_gender", gender);
        contentValues.put("user_height", height);
        contentValues.put("user_weight", weight);
        contentValues.put("user_activity_level", activity_level);
        contentValues.put("user_objective", objective);
        contentValues.put("recommended_calorie_intake", recommended_calorie_intake);
        long result = db.insert("user_details", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //Get data from table
    public Cursor getUserRecommendedIntake() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select recommended_calorie_intake from user_details " +
                "where id = 1", null);
        return cursor;
    }
}
