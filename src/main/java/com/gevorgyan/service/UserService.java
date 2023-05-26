package com.gevorgyan.service;

import com.gevorgyan.entity.UserEntity;
import com.gevorgyan.model.UserRequestModel;
import com.gevorgyan.model.UserResponseModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserService {

    private final ModelMapper modelMapper = new ModelMapper();

    @Transactional
    public UserResponseModel addUser(UserRequestModel userRequestModel) {
        if (UserEntity.findByUsername(userRequestModel.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        UUID userUUID = UUID.randomUUID();
        UserEntity userEntity = modelMapper.map(userRequestModel, UserEntity.class);
        userEntity.setId(userUUID.toString());
        UserEntity.persist(userEntity);
        return modelMapper.map(userEntity, UserResponseModel.class);
    }

    public Optional<UserResponseModel> findUser(String username, String password) {
        UserEntity userEntity = UserEntity.findByUsernameAndPassword(username, password);
        if (userEntity == null) {
            return Optional.empty();
        }
        return Optional.of(modelMapper.map(userEntity, UserResponseModel.class));
    }

    @Transactional
    public void deleteUser(String id) {
        if (UserEntity.findById(id) == null) {
            throw new IllegalArgumentException("User not exists");
        }

        UserEntity.deleteById(id);
    }

    public boolean isNotAdmin(String id) {
        UserEntity userEntity =  UserEntity.findById(id);
        return userEntity == null || !userEntity.getIsAdmin();
    }
}
