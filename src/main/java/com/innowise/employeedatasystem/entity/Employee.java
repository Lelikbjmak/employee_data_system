package com.innowise.employeedatasystem.entity;

import com.innowise.employeedatasystem.util.EntityConstant;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = EntityConstant.Table.TABLE_EMPLOYEES)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    @Temporal(TemporalType.DATE)
    private Date hireDate;

    @Getter
    @OneToOne(mappedBy = EntityConstant.Column.EMPLOYEE, cascade = {CascadeType.REFRESH, CascadeType.REMOVE},
            fetch = FetchType.LAZY)
    private User user;

}
