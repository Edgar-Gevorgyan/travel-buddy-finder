package com.gevorgyan.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorldWeatherOnlineResponseModel {
    @Getter
    @Setter
    public static class Data {
        private List<WeatherResponseModel> weather;
    }

    private Data data;
}
