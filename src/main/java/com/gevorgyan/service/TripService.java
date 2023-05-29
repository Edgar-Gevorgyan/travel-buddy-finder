package com.gevorgyan.service;

import com.gevorgyan.entity.TripEntity;
import com.gevorgyan.entity.UserEntity;
import com.gevorgyan.model.TripRequestModel;
import com.gevorgyan.model.TripResponseModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TripService {

    private static final ModelMapper modelMapper = new ModelMapper();

    @Transactional
    public TripResponseModel addTrip(TripRequestModel tripRequestModel, String userId) {
        UserEntity user = UserEntity.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not exists");
        }

        UUID userUUID = UUID.randomUUID();
        TripEntity tripEntity = modelMapper.map(tripRequestModel, TripEntity.class);
        tripEntity.setId(userUUID.toString());
        tripEntity.setSharedBy(user);
        TripEntity.persist(tripEntity);
        return modelMapper.map(tripEntity, TripResponseModel.class);
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
        return modelMapper.map(trips, new TypeToken<List<TripResponseModel>>() {}.getType());
    }

    public TripResponseModel getTrip(String id) {
        TripEntity trip = TripEntity.findById(id);
        if (trip == null) {
            throw new IllegalArgumentException("Trip not found");
        }
        return modelMapper.map(trip, TripResponseModel.class);
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
        return modelMapper.map(trip, TripResponseModel.class);
    }
}
