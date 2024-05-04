package com.auth.ProgrammingTechnology.Authorization.dal.repository;

import com.auth.ProgrammingTechnology.Authorization.dal.model.AuthAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AuthAccount, UUID> {
    @Query("select a from auth_accounts a where email = ?1")
    AuthAccount findByEmailAddress(String email);
}
