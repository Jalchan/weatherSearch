package com.bignerdranch.android.weathersearch.data;

import org.json.JSONObject;

/**
 * Created by Jal on 11/18/15.
 */
public class Item implements JSONPopulator {
    private  Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));
    }
}
