package com.auth.ProgrammingTechnology.Authorization.dal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Account {
    private UUID id;
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
