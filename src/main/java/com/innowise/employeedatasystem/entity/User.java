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
    private Long id;

    @Column(unique = true, length = 30)
    private String username;

    private String password;

    @Column(unique = true, length = 50)
    private String mail;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = EntityConstant.Table.TABLE_USERS_ROLES,
            joinColumns = @JoinColumn(name = EntityConstant.Column.USER_ID, referencedColumnName = EntityConstant.Column.ID_FIELD),
            inverseJoinColumns = @JoinColumn(name = EntityConstant.Column.ROLE_ID, referencedColumnName = EntityConstant.Column.ID_FIELD))
    private Set<Role> roles;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = EntityConstant.Column.ID_FIELD)
    private Employee employee;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
}