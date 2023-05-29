package com.gevorgyan.model;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TripResponseModel {
    @Getter
    @Setter
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserResponseModel {
        private String username;
    }

    private String id;
    private String location;
    private Date startDate;
    private Integer durationInDays;
    private UserResponseModel sharedBy;
    private List<UserResponseModel> interestedUsers;
}
