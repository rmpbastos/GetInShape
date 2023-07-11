package com.example.getinshape;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById((R.id.food_textView));

        queue = Volley.newRequestQueue(this);

        fetchFood();
    }

    private void fetchFood() {

        //URL provided by the API
        String url = "https://api.calorieninjas.com/v1/nutrition?query=cheese";

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

                        textView.setText(foodArray[0]);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("We got an error, please try again");
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