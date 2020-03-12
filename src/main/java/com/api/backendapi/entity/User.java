package com.api.backendapi.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tbl_users")
@Getter
@Setter
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", columnDefinition = "NVARCHAR(100)")
    private String name;

    @Column(name = "username", columnDefinition = "VARCHAR(20)")
    private String username;

    @Column(name = "password", columnDefinition = "VARCHAR(20)")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToOne(targetEntity = Team.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    private Team team;
}
