package com.staybits.gigmapapi.concerts.infrastructure.persistence.jpa.repositories;

import com.staybits.gigmapapi.concerts.domain.model.aggregates.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
}
