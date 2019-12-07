package com.pizzaapp.repository;

import com.pizzaapp.domain.entities.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourierRepository extends JpaRepository<Courier, String> {

    Optional<Courier> findCourierByEmail(String courierName);
}
