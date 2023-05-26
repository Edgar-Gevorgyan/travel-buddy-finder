package com.gevorgyan.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestModel {
    private String username;
    private String password;
    private Boolean isAdmin;
}
