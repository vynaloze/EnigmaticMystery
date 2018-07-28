package com.vynaloze.smartmirror.view.weather;

import com.vynaloze.smartmirror.model.weather.pojo.Weather;

public interface ViewHandler {
    void updateData(Weather weather);
}
