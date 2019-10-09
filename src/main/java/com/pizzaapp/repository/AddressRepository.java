package com.pizzaapp.repository;

import com.pizzaapp.domain.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {

    @Query("select a from Address a join a.owner as o where o.username = :email order by a.town")
    List<Address> findAllUserAddressesOrderedByName(@Param("email") String email);
}
