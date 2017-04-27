package com.bignerdranch.android.weathersearch.service;

import android.net.Uri;
import android.os.AsyncTask;

import com.bignerdranch.android.weathersearch.WeatherActivity;
import com.bignerdranch.android.weathersearch.data.Channel;
import com.bignerdranch.android.weathersearch.data.JSONPopulator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Jal on 11/18/15.
 */
public class YahooWeatherService {
    private WeatherServiceCallBack callback;
    private String location;
    private  Exception error;

    public YahooWeatherService(WeatherServiceCallBack callback){
        this.callback = callback;
    }




    public String getLocation() {
        return location;
    }

    public void refreshWeather(final String location){
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {

                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")", location);

                String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));

                try {
                    URL url = new URL(endpoint);

                    URLConnection connection = url.openConnection();

                    InputStream inputStream = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null){
                        result.append(line);
                    }

                    return result.toString();

                } catch (Exception e) {
                    error = e;

                }


                return null;
            }

            @Override
            protected void onPostExecute(String s) {

                if (s == null && error != null){
                    callback.serviceFailure(error);

                    return;
                }

                try {
                    JSONObject data = new JSONObject(s);

                    JSONObject queryResults = data.optJSONObject("query");

                    int count = queryResults.optInt("count");

                    if (count == 0){
                        callback.serviceFailure(new locationWeatherException("No weather information found for " +location));
                        return;
                    }

                    Channel channel = new Channel();
                    channel.populate(queryResults.optJSONObject("results").optJSONObject("channel"));

                    callback.serviceSuccess(channel);
                } catch (JSONException e) {
                    callback.serviceFailure(e);

                }
            }


        }.execute(location);
    }
    public  class locationWeatherException extends Exception {
        public  locationWeatherException(String detailMessage){
            super(detailMessage);
        }
    }}
