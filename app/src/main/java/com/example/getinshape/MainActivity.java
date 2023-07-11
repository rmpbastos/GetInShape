package com.example.getinshape;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView foodTextView;
    TextView servingSizeTextView;
    TextView calorieTextView;
    EditText editText;
    Button button;

    String query;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);

        foodTextView = findViewById(R.id.food_textView);
        servingSizeTextView = findViewById(R.id.serving_size_textView);
        calorieTextView = findViewById(R.id.calorie_textView);
        editText = findViewById(R.id.food_editText);
        button = findViewById(R.id.food_button);

        button.setOnClickListener(new View.OnClickListener() {
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

                        foodTextView.setText(foodArray[0]);
                        servingSizeTextView.setText(foodArray[2]);
                        calorieTextView.setText(foodArray[1]);

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
}