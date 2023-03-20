package com.innowise.employeedatasystem.entity;


import com.innowise.employeedatasystem.util.EntityConstant;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = EntityConstant.Table.TABLE_USERS)
public class User implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}