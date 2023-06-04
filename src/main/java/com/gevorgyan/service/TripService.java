package com.gevorgyan.service;

import com.gevorgyan.entity.TripEntity;
import com.gevorgyan.entity.UserEntity;
import com.gevorgyan.model.TripRequestModel;
import com.gevorgyan.model.TripResponseModel;
import com.gevorgyan.model.WeatherResponseModel;
import com.gevorgyan.model.WorldWeatherOnlineResponseModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class TripService {

    private static final ModelMapper modelMapper = new ModelMapper();

    private final UUIDGeneratorService uuidGeneratorService;
    private final WorldWeatherService worldWeatherService;

    public TripService(@RestClient UUIDGeneratorService uuidGeneratorService,
                       @RestClient WorldWeatherService worldWeatherService) {
        this.uuidGeneratorService = uuidGeneratorService;
        this.worldWeatherService = worldWeatherService;
    }

    @Transactional
    public TripResponseModel addTrip(TripRequestModel tripRequestModel, String userId) {
        UserEntity user = UserEntity.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not exists");
        }

        String userUUID = uuidGeneratorService.generateUUIDs(1).get(0);
        TripEntity tripEntity = modelMapper.map(tripRequestModel, TripEntity.class);
        tripEntity.setId(userUUID);
        tripEntity.setSharedBy(user);
        tripEntity.setInterestedUsers(Set.of());
        TripEntity.persist(tripEntity);

        TripResponseModel tripResponseModel =  modelMapper.map(tripEntity, TripResponseModel.class);
        var weather = getWeather(tripResponseModel);
        tripResponseModel.setWeather(weather);
        return tripResponseModel;
    }

    public List<TripResponseModel> getTrips(Boolean available, String username) {
        UserEntity user = UserEntity.findByUsername(username);

        final List<TripEntity> trips;
        if (Boolean.TRUE.equals(available) && user != null) {
            trips = TripEntity.listAvailableAndSharedBy(user.getId());
        } else if (Boolean.TRUE.equals(available)) {
            trips = TripEntity.listAvailable();
        } else if (user != null) {
            trips = TripEntity.listBySharedBy(user.getId());
        } else {
            trips = TripEntity.listAll();
        }

        List<TripResponseModel> tripResponseModels =  modelMapper.map(trips, new TypeToken<List<TripResponseModel>>() {}.getType());
        return tripResponseModels.stream()
                .peek(tripResponseModel -> {
                    var weather = getWeather(tripResponseModel);
                    tripResponseModel.setWeather(weather);
                })
                .collect(Collectors.toList());
    }

    public TripResponseModel getTrip(String id) {
        TripEntity trip = TripEntity.findById(id);
        if (trip == null) {
            throw new IllegalArgumentException("Trip not found");
        }

        TripResponseModel tripResponseModel =  modelMapper.map(trip, TripResponseModel.class);
        var weather = getWeather(tripResponseModel);
        tripResponseModel.setWeather(weather);
        return tripResponseModel;
    }

    @Transactional
    public TripResponseModel addInterest(String tripId, String userId) {
        TripEntity trip = TripEntity.findById(tripId);
        UserEntity user = UserEntity.findById(userId);
        if (trip == null | user == null) {
            throw new IllegalArgumentException("User or trip doesn't exist");
        }
        trip.getInterestedUsers().add(user);
        TripEntity.persist(trip);

        TripResponseModel tripResponseModel =  modelMapper.map(trip, TripResponseModel.class);
        var weather = getWeather(tripResponseModel);
        tripResponseModel.setWeather(weather);
        return tripResponseModel;
    }

    private List<WeatherResponseModel> getWeather(TripResponseModel trip) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = trip.getStartDate().toLocalDate();
        LocalDate endDate = startDate.plusDays(trip.getDurationInDays());
        if (endDate.isBefore(today) || startDate.isAfter(today.plusDays(14))) {
            return List.of();
        }

        long days = ChronoUnit.DAYS.between(today, endDate);
        var worldWeatherOnlineResponseModel = worldWeatherService.getWeather(trip.getLocation(), days);
        return Optional.ofNullable(worldWeatherOnlineResponseModel)
                .map(WorldWeatherOnlineResponseModel::getData)
                .map(WorldWeatherOnlineResponseModel.Data::getWeather)
                .orElse(List.of())
                .stream()
                .filter(weatherResponseModel -> !weatherResponseModel.getDate().isBefore(startDate))
                .collect(Collectors.toList());
    }
}
