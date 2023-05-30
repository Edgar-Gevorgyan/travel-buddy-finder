package com.gevorgyan.service;

import com.gevorgyan.model.WorldWeatherOnlineResponseModel;
import io.quarkus.rest.client.reactive.ClientQueryParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "world-weather-online-api")
@ClientQueryParam(name = "key", value = "${world-weather-online.api-key}")
public interface WorldWeatherService {

    @GET
    @ClientQueryParam(name = "tp", value = "24")
    @ClientQueryParam(name = "cc", value = "no")
    @ClientQueryParam(name = "format", value = "json")
    WorldWeatherOnlineResponseModel getWeather(@QueryParam("q") String city, @QueryParam("num_of_days") long days);
}
