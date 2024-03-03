package com.example.Backend.Relations;

import com.example.Backend.OfficeFunctional.OfficeEntity;
import com.example.Backend.UserFunctional.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "UserToOffice")
public class UserToOffice {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    @ManyToOne
    @JoinColumn
    private UserEntity user;
    @ManyToOne
    private OfficeEntity office;
    private String Role;

    public UserToOffice(){}
    public UserToOffice(UserEntity user, OfficeEntity office, String Role){
        this.user = user;
        this.office = office;
        this.Role = Role;
    }}
