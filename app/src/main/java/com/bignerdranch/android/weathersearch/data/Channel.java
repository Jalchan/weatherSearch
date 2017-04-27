package com.bignerdranch.android.weathersearch.data;

import org.json.JSONObject;

/**
 * Created by Jal on 11/18/15.
 */
public class Channel implements JSONPopulator{
    private Item item;
    private Units units;

    public Item getItem() {
        return item;
    }

    public Units getUnits() {
        return units;
    }

    @Override
    public void populate(JSONObject data) {
        units = new Units();
        units.populate(data.optJSONObject("units"));

        item = new Item();
        item.populate(data.optJSONObject("item"));

    }
}
