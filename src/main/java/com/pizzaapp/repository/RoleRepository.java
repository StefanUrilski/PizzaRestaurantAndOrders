package com.pizzaapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pizzaapp.domain.entities.UserRole;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, String> {

    Optional<UserRole> findByAuthority(String authority);
}
