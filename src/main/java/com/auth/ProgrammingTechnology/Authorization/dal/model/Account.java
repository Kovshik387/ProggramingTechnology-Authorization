package com.auth.ProgrammingTechnology.Authorization.dal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
//@Entity
//@Table(name = "accounts")
@Builder
public class Account {
    private int id;
    private String firstName;
    private String surname;
    private String lastName;
    private String email;
    private String passwordHash;
    private Date createdAt;
    private Date updatedAt;
    private int role;
    private int activityStatus;
    private boolean isDeleted;
    private int currentEvent;
}
