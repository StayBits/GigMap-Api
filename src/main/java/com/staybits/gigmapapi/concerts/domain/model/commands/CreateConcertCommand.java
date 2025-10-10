package com.staybits.gigmapapi.concerts.domain.model.commands;

import com.staybits.gigmapapi.concerts.domain.model.aggregates.Venue;
import com.staybits.gigmapapi.concerts.domain.model.valueobjects.ConcertStatus;
import com.staybits.gigmapapi.concerts.domain.model.valueobjects.Genre;

import java.sql.Date;

public record CreateConcertCommand(
    String title,
    Date datehour,
    Venue venue,
    ConcertStatus status,
    Long userId,
    Genre genre
) {
}
