package com.gevorgyan.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class WeatherResponseModel {
    private LocalDate date;
    private Integer maxtempC;
    private Integer mintempC;
    private Integer avgtempC;
    private Double sunHour;
    private Integer uvIndex;
}
