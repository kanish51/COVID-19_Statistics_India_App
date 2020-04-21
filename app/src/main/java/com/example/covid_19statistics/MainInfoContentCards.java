package com.example.covid_19statistics;

import androidx.annotation.IntegerRes;

public class MainInfoContentCards
{
    String latestValue="";

    public MainInfoContentCards(String latestValue) {
        this.latestValue = latestValue;
    }

    public String  getLatestValue() {
        return latestValue;
    }

    public void setLatestValue(String latestValue) {
        this.latestValue = latestValue;
    }
}
