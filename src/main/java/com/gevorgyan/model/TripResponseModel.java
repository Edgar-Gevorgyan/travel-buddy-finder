package com.gevorgyan.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class TripResponseModel {
    @Getter
    @Setter
    static class UserResponseModel {
        private String username;
    }

    private String id;
    private String location;
    private Date startDate;
    private Integer durationInDays;
    private List<WeatherResponseModel> weather;
    private UserResponseModel sharedBy;
    private List<UserResponseModel> interestedUsers;
}
