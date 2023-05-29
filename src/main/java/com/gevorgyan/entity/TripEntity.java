package com.gevorgyan.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trips")
public class TripEntity extends PanacheEntityBase {
    @Id
    private String id;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private Date startDate;
    @Column(nullable = false)
    private Integer durationInDays;
    @ManyToOne
    private UserEntity sharedBy;
    @ManyToMany
    @JoinTable(
            name = "interesting_users_trip",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> interestedUsers;

    public static List<TripEntity> listAvailable(){
        return list("startDate >= ?1", LocalDate.now());
    }

    public static List<TripEntity> listBySharedBy(String userId){
        return list("sharedBy.id = ?1", userId);
    }

    public static List<TripEntity> listAvailableAndSharedBy(String userId){
        return list("startDate >= ?1 and sharedBy.id = ?2", LocalDate.now(), userId);
    }

}