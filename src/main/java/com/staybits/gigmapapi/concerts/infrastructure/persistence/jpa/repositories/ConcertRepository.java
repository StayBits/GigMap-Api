package com.staybits.gigmapapi.concerts.infrastructure.persistence.jpa.repositories;

import com.staybits.gigmapapi.concerts.domain.model.aggregates.Concert;
import com.staybits.gigmapapi.concerts.domain.model.valueobjects.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long> {
    List<Concert> findByGenre(Genre genre);
    
    List<Concert> findByUserId(Long userId);
}
