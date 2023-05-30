package com.gevorgyan.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseModel {
    private String id;
    private String username;
    private Boolean isAdmin;
}
