package com.pizzaapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.pizzaapp.domain.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String email);

    @Query("SELECT u FROM User u order by u.username")
    List<User> findAllOrderedAlphabetically();
}
