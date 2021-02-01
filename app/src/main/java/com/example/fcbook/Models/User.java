package com.example.fcbook.Models;

import java.util.List;

public class User {
    private String uname;
    private List<String>meals_id;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public List<String> getMeals_id() {
        return meals_id;
    }

    public void setMeals_id(List<String> meals_id) {
        this.meals_id = meals_id;
    }
}
