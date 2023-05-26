package com.gevorgyan.controller;

import com.gevorgyan.model.UserRequestModel;
import com.gevorgyan.model.UserResponseModel;
import com.gevorgyan.service.UserService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {

    private final UserService userService;

    @POST
    public Response login(UserRequestModel userRequestModel) {
        Optional<UserResponseModel> userResponseModel = userService
                .findUser(userRequestModel.getUsername(), userRequestModel.getPassword());

        if (userResponseModel.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(userResponseModel.get()).build();
    }
}
