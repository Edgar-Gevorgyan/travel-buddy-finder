package com.gevorgyan.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "users")
@ToString
public class UserEntity extends PanacheEntityBase {
    @Id
    private String id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(columnDefinition = "boolean default false")
    private Boolean isAdmin;
    @OneToMany(mappedBy="sharedBy")
    private Set<TripEntity> trips;
    @ManyToMany(mappedBy="interestedUsers")
    private Set<TripEntity> interestingTrips;

    public static UserEntity findByUsername(String username){
        return find("username", username).firstResult();
    }

    public static UserEntity findByUsernameAndPassword(String username, String password){
        return find("username = ?1 and password = ?2", username, password).firstResult();
    }
}