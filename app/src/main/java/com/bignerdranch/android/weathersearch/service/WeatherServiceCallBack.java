package com.bignerdranch.android.weathersearch.service;

import com.bignerdranch.android.weathersearch.data.Channel;

/**
 * Created by Jal on 11/18/15.
 */
public interface WeatherServiceCallBack {
    void serviceSuccess(Channel channel);



    void serviceFailure(Exception exception);

}
