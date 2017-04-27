package com.bignerdranch.android.weathersearch.data;

import org.json.JSONObject;

/**
 * Created by Jal on 11/18/15.
 */
public class Units implements JSONPopulator{
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {
        temperature = data.optString("temperature");
    }
}
