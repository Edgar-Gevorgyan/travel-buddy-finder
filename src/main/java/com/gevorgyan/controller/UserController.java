package com.gevorgyan.controller;

import com.gevorgyan.model.UserRequestModel;
import com.gevorgyan.model.UserResponseModel;
import com.gevorgyan.service.UserService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    private final UserService userService;

    @POST
    public Response addUser(@HeaderParam("Authorization") Optional<String> currentUserId,
                            UserRequestModel userRequestModel) {
        if (userRequestModel.getIsAdmin() && userService.isNotAdmin(currentUserId.orElse(""))) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            UserResponseModel createdUser = userService.addUser(userRequestModel);
            return Response.status(Response.Status.CREATED).entity(createdUser).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@HeaderParam("Authorization") Optional<String> currentUserId,
                               @PathParam("id") String userId) {
        if (userService.isNotAdmin(currentUserId.orElse(""))) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            userService.deleteUser(userId);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
