package com.src.destinations.controllers;

import com.src.destinations.models.Destination;
import com.src.destinations.services.DestinationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("destinations")
public class DestinationController {

    @Autowired
    DestinationService destinationService;

    @GetMapping("/")
    public ResponseEntity<List<Destination>> findAll() {
        return destinationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Destination>> findById(@PathVariable UUID id) {
        return destinationService.findById(id);
    }

    @GetMapping("/{name}/{location}")
    public ResponseEntity<List<Destination>> findByNameOrLocation(@PathVariable String name, @PathVariable String location) {
        return destinationService.findByNameOrLocation(name, location);
    }

    @PostMapping("/")
    public ResponseEntity<Destination> create(@RequestBody @Valid Destination destination) {
        return destinationService.create(destination);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Destination> update(
        @PathVariable UUID id,
        @RequestBody @Valid Destination destination
    ) {
        return destinationService.update(id, destination);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        return destinationService.delete(id);
    }

    @PatchMapping("/{id}/rating")
    public ResponseEntity<String> addRating(
            @PathVariable UUID id,
            @RequestParam double rating
    ) {
        return destinationService.addRating(id, rating);
    }
}
