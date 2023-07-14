package com.example.getinshape;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class MainActivity extends AppCompatActivity {

    private Button open_user_button, open_search_button, open_diary_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        open_user_button = findViewById(R.id.open_user_button);
        open_search_button = findViewById(R.id.open_search_button);
        open_diary_button = findViewById(R.id.open_diary_button);

        open_user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserDetailsActivity();
            }
        });

        open_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchActivity();
            }
        });

        open_diary_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open DiaryActivity
                openDiaryActivity();
            }
        });
    }

    public void openUserDetailsActivity() {
        Intent intent = new Intent(this, UserDetailsActivity.class);
        startActivity(intent);
    }

    public void openSearchActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void openDiaryActivity() {
        Intent intent = new Intent(this, DiaryActivity.class);
        startActivity(intent);
    }
}