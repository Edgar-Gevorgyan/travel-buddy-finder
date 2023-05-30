package com.gevorgyan.service;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "uuid-generator-api")
public interface UUIDGeneratorService {

    @GET
    @Path("/count/{count}")
    List<String> generateUUIDs(@PathParam("count") int count);
}
