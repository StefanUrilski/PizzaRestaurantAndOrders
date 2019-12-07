package com.pizzaapp.repository;

import com.pizzaapp.domain.entities.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourierRepository extends JpaRepository<Courier, String> {

    @Query("select c " +
            "from Courier as c " +
            "join c.user as u " +
            "where u.username = :courierName")
    Optional<Courier> findCourierByName(String courierName);
}
