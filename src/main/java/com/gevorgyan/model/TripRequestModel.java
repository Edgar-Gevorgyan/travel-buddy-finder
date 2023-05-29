package com.gevorgyan.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TripRequestModel {
    private String location;
    private Date startDate;
    private Integer durationInDays;
}
