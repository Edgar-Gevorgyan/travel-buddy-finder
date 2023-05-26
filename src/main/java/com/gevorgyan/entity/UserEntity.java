package com.gevorgyan.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "users")
public class UserEntity extends PanacheEntityBase {
    @Id
    private String id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(columnDefinition = "boolean default false")
    private Boolean isAdmin;

    public static UserEntity findByUsername(String username){
        return find("username", username).firstResult();
    }

    public static UserEntity findByUsernameAndPassword(String username, String password){
        return find("username = ?1 and password = ?2", username, password).firstResult();
    }
}