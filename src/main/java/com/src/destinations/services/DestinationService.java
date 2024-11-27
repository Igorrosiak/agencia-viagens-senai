package com.src.destinations.services;

import com.src.destinations.models.Destination;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DestinationService {
    public ResponseEntity<List<Destination>> findAll();

    public ResponseEntity<Optional<Destination>> findById(UUID id);

    public ResponseEntity<List<Destination>> findByNameOrLocation(String name, String location);

    public ResponseEntity<Destination> create(Destination destination);

    public ResponseEntity<Destination> update(UUID id, Destination destination);

    public ResponseEntity delete(UUID id);

    public ResponseEntity<String> addRating(UUID id, double rating);
}

