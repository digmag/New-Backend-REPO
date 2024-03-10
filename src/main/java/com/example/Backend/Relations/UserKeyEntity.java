package com.example.Backend.Relations;

import com.example.Backend.KeyFunctional.KeyEntity;
import com.example.Backend.UserFunctional.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
public class UserKeyEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    @OneToOne
    @JoinColumn
    private UserEntity userFrom;
    @OneToOne
    @JoinColumn
    private UserEntity userTo;
    @OneToOne
    @JoinColumn
    private KeyEntity keyEntity;
    private StatusKey status;

    public UserKeyEntity(UserEntity userFrom, UserEntity userTo, KeyEntity keyEntity){
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.keyEntity = keyEntity;
        this.status = StatusKey.IN_PROCESS;
    }
}
