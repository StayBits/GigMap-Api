package com.staybits.gigmapapi.concerts.domain.services;

import com.staybits.gigmapapi.concerts.domain.model.aggregates.Concert;
import com.staybits.gigmapapi.concerts.domain.model.queries.GetAllConcertsQuery;
import com.staybits.gigmapapi.concerts.domain.model.queries.GetConcertByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ConcertQueryService {
  List<Concert> handle(GetAllConcertsQuery query);

  Optional<Concert> handle(GetConcertByIdQuery query);

  Boolean checkAttendance(Long concertId, Long userId);
}
