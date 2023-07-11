package com.example.getinshape;

import java.time.LocalDateTime;

public class Food {

    private LocalDateTime localDateTime;
    private String name;
    private double serving_size_g;
    private double calories;

    public Food(LocalDateTime localDateTime, String name, double serving_size_g, double calories) {
        this.localDateTime = localDateTime;
        this.name = name;
        this.serving_size_g = serving_size_g;
        this.calories = calories;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getServing_size_g() {
        return serving_size_g;
    }

    public void setServing_size_g(double serving_size_g) {
        this.serving_size_g = serving_size_g;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }
}
