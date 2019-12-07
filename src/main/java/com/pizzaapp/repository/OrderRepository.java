package com.pizzaapp.repository;

import com.pizzaapp.domain.entities.Order;
import com.pizzaapp.domain.entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    @Query( "select o " +
            "from Order as o " +
            "join o.address as a " +
            "where a.town like :town " +
            "and o.taken = :taken " +
            "order by o.orderedOn desc")
    List<Order> findAllByTownAndIfTaken(@Param("town") Town town, boolean taken);

}
