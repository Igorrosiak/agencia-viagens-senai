package com.src.destinations.services;

import com.src.destinations.models.Destination;
import com.src.destinations.repositories.DestinationRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DestinationServiceImpl implements DestinationService {

    @Autowired
    DestinationRepository destinationRepository;

    @Override
    public ResponseEntity<List<Destination>> findAll() {
        try {
            List<Destination> destinationsList = destinationRepository.findAll();
            if (destinationsList.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(destinationsList);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<Optional<Destination>> findById(UUID id) {
        try {
            Optional<Destination> destinationOptional = destinationRepository.findById(id);
            if (destinationOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(destinationOptional);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<List<Destination>> findByNameOrLocation(@NotNull String name, @NotNull String location) {
        try {
            List<Destination> destinations = destinationRepository.findByNameOrLocation(name, location);
            if (destinations.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(destinations);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<Destination> create(Destination destinationData) {
        try {
            Destination destination = new Destination();
            BeanUtils.copyProperties(destinationData, destination);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(destinationRepository.save(destination));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Destination> update(UUID id, Destination destinationData) {
        try {
            Optional<Destination> destinationOptional = destinationRepository.findById(id);
            if (destinationOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            var destination = destinationOptional.get();
            BeanUtils.copyProperties(destinationData, destination);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(destinationRepository.save(destination));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity delete(UUID id) {
        try {
            Optional<Destination> destinationOptional = destinationRepository.findById(id);
            if (destinationOptional.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Destination not found");
            }
            destinationRepository.deleteById(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Destination was Deleted");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @Override
    public ResponseEntity<String> addRating(UUID id, double rating) {
        try {
            Optional<Destination> destinationOptional = destinationRepository.findById(id);
            if (destinationOptional.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Destination not found");
            }

            Destination destination = destinationOptional.get();

            double currentAverage = destination.getAverageRating();
            int totalReservations = destination.getTotalReservations();

            double newAverage = ((currentAverage * totalReservations) + rating) / (totalReservations + 1);

            destination.setAverageRating(newAverage);
            destination.setTotalReservations(totalReservations + 1);

            destinationRepository.save(destination);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Rating added successfully. New average rating: " + newAverage);
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body("An error occurred while adding the rating.");
        }
    }

}
