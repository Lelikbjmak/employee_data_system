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
@Table(name = EntityConstant.Table.TABLE_ROLES)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @ManyToMany(mappedBy = EntityConstant.Column.ROLES)
    private Set<User> userSet;

}
