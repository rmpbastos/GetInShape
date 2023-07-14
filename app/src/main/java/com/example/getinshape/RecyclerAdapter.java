package com.example.getinshape;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList food_id, serving_id, calories_id, timestamp_id;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public RecyclerAdapter(Context context, ArrayList food_id, ArrayList serving_id, ArrayList calories_id, ArrayList timestamp_id) {
        this.context = context;
        this.food_id = food_id;
        this.serving_id = serving_id;
        this.calories_id = calories_id;
        this.timestamp_id = timestamp_id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        String food_testing = StringUtils.capitalize(String.valueOf(food_id.get(position)).replace("\"", ""));
//        holder.food_id.setText(food_testing);
        holder.food_id.setText(StringUtils.capitalize(String.valueOf(food_id.get(position)).replace("\"", "")));
        holder.serving_id.setText(String.valueOf(serving_id.get(position)) + " g");
        holder.calories_id.setText(String.valueOf(calories_id.get(position)) + " kcal");
        holder.timestamp_id.setText(String.valueOf(timestamp_id.get(position)));
    }

    @Override
    public int getItemCount() {
        return food_id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView food_id, serving_id, calories_id, timestamp_id;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            food_id = itemView.findViewById(R.id.food_name);
            serving_id = itemView.findViewById(R.id.food_serving);
            calories_id = itemView.findViewById(R.id.food_calories);
            timestamp_id = itemView.findViewById(R.id.food_timestamp);
        }
    }


}
