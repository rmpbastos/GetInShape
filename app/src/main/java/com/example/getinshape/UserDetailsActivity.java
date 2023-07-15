package com.example.getinshape;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UserDetailsActivity extends AppCompatActivity {

    private EditText name_editText, age_editText, height_editText, weight_editText;
    private Spinner gender_spinner, activity_level_spinner, objective_spinner;
    private Button save_button;

    String user_name, user_gender, user_activity_level, user_objective;
    int user_age, user_height;
    double user_weight;

    //Total Daily Energy Expenditure
    double tdee;

    //Recommended calorie intake
    double recommended_calorie_intake;

    DBHelperUser db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        name_editText = findViewById(R.id.name_editText);
        age_editText = findViewById(R.id.age_editText);
        height_editText = findViewById(R.id.height_editText);
        weight_editText = findViewById(R.id.weight_editText);
        gender_spinner = findViewById(R.id.gender_spinner);
        activity_level_spinner = findViewById(R.id.activity_level_spinner);
        objective_spinner = findViewById(R.id.objective_spinner);
        save_button = findViewById(R.id.save_button);

        db = new DBHelperUser(this);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name = name_editText.getText().toString();

                try {
                    user_age = Integer.parseInt(age_editText.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(UserDetailsActivity.this, "Please enter your age", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    user_height = Integer.parseInt(height_editText.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(UserDetailsActivity.this, "Please enter your height", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    user_weight = Double.parseDouble(weight_editText.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(UserDetailsActivity.this, "Please enter your weight", Toast.LENGTH_SHORT).show();
                    return;
                }

                user_gender = gender_spinner.getSelectedItem().toString();
                if(gender_spinner.getSelectedItemPosition() == 0) {
                    user_gender = "Female";
                }
                else {
                    user_gender = "Male";
                }

                user_activity_level = activity_level_spinner.getSelectedItem().toString();
                if(activity_level_spinner.getSelectedItemPosition() == 0) {
                    user_activity_level = "Sedentary";
                } else if (activity_level_spinner.getSelectedItemPosition() == 1) {
                    user_activity_level = "Lightly Active";
                } else if (activity_level_spinner.getSelectedItemPosition() == 2) {
                    user_activity_level = "Moderately Active";
                } else if (activity_level_spinner.getSelectedItemPosition() == 3) {
                    user_activity_level = "Very Active";
                } else if (activity_level_spinner.getSelectedItemPosition() == 4) {
                    user_activity_level = "Extremely Active";
                }

                user_objective = objective_spinner.getSelectedItem().toString();
                if(objective_spinner.getSelectedItemPosition() == 0) {
                    user_objective = "Maintain";
                } else if(objective_spinner.getSelectedItemPosition() == 1) {
                    user_objective = "Lose";
                } else if (objective_spinner.getSelectedItemPosition() == 2) {
                    user_objective = "Gain";
                }

                calculateRecommendedCalorieIntake();

                //Insert user details into the database
                Boolean checkInsertData = db.insertUserDetails(user_name, user_age, user_gender,
                                user_height, user_weight, user_activity_level, user_objective, recommended_calorie_intake);
                if (checkInsertData == true) {
                    Toast.makeText(UserDetailsActivity.this, "New entry inserted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserDetailsActivity.this, "Sorry, entry not inserted.", Toast.LENGTH_SHORT).show();
                }

                openSearchActivity();

            }
        });
    }

    public void calculateRecommendedCalorieIntake() {

        //Basal Metabolic Rate
        double bmr;

        //TDEE for a female user
        if(user_gender.equals("Female")) {
            bmr = 655 + (9.6 * user_weight) + (1.8 * user_height) - (4.7 * user_age);
        } else {
            bmr = 66 + (13.7 * user_weight) + (5 * user_height) - (6.8 * user_age);
        }

        if(user_activity_level.equals("Sedentary")) {
            tdee = bmr * 1.2;
        } else if (user_activity_level.equals("Lightly Active")) {
            tdee = bmr * 1.375;
        } else if (user_activity_level.equals("Moderately Active")) {
            tdee = bmr * 1.55;
        } else if (user_activity_level.equals("Very Active")) {
            tdee = bmr * 1.725;
        } else {
            tdee = bmr * 1.9;
        }

        if(user_objective.equals("Maintain")) {
            recommended_calorie_intake = tdee;
        } else if (user_objective.equals("Lose")) {
            recommended_calorie_intake = tdee * 0.8;
        } else {
            recommended_calorie_intake = tdee * 1.15;
        }
    }

    public void openSearchActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}