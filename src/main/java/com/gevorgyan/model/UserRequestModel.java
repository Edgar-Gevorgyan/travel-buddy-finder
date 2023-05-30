package com.gevorgyan.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestModel {
    private String username;
    private String password;
    private Boolean isAdmin;
}
