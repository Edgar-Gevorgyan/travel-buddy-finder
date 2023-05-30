package com.gevorgyan.controller;

import com.gevorgyan.model.TripRequestModel;
import com.gevorgyan.model.TripResponseModel;
import com.gevorgyan.service.TripService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Path("/trips")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class TripController {

    private final TripService tripService;

    @POST
    public Response addTrip(@HeaderParam("Authorization") String currentUserId,
                            TripRequestModel tripRequestModel) {
        try {
            TripResponseModel createdTrip = tripService.addTrip(tripRequestModel, currentUserId);
            return Response.status(Response.Status.CREATED).entity(createdTrip).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    public Response getTrips(@QueryParam("available") Boolean available,
                             @QueryParam("username") String username) {
        List<TripResponseModel> trips = tripService.getTrips(available, username);
        return Response.ok(trips).build();
    }

    @GET
    @Path("/{id}")
    public Response getTrip(@PathParam("id") String tripId) {
        try {
            TripResponseModel trip = tripService.getTrip(tripId);
            return Response.ok(trip).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/{id}/interestedUsers")
    public Response addInterest(@HeaderParam("Authorization") String currentUserId,
                                @PathParam("id") String tripId) {
        try {
            TripResponseModel trip = tripService.addInterest(tripId, currentUserId);
            return Response.status(Response.Status.CREATED).entity(trip).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
