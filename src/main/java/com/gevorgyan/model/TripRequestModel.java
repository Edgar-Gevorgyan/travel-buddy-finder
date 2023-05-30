package com.gevorgyan.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class TripRequestModel {
    private String location;
    private Date startDate;
    private Integer durationInDays;
}
