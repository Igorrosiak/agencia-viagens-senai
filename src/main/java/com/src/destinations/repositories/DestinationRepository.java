package com.src.destinations.repositories;

import com.src.destinations.models.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface DestinationRepository extends JpaRepository<Destination, UUID> {
    void save(Optional<Destination> destination);
    List<Destination> findByNameOrLocation(String name, String location);
}
