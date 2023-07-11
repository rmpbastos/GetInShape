package com.example.getinshape;

public class Food {

    private String name;
    private double serving_size_g;
    private double calories;

    public Food(String name, double serving_size_g, double calories) {
        this.name = name;
        this.serving_size_g = serving_size_g;
        this.calories = calories;
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
