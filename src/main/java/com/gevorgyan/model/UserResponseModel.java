package com.gevorgyan.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseModel {
    private String id;
    private String username;
    private Boolean isAdmin;
}
