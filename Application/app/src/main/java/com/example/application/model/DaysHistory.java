package com.example.application.model;

public class DaysHistory {

    private int count;
    private String date;

    public DaysHistory(int count, String date) {
        this.count = count;
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
