package com.innowise.employeedatasystem.entity;


import com.innowise.employeedatasystem.util.EntityConstant;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = EntityConstant.Table.TABLE_USERS)
public class User {

    @Id
    @Getter
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Getter
    @Column(unique = true)
    private String mail;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = EntityConstant.Table.TABLE_USERS_ROLES,
            joinColumns = @JoinColumn(name = EntityConstant.Column.USER_ID, referencedColumnName = EntityConstant.Column.ID),
            inverseJoinColumns = @JoinColumn(name = EntityConstant.Column.ROLE_ID, referencedColumnName = EntityConstant.Column.ID))
    @Getter
    private Set<Role> roles;

    @Getter
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = EntityConstant.Column.ID)
    private Employee employee;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
}